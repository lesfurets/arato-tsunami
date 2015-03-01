package net.courtanet.arato.tsunami;

import java.util.Scanner;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.ecran.Ecran;
import net.courtanet.arato.tsunami.ecran.SystemeEcran;

public class AratoTsunami {

	public static Scanner input = new Scanner(System.in);

	private static Ecran ecranEnCours = SystemeEcran.ACCUEIL;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Initialisation de l'application");
		System.out.println("Création des schémas dans Cassandra");

		CassandraCluster.getInstance().init();
		SparkCluster.getInstance();
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

		while (AratoTsunami.ecranEnCours != null) {
			AratoTsunami.ecranEnCours.action();

			Thread.sleep(5000);
			AratoTsunami.ecranEnCours.afficher();
			System.out.println("...");

			int choix = faireUnChoix(AratoTsunami.ecranEnCours) - 1;
			AratoTsunami.ecranEnCours = ecranEnCours.naviguer(choix);
		}
	}

	private static int faireUnChoix(Ecran ecran) {
		int choix = 0;
		String message = "Veuillez entrer une valeur entre 1 et "
				+ ecran.getMaxChoix();

		while (choix > ecran.getMaxChoix() || choix < 1) {// TODO exit si trop
															// de tentative
			System.out.println(message);

			while (!input.hasNextInt()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				input.next();
			}
			choix = input.nextInt();
		}
		return choix;
	}

}
