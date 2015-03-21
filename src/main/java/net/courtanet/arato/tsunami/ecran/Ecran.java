package net.courtanet.arato.tsunami.ecran;

import java.util.ArrayList;
import java.util.List;

public abstract class Ecran {

	protected final List<Ecran> NAVIGATION = new ArrayList<>();
	protected final Vue vue;

	protected final String titre;

	public Ecran(String titre, Vue vue) {
		this.titre = titre;
		this.vue = vue;
	}

	// TODO needs test
	public void afficher() {
		for (int i = 0; i < this.NAVIGATION.size(); i++) {
			System.out.println(i + 1 + " : "
					+ this.NAVIGATION.get(i).getTitre());
		}
	}

	public Ecran naviguer(int choix) {
		return this.NAVIGATION.get(choix - 1);
	}

	public int getMaxChoix() {
		return this.NAVIGATION.size();
	}

	public String getTitre() {
		return this.titre;
	}

	public void ajouterEcran(Ecran ecran) {
		this.NAVIGATION.add(ecran);
	}

	public abstract void action();
}
