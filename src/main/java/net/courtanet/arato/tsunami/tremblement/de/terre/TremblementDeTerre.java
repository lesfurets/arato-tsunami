package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.Noeud;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;
import net.courtanet.arato.tsunami.ecran.Vue;

public class TremblementDeTerre {

	public static long debutTremblement;
	private final Vue vue;

	public TremblementDeTerre(Vue vue) {
		this.vue = vue;
	}

	// TODO needs test
	public void trembler() {
		System.out.println("Création des schémas dans Cassandra");
		CassandraCluster.getInstance().createTableCampagne();
		System.out.println("Création des schémas dans Cassandra OK");

		Coordonnees epicentre = demanderEpicentre();
		LocalDateTime moment = vue.demanderMoment();

		System.out.println("Début du tremblement de terre");
		Campagne campagne = debutTremblement();

		couperNoeuds(epicentre);
		System.out.println("Arrêt des noeuds OK");

		alerter(epicentre, moment, campagne);
	}

	// TODO needs test
	private Coordonnees demanderEpicentre() {
		System.out.println("Où vouler-vous faire trembler la terre ?");
		double latitude = vue.entrerCoordonnee("latitude", 32, 40);// TODO
																	// mettre
																	// en config
		double longitude = vue.entrerCoordonnee("longitude", 133, 145);// TODO
																		// mettre
																		// en
																		// config
		return new Coordonnees(latitude, longitude);
	}

	// TODO needs test
	private void couperNoeuds(Coordonnees epicentre) {
		System.out
				.println("La terre a tremblée... Arrêt des noeuds proches de l'épicentre.");
		for (Noeud noeud : Noeud.values()) {
			if (Coordonnees.isNextToTheEpicenter(epicentre,
					noeud.getCoordonnees())) {
				noeud.stop();
				System.out.println("Noeud " + noeud.getNom() + " stoppé.");
			}
		}
	}

	// TODO needs test
	private void alerter(Coordonnees epicentre, LocalDateTime moment,
			Campagne campagne) {
		System.out.println("Envoi des SMS...");
		// Ici, on ne doit pas utiliser Spark

		// Selection des numeros de telephone a contacter
		TelephoneDAO telephoneDao = new TelephoneDAO();
		Set<String> antennes = getAntennes(epicentre);

		// Notification par SMS
		for (String antenne : antennes)
			campagne.ajouterNombrePrevenus(telephoneDao.selectTelsToAlert(
					antenne, moment).size());// TODO voir pour mettre ca
												// asynchrone
												// (ResultSetFuture ?)
		campagne.stopper();
		System.out.println(campagne.getNombrePrevenus() + " SMS envoyés.");
	}

	// TODO needs test
	private Set<String> getAntennes(Coordonnees epicentre) {
		Set<String> antennes = new HashSet<>();
		for (Noeud noeud : Noeud.values()) {
			if (Coordonnees.isNextToTheEpicenter(epicentre,
					noeud.getCoordonnees())) {
				for (int i = 0; i < 100; i++) {
					final String cond = (i < 10) ? "0" : "";
					antennes.add(noeud.getNom().substring(0, 3) + "_" + cond
							+ i);
				}
			}
		}
		return antennes;
	}

	// TODO needs test
	private Campagne debutTremblement() {
		debutTremblement = System.currentTimeMillis();
		System.out.println("Début du tremblement de terre à "
				+ debutTremblement);
		System.out.println("Lancement de la campagne d'enregistrement");
		DateTimeFormatter formater = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Campagne.lancerCampagne("campagne"
				+ formater.format(LocalDateTime.now()));
	}
}
