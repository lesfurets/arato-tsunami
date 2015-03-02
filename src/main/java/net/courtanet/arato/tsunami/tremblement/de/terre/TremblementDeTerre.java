package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.Noeud;
import net.courtanet.arato.tsunami.dao.SMSDAO;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;
import net.courtanet.arato.tsunami.ecran.SystemeEcran;

public class TremblementDeTerre {

	public static long debutTremblement;

	public void trembler() {
		System.out.println("Création des schémas dans Cassandra");
		CassandraCluster.getInstance().createSMSTable();
		System.out.println("Création des schémas dans Cassandra OK");

		Coordonnees epicentre = demanderEpicentre();
		Date moment = demanderMoment();

		System.out.println("Début du tremblement de terre");
		debutTremblement();

		couperNoeuds(epicentre);
		System.out.println("Arrêt des noeuds OK");

		alerter(epicentre, moment);
	}

	private Date demanderMoment() {
		System.out.println("Quand vouler-vous faire trembler la terre ?");
		Date moment = null;// TODO LocalDateTime et DateTimeFormatter
		String saisie = "";
		String message = "Veuillez entrer une date au format aaaa/mm/jj hh:mm:ss";
		final DateFormat SDF = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		boolean encore = true;
		while (encore) {
			System.out.println(message);// TODO exit si trop de tentative

			while (!SystemeEcran.input.hasNext()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				SystemeEcran.input.next();
			}
			saisie = SystemeEcran.input.next() + " "
					+ SystemeEcran.input.next();

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
		double latitude = entrerCoordonnee("latitude", 32, 40);// TODO mettre
																// en config
		double longitude = entrerCoordonnee("longitude", 133, 145);// TODO
																	// mettre en
																	// config
		return new Coordonnees(latitude, longitude);
	}

	private double entrerCoordonnee(String coord, double min, double max) {
		double saisie = min - 1;
		String message = "Veuillez entrer une " + coord + " entre " + min
				+ " et " + max + " :";

		while (saisie < min || saisie > max) {// TODO exit si trop de tentative
			System.out.println(message);

			while (!SystemeEcran.input.hasNextDouble()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				SystemeEcran.input.next();
			}
			saisie = SystemeEcran.input.nextDouble();
		}
		return saisie;
	}

	private void couperNoeuds(Coordonnees epicentre) {
		System.out
				.println("La terre a tremblée... Arrêt des noeuds proches de l'épicentre.");
		for (Noeud noeud : Noeud.values()) {
			if (Coordonnees.isNextToTheEpicenter(epicentre,
					noeud.getCoordonnees())) {
				noeud.stop();
				System.out.println("Noeud " + noeud.getName() + " stoppé.");
			}
		}
	}

	private void alerter(Coordonnees epicentre, Date moment) {
		System.out.println("Envoi des SMS...");
		// Ici, on ne doit pas utiliser Spark

		// Selection des numeros de telephone a contacter
		TelephoneDAO telephoneDao = new TelephoneDAO();
		Set<String> antennes = getAntennes(epicentre);

		// Notification par SMS
		SMSDAO smsDao = new SMSDAO();
		for (String antenne : antennes)
			smsDao.envoyerSMS(telephoneDao.selectTelsToAlert(antenne, moment));
		System.out.println("SMS envoyés.");
	}

	private Set<String> getAntennes(Coordonnees epicentre) {
		Set<String> antennes = new HashSet<>();
		for (Noeud noeud : Noeud.values()) {
			if (Coordonnees.isNextToTheEpicenter(epicentre,
					noeud.getCoordonnees())) {
				for (int i = 0; i < 100; i++) {
					final String cond = (i < 10) ? "0" : "";
					antennes.add(noeud.getName().substring(0, 3) + "_" + cond
							+ i);
				}
			}
		}
		return antennes;
	}

	private void debutTremblement() {
		debutTremblement = System.currentTimeMillis();
		System.out.println("Début du tremblement de terre à "
				+ debutTremblement);
	}
}
