package net.courtanet.arato.tsunami.ecran;


public class Accueil extends Ecran {

	public Accueil() {
		super("Accueil");
	}

	@Override
	public void action() {
		System.out.println("Bienvenue dans l'application Arato Tsunami");
	}

}
