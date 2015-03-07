package net.courtanet.arato.tsunami.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.util.HashMap;
import java.util.Map;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;

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

	public Map<Integer, Integer> getResultatsCampagne(String nomCampagne)
			throws PasDeCampagneException {
		Map<Integer, Integer> resultats = new HashMap<>();
		Where select = QueryBuilder//
				.select().all().from(TABLE_CAMPAGNE)//
				.where(eq(COL_CAMPAGNE, nomCampagne));
		ResultSet res = CassandraCluster.getInstance().getSession()
				.execute(select);

		if (res.all().isEmpty())
			throw new PasDeCampagneException();
		for (Row row : res)
			resultats.put(row.getInt(COL_AVANCEMENT),
					row.getInt(COL_NOMBRE_PREVENUS));

		return resultats;
	}

}
