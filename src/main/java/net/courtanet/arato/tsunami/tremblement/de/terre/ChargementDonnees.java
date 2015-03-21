package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;
import net.courtanet.arato.tsunami.dao.TelephoneDTO;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import scala.Tuple2;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

@SuppressWarnings("serial")
// TODO voir si c'est bien, pour l'instant, on laisse comme ca
public class ChargementDonnees implements Serializable {

	private final String DATA_PATH = ChargementDonnees.class.getResource(
			"data_1Mb.csv").getPath();

	// TODO needs test
	public void charger() {
		System.out.println("Chargement des données avec Spark");

		System.out.println("Création des schémas dans Cassandra");
		CassandraCluster.getInstance().creerTableTelephone();
		System.out.println("Création des schémas dans Cassandra OK");

		SparkCluster sparkCluster = SparkCluster.getInstance();

		System.out.println("Création du job Spark");
		// Chargement des données dans un TelephoneDTO simple, 1 numero de
		// telephone -> 1
		// TelephoneDTO
		JavaRDD<TelephoneDTO> distributedRawData = sparkCluster
				.getContext()
				.textFile(DATA_PATH)
				.map(line -> {
					String antenne = line.substring(line.indexOf(";") + 1,
							line.indexOf(";") + 7);
					String tranchehoraire = line.substring(0, 13);
					Set<String> telephones = new HashSet<>();
					telephones.add(line.substring(line.lastIndexOf(";") + 1,
							line.length()));
					return new TelephoneDTO(antenne, tranchehoraire, telephones);
				});

		// Association de chaque raw dto a sa clef de partition
		JavaPairRDD<String, TelephoneDTO> pairs = distributedRawData
				.mapToPair((TelephoneDTO dto) -> {
					String partitionKey = dto.getAntenne()
							+ dto.getTranchehoraire();
					return new Tuple2<String, TelephoneDTO>(partitionKey, dto);
				});

		// Aggregation des numeros de telphone dans les dto en fonction
		// de la clef de partition
		JavaRDD<TelephoneDTO> distributedData = pairs.reduceByKey(
				(TelephoneDTO dto1, TelephoneDTO dto2) -> {
					TelephoneDTO dto = new TelephoneDTO();
					dto.setAntenne(dto1.getAntenne());
					dto.setTranchehoraire(dto1.getTranchehoraire());
					Set<String> telephones = dto1.getTelephones();
					telephones.addAll(dto2.getTelephones());
					dto.setTelephones(telephones);
					return dto;
				}).map(t -> t._2);

		System.out.println("Soumission job Spark");
		// Insertion des donnees dans Cassandra
		CassandraJavaUtil
				.javaFunctions(distributedData)
				.writerBuilder(CassandraCluster.KEY_SPACE,
						TelephoneDAO.TABLE_TELEPHONE,
						CassandraJavaUtil.mapToRow(TelephoneDTO.class))
				.saveToCassandra();
		System.out.println("Fin job Spark");
	}

}
