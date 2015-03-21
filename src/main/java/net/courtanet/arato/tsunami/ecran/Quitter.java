package net.courtanet.arato.tsunami.ecran;

public class Quitter extends Ecran {

	Quitter(Vue vue) {
		super("Quitter", vue);
	}

	@Override
	public void action() {
		System.out.println("Sayonara.");
		vue.quitter();
	}

}
