package net.courtanet.arato.tsunami.ecran;

import java.util.Scanner;

public class SystemeEcran {

	public final static Ecran ACCUEIL = new Accueil();
	public final static Ecran CHARGEMENT = new Chargement();
	public final static Ecran TSUNAMI = new Tsunami();
	public final static Ecran RESULTATS = new Resultats();
	public final static Ecran QUITTER = new Quitter();

	public static Scanner input = new Scanner(System.in);
	private static Ecran ecranEnCours = SystemeEcran.ACCUEIL;

	private static SystemeEcran INSTANCE;

	private SystemeEcran() {
		ACCUEIL.ajouterEcran(CHARGEMENT);
		ACCUEIL.ajouterEcran(TSUNAMI);
		ACCUEIL.ajouterEcran(RESULTATS);
		ACCUEIL.ajouterEcran(QUITTER);

		CHARGEMENT.ajouterEcran(TSUNAMI);
		CHARGEMENT.ajouterEcran(ACCUEIL);

		TSUNAMI.ajouterEcran(RESULTATS);
		TSUNAMI.ajouterEcran(ACCUEIL);

		RESULTATS.ajouterEcran(ACCUEIL);
	}

	public static SystemeEcran getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SystemeEcran();
		}
		return INSTANCE;
	}

	public void demarrer() throws InterruptedException {
		while (SystemeEcran.ecranEnCours != null) {
			SystemeEcran.ecranEnCours.action();

			Thread.sleep(3000);
			SystemeEcran.ecranEnCours.afficher();
			System.out.println("...");

			int choix = faireUnChoix(SystemeEcran.ecranEnCours) - 1;
			SystemeEcran.ecranEnCours = ecranEnCours.naviguer(choix);
		}
	}

	private int faireUnChoix(Ecran ecran) {
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
