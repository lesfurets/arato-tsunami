package net.courtanet.arato.tsunami.cluster;

import net.courtanet.arato.tsunami.tremblement.de.terre.Coordonnees;

public enum Noeud {

	TOKYO("127.0.0.1", "Tokyo", new Coordonnees(35.732727, 139.722404)), // TODO
																			// mettre
																			// en
																			// config
																			// pour
																			// le
																			// local
																			// ou
																			// le
																			// cluster
	// YOKOHAMA("192.168.1.102", "Yokohama",
	// new Coordonnees(35.462635, 139.774854)), //
	// OSAKA("192.168.1.103", "Osaka", new Coordonnees(34.705359, 135.500729)),
	// //
	// NAGOYA("192.168.1.104", "Nagoya", new Coordonnees(35.193866,
	// 136.907394)), //
	// SAPPORO("192.168.1.105", "Sapporo", new Coordonnees(43.179025,
	// 141.388028)), //
	;

	private final String ip;
	private final String name;
	private final Coordonnees coordonnees;

	Noeud(String ip, String name, Coordonnees coordonnees) {
		this.ip = ip;
		this.name = name;
		this.coordonnees = coordonnees;
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void stop() {
		CassandraCluster.getInstance().stop(this.getIp());
		SparkCluster.getInstance().stop(this.getIp());
	}

}
