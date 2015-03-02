package net.courtanet.arato.tsunami.dao;

import static net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre.debutTremblement;

import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class SMSDAO extends Dao {

	public final static String TABLE_SMS = "sms";

	private final static String COL_TELEPHONE = "telephone";
	private final static String COL_DATE_RECEPTION = "receptionapres";

	@Override
	protected String getTable() {
		return TABLE_SMS;
	}

	@Override
	protected String getCreateStatement() {
		return "CREATE TABLE " + CassandraCluster.KEY_SPACE + "." + TABLE_SMS
				+ " (" + COL_TELEPHONE + " text, " + COL_DATE_RECEPTION
				+ " double, PRIMARY KEY (" + COL_TELEPHONE + ", "
				+ COL_DATE_RECEPTION + "))";
	}

	public void envoyerSMS(Set<String> telephones) {
		for (String telephone : telephones) {
			Insert insert = QueryBuilder//
					.insertInto(TABLE_SMS)//
					.value(COL_TELEPHONE, telephone)//
					.value(COL_DATE_RECEPTION,
							System.currentTimeMillis() - debutTremblement);
			CassandraCluster.getInstance().getSession().executeAsync(insert);
		}
	}

}
