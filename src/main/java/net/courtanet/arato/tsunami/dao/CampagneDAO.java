package net.courtanet.arato.tsunami.dao;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class CampagneDAO extends Dao {

	public final static String TABLE_CAMPAGNE = "campagne";

	private final static String COL_CAMPAGNE = "campagne";
	private final static String COL_AVANCEMENT = "avancement";
	private final static String COL_NOMBRE_PREVENUS = "nombreprevenus";

	@Override
	protected String getTable() {
		return TABLE_CAMPAGNE;
	}

	@Override
	protected boolean doitEtreSupprimee() {
		return false;
	}

	@Override
	protected String getRequeteCreation() {
		return "CREATE TABLE IF NOT EXISTS " + CassandraCluster.KEY_SPACE + "."
				+ TABLE_CAMPAGNE + " (" + COL_CAMPAGNE + " text, "
				+ COL_AVANCEMENT + " int, " + COL_NOMBRE_PREVENUS
				+ " int, PRIMARY KEY (" + COL_CAMPAGNE + ", " + COL_AVANCEMENT
				+ "))";
	}

	public void enregistrerAvancement(String campagne, long avancement,
			int nombrePersonnesPrevenus) {
		Insert insert = QueryBuilder//
				.insertInto(TABLE_CAMPAGNE)//
				.value(COL_CAMPAGNE, campagne)//
				.value(COL_AVANCEMENT, avancement)//
				.value(COL_NOMBRE_PREVENUS, nombrePersonnesPrevenus);
		CassandraCluster.getInstance().getSession().executeAsync(insert);
	}

}
