package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.AratoTsunami;

public class Chargement extends Ecran {

	public Chargement(Vue vue) {
		super("Chargement des données dans AWS", vue);
	}

	@Override
	public void action() {
		System.out.println(this.titre);

		AratoTsunami.charger();
	}

}
