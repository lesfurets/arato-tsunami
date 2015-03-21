package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre;

public class Tsunami extends Ecran {

	public Tsunami(Vue vue) {
		super("Faire trembler la terre", vue);
	}

	@Override
	public void action() {
		System.out.println(this.titre);

		new TremblementDeTerre(vue).trembler();
	}

}
