package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.AratoTsunami;

public class Quitter extends Ecran {

	Quitter(Vue vue) {
		super("Quitter", vue);
	}

	@Override
	public void action() {
		System.out.println("Sayonara.");

		AratoTsunami.quitter();
	}

}
