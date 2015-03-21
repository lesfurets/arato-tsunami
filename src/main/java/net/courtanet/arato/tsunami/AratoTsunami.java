package net.courtanet.arato.tsunami;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.ecran.SystemeEcran;

public class AratoTsunami {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Initialisation de l'application");

		System.out.println("Initialisation cluster Cassandra");
		CassandraCluster.getInstance().init();// TODO Arrêter le programme si
												// Cassandra n'est pas démarée
		System.out.println("Initialisation cluster Cassandra OK");

		System.out.println("Initialisation cluster Spark");
		SparkCluster.getInstance();
		System.out.println("Initialisation cluster Spark OK");
		System.out
				.println("                                                        ");
		System.out
				.println(" _____         _          _____                       _ ");
		System.out
				.println("|  _  |___ ___| |_ ___   |_   _|___ _ _ ___ ___ _____|_|");
		System.out
				.println("|     |  _| .'|  _| . |    | | |_ -| | |   | .'|     | |");
		System.out
				.println("|__|__|_| |__,|_| |___|    |_| |___|___|_|_|__,|_|_|_|_|");
		System.out
				.println("                                                        ");

		SystemeEcran.getInstance().demarrer();
	}

}
