package net.courtanet.arato.tsunami.dao;

import java.util.List;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class TelephoneDAO {

	private final String TABLE_TELEPHONE = "telephone";

	private final String COL_ANTENNE = "antenne";
	private final String COL_TRANCHE_HORAIRE = "tranchehoraire";
	private final String COL_TELEPHONES = "telephones";

	public void createTable() {
		String create = "CREATE TABLE " + CassandraCluster.KEY_SPACE + "."
				+ TABLE_TELEPHONE + " (" + COL_ANTENNE + " text, "
				+ COL_TRANCHE_HORAIRE + " test, " + COL_TELEPHONES
				+ " LIST<text>, PRIMARY KEY (antenne, tranchehoraire))";
		CassandraCluster.getInstance().getSession().execute(create);
	}

	public void inserer(List<TelephoneDTO> telephones) {
		for (TelephoneDTO tel : telephones) {
			Statement insert = QueryBuilder.insertInto(TABLE_TELEPHONE)//
					.value(COL_ANTENNE, tel.getCodeAntenne())//
					.value(COL_TRANCHE_HORAIRE, tel.getTrancehHoraire())//
					.value(COL_TELEPHONES, tel.getTelephones());
			CassandraCluster.getInstance().getSession().execute(insert);
		}
	}

	public class TelephoneDTO {
		private final String codeAntenne;
		private final String trancehHoraire;
		private final List<String> telephones;

		public TelephoneDTO(String codeAntenne, String trancehHoraire,
				List<String> telephones) {
			this.codeAntenne = codeAntenne;
			this.trancehHoraire = trancehHoraire;
			this.telephones = telephones;
		}

		public String getCodeAntenne() {
			return codeAntenne;
		}

		public String getTrancehHoraire() {
			return trancehHoraire;
		}

		public List<String> getTelephones() {
			return telephones;
		}
	}

}
