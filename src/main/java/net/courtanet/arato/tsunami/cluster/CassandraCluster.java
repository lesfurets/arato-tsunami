package net.courtanet.arato.tsunami.cluster;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import net.courtanet.arato.tsunami.dao.TelephoneDAO;

public class CassandraCluster {

    public final static String KEY_SPACE = "arato";

    private static CassandraCluster INSTANCE = null;

    private Cluster cluster = null;
    private Session session = null;

    private CassandraCluster() {
        try {
            cluster = Cluster.builder().addContactPoints(getContactPoints()).build();
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
        session.execute("DROP KEYSPACE IF EXISTS " + KEY_SPACE + ";");
        session.execute("CREATE KEYSPACE " + KEY_SPACE
                        + " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");
        session.execute("USE " + KEY_SPACE + ";");
        TelephoneDAO dao = new TelephoneDAO();
        dao.createTable();
    }

    public void stop(String ip) {
        // TODO Auto-generated method stub
        System.out.println("Noeud " + ip + " stoppé.");
    }

}
