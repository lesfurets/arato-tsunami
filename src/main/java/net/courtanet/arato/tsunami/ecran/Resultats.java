package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.ResultatsAlerte;

public class Resultats extends Ecran {

	public Resultats(Vue vue) {
		super("Résultats", vue);
	}

	@Override
	public void action() {
		System.out.println("Affichage des résultats");

		new ResultatsAlerte(vue).afficher();
	}

}
