package net.courtanet.arato.tsunami.ecran;

import java.time.LocalDateTime;

public interface Vue {

	public void demarrer();

	public LocalDateTime demanderMoment();

	public double entrerCoordonnee(String coordonnee, double min, double max);

	public String choixCampagne();

	public void afficherLigne(String aAfficher);

	public void afficher(String aAfficher);

	public void quitter();

}
