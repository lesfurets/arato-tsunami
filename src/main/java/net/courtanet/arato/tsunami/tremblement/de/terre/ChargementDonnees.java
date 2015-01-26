package net.courtanet.arato.tsunami.tremblement.de.terre;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;

import org.apache.spark.api.java.JavaRDD;

public class ChargementDonnees {

	private final String DATA_PATH = "";

	public void charger() {
		System.out.println("Creation des schemas dans Cassandra");

		CassandraCluster cassandraCluster = CassandraCluster.getInstance();
		cassandraCluster.init();

		System.out.println("Chargement des données avec Spark");

		SparkCluster sparkCluster = SparkCluster.getInstance();
		sparkCluster.init();

		JavaRDD<String> distributedData = sparkCluster.getContext().textFile(
				DATA_PATH);

		// Insertion des donnees dans Cassandra
		// CassandraJavaUtil.javaFunctions(distributedData).saveToCassandra(
		// KEY_SPACE, "telephone", null, null);

		TelephoneDAO telephone = new TelephoneDAO();
		telephone.inserer(null);// TODO

	}

}
