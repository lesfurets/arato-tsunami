package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.tremblement.de.terre.Coordonnees;

public class Tsunami extends Ecran {

	public Tsunami() {
		super("Faire trembler la terre");
	}

	@Override
	public void action() {
		Coordonnees coord = demanderCoordonnees();
		couperNoeuds(coord);
		envoyerSMS();
	}

	private Coordonnees demanderCoordonnees() {
		System.out.println("Où vouler -vous faire trembler la terre ?");
		return null;
	}

	private void couperNoeuds(Coordonnees coord) {
		System.out
				.println("La terre a tremblée... Arrêt des noeuds proches de l'épicentre.");
	}

	private void envoyerSMS() {
		System.out.println("Envoi des SMS...");
	}

}
