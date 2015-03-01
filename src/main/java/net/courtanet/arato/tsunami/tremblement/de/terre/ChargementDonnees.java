package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import scala.Tuple2;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

@SuppressWarnings("serial")
// TODO voir si c'est bien, pour l'instant, on laisse comme ca
public class ChargementDonnees implements Serializable {

	private final String DATA_PATH = ChargementDonnees.class.getResource(
			"data_1Mb.csv").getPath();

	public void charger() {
		System.out.println("Création des schémas dans Cassandra");

		CassandraCluster cassandraCluster = CassandraCluster.getInstance();
		cassandraCluster.init();

		System.out.println("Chargement des données avec Spark");

		SparkCluster sparkCluster = SparkCluster.getInstance();

		// Chargement des données dans un Dto simple, 1 numero de telephone -> 1
		// Dto
		JavaRDD<Dto> distributedRawData = sparkCluster
				.getContext()
				.textFile(DATA_PATH)
				.map(line -> {
					String antenne = line.substring(line.indexOf(";") + 1,
							line.indexOf(";") + 7);
					String tranchehoraire = line.substring(0, 13);
					Set<String> telephones = new HashSet<>();
					telephones.add(line.substring(line.lastIndexOf(";") + 1,
							line.length()));
					return new Dto(antenne, tranchehoraire, telephones);
				});

		// Association de chaque raw Dto a sa clef de partition
		JavaPairRDD<String, Dto> pairs = distributedRawData
				.mapToPair((Dto dto) -> {
					String partitionKey = dto.getAntenne()
							+ dto.getTranchehoraire();
					return new Tuple2<String, Dto>(partitionKey, dto);
				});

		// Aggregation des numeros de telphone dans les Dto en fonction de la
		// clef de partition
		JavaRDD<Dto> distributedData = pairs.reduceByKey(
				(Dto dto1, Dto dto2) -> {
					Dto dto = new Dto();
					dto.setAntenne(dto1.getAntenne());
					dto.setTranchehoraire(dto1.getTranchehoraire());
					Set<String> telephones = dto1.getTelephones();
					telephones.addAll(dto2.getTelephones());
					dto.setTelephones(telephones);
					return dto;
				}).map(t -> t._2);

		// Insertion des donnees dans Cassandra
		CassandraJavaUtil
				.javaFunctions(distributedData)
				.writerBuilder(CassandraCluster.KEY_SPACE,
						TelephoneDAO.TABLE_TELEPHONE,
						CassandraJavaUtil.mapToRow(Dto.class))
				.saveToCassandra();
	}

	public class Dto implements Serializable {

		private String antenne;
		private String tranchehoraire;
		private Set<String> telephones;

		public Dto() {
		}

		public Dto(String antenne, String tranchehoraire, Set<String> telephones) {
			this.antenne = antenne;
			this.tranchehoraire = tranchehoraire;
			this.telephones = telephones;
		}

		public String getAntenne() {
			return this.antenne;
		}

		public void setAntenne(String antenne) {
			this.antenne = antenne;
		}

		public String getTranchehoraire() {
			return this.tranchehoraire;
		}

		public void setTranchehoraire(String tranchehoraire) {
			this.tranchehoraire = tranchehoraire;
		}

		public Set<String> getTelephones() {
			return this.telephones;
		}

		public void setTelephones(Set<String> telephones) {
			this.telephones = telephones;
		}
	}

}
