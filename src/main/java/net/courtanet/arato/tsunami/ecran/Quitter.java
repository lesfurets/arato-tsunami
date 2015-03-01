package net.courtanet.arato.tsunami.ecran;


public class Quitter extends Ecran {

	Quitter() {
		super("Quitter");
	}

	@Override
	public void action() {
		System.out.println("Sayonara.");
		SystemeEcran.input.close();
		System.exit(0);
	}

}
