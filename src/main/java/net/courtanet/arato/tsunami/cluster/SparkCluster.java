package net.courtanet.arato.tsunami.cluster;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkCluster {

	private static SparkCluster INSTANCE = null;

	private JavaSparkContext context = null;

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

	public void close() {
		context.close();
	}

	public JavaSparkContext getContext() {
		return getInstance().context;
	}

	public void stop(String ip) {
		// TODO Auto-generated method stub
		System.out.println("Noeud " + ip + " stoppé.");
	}

}
