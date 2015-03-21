package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.util.Map.Entry;
import java.util.TreeMap;

import net.courtanet.arato.tsunami.dao.CampagneDAO;
import net.courtanet.arato.tsunami.dao.PasDeCampagneException;
import net.courtanet.arato.tsunami.ecran.Vue;

public class ResultatsAlerte {

	private final Vue vue;

	public ResultatsAlerte(Vue vue) {
		this.vue = vue;
	}

	public void afficher() {
		vue.afficherLigne("Sélection de la campagne à afficher");
		String nomCampagne = vue.choixCampagne();

		vue.afficherLigne("Affichage résultats de la campagne : " + nomCampagne);
		afficherResultats(nomCampagne);
	}

	private void afficherResultats(String nomCampagne) {
		CampagneDAO campagneDao = new CampagneDAO();
		TreeMap<Integer, Integer> enregistrements = new TreeMap<>(//
				(Integer i1, Integer i2) -> i1.compareTo(i2));
		try {
			enregistrements.putAll(campagneDao
					.getResultatsCampagne(nomCampagne));

			vue.afficherLigne("temps (ms) | nombre de prévenus");
			vue.afficherLigne("-----------+-------------------");
			for (Entry<Integer, Integer> enregistrement : enregistrements
					.entrySet()) {
				String label = Integer.toString(enregistrement.getKey());
				while (label.length() < 10)
					label = " " + label;
				vue.afficher(label + " : ");
				for (int i = 0; i < enregistrement.getValue(); i++)
					vue.afficher("*");
				vue.afficherLigne("");
			}
		} catch (PasDeCampagneException e) {
			vue.afficherLigne("Campagne " + nomCampagne + " pas trouvée.");
		}
	}
}
