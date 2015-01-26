package net.courtanet.arato.tsunami.ecran;

public class SystemeEcran {

	public final static Ecran ACCUEIL = new Accueil();
	public final static Ecran CHARGEMENT = new Chargement();
	public final static Ecran TSUNAMI = new Tsunami();
	public final static Ecran RESULTATS = new Resultats();
	public final static Ecran QUITTER = new Quitter();

	static {
		ACCUEIL.add(CHARGEMENT);
		ACCUEIL.add(TSUNAMI);
		ACCUEIL.add(RESULTATS);
		ACCUEIL.add(QUITTER);

		CHARGEMENT.add(TSUNAMI);
		CHARGEMENT.add(ACCUEIL);

		TSUNAMI.add(RESULTATS);
		TSUNAMI.add(ACCUEIL);

		RESULTATS.add(ACCUEIL);

	}

}
