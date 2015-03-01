package net.courtanet.arato.tsunami.cluster;

import net.courtanet.arato.tsunami.dao.SMSDAO;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraCluster {

	public final static String KEY_SPACE = "arato";

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

	public void close() {
		cluster.close();
	}

	public Metadata getMetadata() {
		return cluster.getMetadata();
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
		session.execute("CREATE KEYSPACE IF NOT EXISTS "
				+ KEY_SPACE
				+ " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");// TODO
																								// mettre
																								// en
																								// config
																								// le
																								// replication_factor
																								// et
																								// la
																								// strategy
		session.execute("USE " + KEY_SPACE + ";");
	}

	public void createTelephoneTable() {
		TelephoneDAO telephoneDao = new TelephoneDAO();
		telephoneDao.createTable();
	}

	public void createSMSTable() {
		SMSDAO smsDao = new SMSDAO();
		smsDao.createTable();
	}

	public void stop(String ip) {
		System.out.println("Arrêt noeud Cassandra (" + ip + ").");
		// TODO Auto-generated method stub
		System.out.println("Noeud Cassandra (" + ip + ") stoppé.");
	}

}
