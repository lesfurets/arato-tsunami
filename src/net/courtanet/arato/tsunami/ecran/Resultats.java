package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.ResultatsAlerte;

public class Resultats extends Ecran {

	public Resultats() {
		super("Résultats");
	}

	@Override
	public void action() {
		System.out.println("Affichage des résultats");

		ResultatsAlerte res = new ResultatsAlerte();
		res.afficher();
	}

}
