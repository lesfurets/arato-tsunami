package net.courtanet.arato.tsunami.dao;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
// TODO sera important de se poser la question lors du passage a un cluster
// spark a plusieurs noeuds
public class TelephoneDTO implements Serializable {

	private String antenne;
	private String tranchehoraire;
	private Set<String> telephones;

	public TelephoneDTO() {
	}

	public TelephoneDTO(String antenne, String tranchehoraire,
			Set<String> telephones) {
		this.antenne = antenne;
		this.tranchehoraire = tranchehoraire;
		this.telephones = telephones;
	}

	public String getAntenne() {
		return this.antenne;
	}

	public void setAntenne(String antenne) {
		this.antenne = antenne;
	}

	public String getTranchehoraire() {
		return this.tranchehoraire;
	}

	public void setTranchehoraire(String tranchehoraire) {
		this.tranchehoraire = tranchehoraire;
	}

	public Set<String> getTelephones() {
		return this.telephones;
	}

	public void setTelephones(Set<String> telephones) {
		this.telephones = telephones;
	}
}