package net.courtanet.arato.tsunami.ecran;

public class Accueil extends Ecran {

	public Accueil(Vue vue) {
		super("Accueil", vue);
	}

	@Override
	public void action() {
		System.out.println(this.getTitre());
	}
}
