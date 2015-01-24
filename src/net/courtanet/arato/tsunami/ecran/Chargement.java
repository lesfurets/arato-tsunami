package net.courtanet.arato.tsunami.ecran;

public class Chargement extends Ecran {

	public Chargement() {
		super("Chargement des données dans AWS");
	}

	@Override
	public void action() {
		System.out.println(this.titre);
	}

}
