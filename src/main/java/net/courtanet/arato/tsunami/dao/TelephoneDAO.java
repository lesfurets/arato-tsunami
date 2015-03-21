package net.courtanet.arato.tsunami.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class TelephoneDAO extends Dao {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH");

	public final static String TABLE_TELEPHONE = "telephone";

	private final static String COL_ANTENNE = "antenne";
	private final static String COL_TRANCHE_HORAIRE = "tranchehoraire";
	private final static String COL_TELEPHONES = "telephones";

	@Override
	protected String getTable() {
		return TABLE_TELEPHONE;
	}

	@Override
	protected boolean doitEtreSupprimee() {
		return true;
	}

	@Override
	protected String getRequeteCreation() {
		return "CREATE TABLE " + CassandraCluster.KEY_SPACE + "."
				+ TABLE_TELEPHONE + " (" + COL_ANTENNE + " text, "
				+ COL_TRANCHE_HORAIRE + " text, " + COL_TELEPHONES
				+ " SET<text>, PRIMARY KEY (" + COL_ANTENNE + ", "
				+ COL_TRANCHE_HORAIRE + "))";
	}

	// TODO needs test
	public Set<String> selectTelsToAlert(String antenne,
			LocalDateTime trancheHoraire) {
		Statement select = QueryBuilder//
				.select(COL_TELEPHONES)//
				.from(TABLE_TELEPHONE)//
				.where(eq(COL_ANTENNE, antenne))//
				.and(eq(COL_TRANCHE_HORAIRE, FORMATTER.format(trancheHoraire)));//

		ResultSet res = CassandraCluster.getInstance().getSession()
				.execute(select);

		Set<String> telsToAlert = new HashSet<>();
		for (Row r : res) {
			telsToAlert.addAll(r.getSet(COL_TELEPHONES, String.class));
		}
		return telsToAlert;
	}

}
