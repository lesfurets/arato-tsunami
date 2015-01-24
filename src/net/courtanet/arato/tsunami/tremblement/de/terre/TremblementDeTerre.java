package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.courtanet.arato.tsunami.AratoTsunami;
import net.courtanet.arato.tsunami.cluster.Noeud;
import net.courtanet.arato.tsunami.dao.SMSDAO;

public class TremblementDeTerre {

	public void trembler() {
		Coordonnees epicentre = demanderEpicentre();
		Date moment = demanderMoment();
		couperNoeuds(epicentre);
		envoyerSMS(moment);
	}

	private Date demanderMoment() {
		System.out.println("Qaund vouler-vous faire trembler la terre ?");
		Date moment = null;
		String saisie = "";
		String message = "Veuillez entrer une date au format yyyy/mm/jj hh:mm:ss";
		DateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:ii:ss");
		boolean encore = true;
		while (encore) {
			System.out.println(message);

			while (!AratoTsunami.input.hasNext()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				AratoTsunami.input.next();
			}
			saisie = AratoTsunami.input.next();

			try {
				moment = SDF.parse(saisie);
				encore = false;
			} catch (Exception e) {
				// On continue
			}
		}
		return moment;
	}

	private Coordonnees demanderEpicentre() {
		System.out.println("Où vouler-vous faire trembler la terre ?");
		double latitude = entrerCoordonnee("latitude", 0, 1);
		double longitude = entrerCoordonnee("longitude", 0, 1);
		return new Coordonnees(latitude, longitude);
	}

	private double entrerCoordonnee(String coord, double min, double max) {
		double saisie = 0;
		String message = "Veuillez entrer une " + coord + " entre " + min
				+ " et" + max + " :";

		while (saisie < min || saisie > max) {
			System.out.println(message);

			while (!AratoTsunami.input.hasNextDouble()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				AratoTsunami.input.next();
			}
			saisie = AratoTsunami.input.nextDouble();
		}
		return saisie;
	}

	private void couperNoeuds(Coordonnees epicentre) {
		System.out
				.println("La terre a tremblée... Arrêt des noeuds proches de l'épicentre.");
		for (Noeud noeud : Noeud.values()) {
			if (Coordonnees.hasToBeStopped(epicentre, noeud.getCoordonnees())) {
				noeud.stop();
				System.out.println("Noeud " + noeud.getName() + " stoppé");
			}
		}
	}

	private void envoyerSMS(Date moment) {
		System.out.println("Envoi des SMS...");
		// Ici, on ne peut pas utiliser Spark
		SMSDAO sms = new SMSDAO();
		sms.envoyerSMS(moment);
	}
}
