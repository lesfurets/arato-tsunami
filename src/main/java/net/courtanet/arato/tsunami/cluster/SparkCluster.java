package net.courtanet.arato.tsunami.cluster;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkCluster {

	private static SparkCluster INSTANCE;

	private JavaSparkContext context;

	private SparkCluster() {
		try {
			SparkConf conf = new SparkConf()
					.setAppName("arato-tsunami")
					.setMaster("local[1]")
					.set("spark.cassandra.connection.host", Noeud.TOKYO.getIp());
			context = new JavaSparkContext(conf);
		} catch (Exception e) {
		}
	}

	public static SparkCluster getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SparkCluster();
		}
		return INSTANCE;
	}

	public JavaSparkContext getContext() {
		return getInstance().context;
	}

	public void stop(String ip) {
		System.out.println("Arrêt noeud Spark (" + ip + ").");
		// TODO Auto-generated method stub
		System.out.println("Noeud Spark (" + ip + ") stoppé.");
	}

}
