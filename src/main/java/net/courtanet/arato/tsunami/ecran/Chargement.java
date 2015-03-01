package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.ChargementDonnees;

public class Chargement extends Ecran {

	public Chargement() {
		super("Chargement des données dans AWS");
	}

	@Override
	public void action() {
		System.out.println(this.titre);

		new ChargementDonnees().charger();
	}

}
