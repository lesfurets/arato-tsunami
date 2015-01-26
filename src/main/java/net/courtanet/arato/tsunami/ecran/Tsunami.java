package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre;

public class Tsunami extends Ecran {

	public Tsunami() {
		super("Faire trembler la terre");
	}

	@Override
	public void action() {
		System.out.println(this.titre);

		TremblementDeTerre tdt = new TremblementDeTerre();
		tdt.trembler();
	}

}
