package net.courtanet.arato.tsunami.ecran;

import java.time.LocalDateTime;

public class SystemeEcran implements Vue {

	public final Ecran ACCUEIL = new Accueil(this);
	public final Ecran CHARGEMENT = new Chargement(this);
	public final Ecran TSUNAMI = new Tsunami(this);
	public final Ecran RESULTATS = new Resultats(this);
	public final Ecran QUITTER = new Quitter(this);

	private final static ConsoleIO console = new ConsoleIO();
	private static Ecran ecranEnCours;

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

		ecranEnCours = this.ACCUEIL;
	}

	public static SystemeEcran getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SystemeEcran();
		}
		return INSTANCE;
	}

	@Override
	public void demarrer() {
		while (SystemeEcran.ecranEnCours != null) {
			SystemeEcran.ecranEnCours.action();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Problème");
				quitter();
			}
			SystemeEcran.ecranEnCours.afficher();
			System.out.println("...");
			try {
				int choix = console.faireUnChoixInt("valeur", 1,
						ecranEnCours.getMaxChoix());
				SystemeEcran.ecranEnCours = ecranEnCours.naviguer(choix);
			} catch (MaxTentativeException e) {
				afficherLigne(e.getMessage());
				quitter();
			}
		}
	}

	@Override
	public void quitter() {
		console.quitter();
	}

	@Override
	public String choixCampagne() {
		try {
			return console.choixCampagne();
		} catch (MaxTentativeException e) {
			afficherLigne(e.getMessage());
			quitter();
		}
		return null;
	}

	@Override
	public void afficherLigne(String aAfficher) {
		System.out.println(aAfficher);
	}

	@Override
	public void afficher(String aAfficher) {
		System.out.print(aAfficher);
	}

	@Override
	public double entrerCoordonnee(String coordonnee, double min, double max) {
		try {
			return console.faireUnChoixDouble(coordonnee, min, max);
		} catch (MaxTentativeException e) {
			afficherLigne(e.getMessage());
			quitter();
		}
		return 0;
	}

	@Override
	public LocalDateTime demanderMoment() {
		return console.demanderMoment();
	}
}
