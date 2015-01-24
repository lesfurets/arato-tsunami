package net.courtanet.arato.tsunami.cluster;

import net.courtanet.arato.tsunami.dao.TelephoneDAO;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;

public class CassandraCluster {

	public final static String KEY_SPACE = "arato";

	private static CassandraCluster INSTANCE = null;

	private Cluster cluster = null;
	private Session session = null;
	private PoolingOptions poolingOptions = new PoolingOptions();

	private CassandraCluster() {
		try {
			cluster = Cluster.builder().addContactPoints(getContactPoints())//
					.withPoolingOptions(poolingOptions).build();
			session = cluster.connect(KEY_SPACE);
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
		session.execute("DROP KEYSPACE IF EXISTS " + KEY_SPACE);
		session.execute("CREATE KEYSPACE "
				+ KEY_SPACE
				+ " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");
		TelephoneDAO dao = new TelephoneDAO();
		dao.createTable();
	}

	public void stop(String ip) {
		// TODO Auto-generated method stub
		System.out.println("Noeud " + ip + " stoppé.");
	}

}