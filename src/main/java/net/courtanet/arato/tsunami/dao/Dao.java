package net.courtanet.arato.tsunami.dao;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

public abstract class Dao {

	public void createTable() {
		System.out.println("Suppression ancienne table " + getTable());
		CassandraCluster.getInstance().getSession()
				.execute("DROP TABLE IF EXISTS " + getTable() + ";");
		System.out.println("Création table " + getTable());
		CassandraCluster.getInstance().getSession()
				.execute(getCreateStatement());
	}

	protected abstract String getTable();

	protected abstract String getCreateStatement();

}
