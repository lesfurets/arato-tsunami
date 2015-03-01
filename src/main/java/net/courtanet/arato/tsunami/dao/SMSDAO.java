package net.courtanet.arato.tsunami.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class SMSDAO extends Dao {

	public final static String TABLE_SMS = "sms";

	private final static String COL_TELEPHONE = "telephone";
	private final static String COL_DATE_RECEPTION = "datereception";

	@Override
	protected String getCreateStatement() {
		return "CREATE TABLE " + CassandraCluster.KEY_SPACE + "." + TABLE_SMS
				+ " (" + COL_TELEPHONE + " text, " + COL_DATE_RECEPTION
				+ " text, PRIMARY KEY (" + COL_TELEPHONE + ", "
				+ COL_DATE_RECEPTION + "))";
	}

	public void envoyerSMS(Set<String> telephones) {
		for (String telephone : telephones) {
			String dateReception = LocalDateTime.now().format(
					DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			Insert insert = QueryBuilder.insertInto(TABLE_SMS)//
					.value(COL_TELEPHONE, telephone)//
					.value(COL_DATE_RECEPTION, dateReception);
			CassandraCluster.getInstance().getSession().executeAsync(insert);
		}
	}

}
