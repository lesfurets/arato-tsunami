package net.courtanet.arato.tsunami.dao;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

public abstract class Dao {

	public void createTable() {
		CassandraCluster.getInstance().getSession()
				.execute(getCreateStatement());
	}

	protected abstract String getCreateStatement();

}
