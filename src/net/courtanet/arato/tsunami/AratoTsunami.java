package net.courtanet.arato.tsunami;

import java.util.Scanner;

import net.courtanet.arato.tsunami.ecran.Ecran;
import net.courtanet.arato.tsunami.ecran.SystemeEcran;

public class AratoTsunami {

	public static Scanner input = new Scanner(System.in);

	private static Ecran ecranEnCours = SystemeEcran.ACCUEIL;

	public static void main(String[] args) {
		while (AratoTsunami.ecranEnCours != null) {
			AratoTsunami.ecranEnCours.action();

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

		while (choix > ecran.getMaxChoix() || choix < 1) {
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
