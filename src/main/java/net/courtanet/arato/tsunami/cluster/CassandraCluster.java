package net.courtanet.arato.tsunami.cluster;

import static net.courtanet.arato.tsunami.configuration.Parametres.facteur_de_replication;
import static net.courtanet.arato.tsunami.configuration.Parametres.keyspace;
import static net.courtanet.arato.tsunami.configuration.Parametres.strategie_de_replication;
import net.courtanet.arato.tsunami.configuration.Parametres;
import net.courtanet.arato.tsunami.dao.CampagneDAO;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraCluster {

	public final static String KEY_SPACE = Parametres.valeurDe(keyspace);

	private static CassandraCluster INSTANCE;

	private Cluster cluster;
	private Session session;

	private CassandraCluster() {
		try {
			cluster = Cluster.builder().addContactPoints(getContactPoints())
					.build();
			session = cluster.connect();
		} catch (Exception e) {
		}
	}

	public static CassandraCluster getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CassandraCluster();
		}
		return INSTANCE;
	}

	public Session getSession() {
		return session;
	}

	private static String[] getContactPoints() {
		String[] contactPoints = new String[Noeud.values().length];
		int i = 0;
		for (Noeud noeud : Noeud.values()) {
			contactPoints[i] = noeud.getIp();
			i++;
		}
		return contactPoints;
	}

	public void init() {
		session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEY_SPACE
				+ " WITH replication = {'class': '"
				+ Parametres.valeurDe(strategie_de_replication)
				+ "', 'replication_factor': "
				+ Parametres.valeurDe(facteur_de_replication) + "};");
		session.execute("USE " + KEY_SPACE + ";");
	}

	public void creerTableTelephone() {
		TelephoneDAO telephoneDao = new TelephoneDAO();
		telephoneDao.creerTable();
	}

	public void createTableCampagne() {
		CampagneDAO campagneDao = new CampagneDAO();
		campagneDao.creerTable();
	}

	public void stop(String ip) {
		System.out.println("Arrêt noeud Cassandra (" + ip + ").");
		// TODO Auto-generated method stub
		System.out.println("Noeud Cassandra (" + ip + ") stoppé.");
	}

}
