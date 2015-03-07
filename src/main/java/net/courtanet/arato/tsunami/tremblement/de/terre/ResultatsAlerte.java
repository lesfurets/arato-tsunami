package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.courtanet.arato.tsunami.dao.CampagneDAO;
import net.courtanet.arato.tsunami.dao.PasDeCampagneException;
import net.courtanet.arato.tsunami.ecran.SystemeEcran;

public class ResultatsAlerte {

	public void afficher() {
		System.out.println("Sélection de la campagne à afficher");
		String nomCampagne = choixCampagne();

		System.out.println("Affichage résultats de la campagne : "
				+ nomCampagne);
		afficherResultats(nomCampagne);
	}

	private String choixCampagne() {
		System.out.println("De quelle campagne afficher les résultats ?");
		String saisie = "";
		final DateTimeFormatter formatter = DateTimeFormatter//
				.ofPattern("yyyy-MM-dd HH:mm:ss")//
				.withLocale(Locale.FRANCE);
		final String message = "Veuillez entrer une date de campagne au format aaaa-mm-jj hh:mm:ss";
		boolean encore = true;
		while (encore) {
			System.out.println(message);// TODO exit si trop de tentative

			while (!SystemeEcran.input.hasNext()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				SystemeEcran.input.next();
			}
			saisie = SystemeEcran.input.next() + " "
					+ SystemeEcran.input.next();

			try {
				formatter.parse(saisie);
				encore = false;
			} catch (DateTimeParseException e) {
				// On continue
			}
		}
		return "campagne" + saisie;
	}

	private void afficherResultats(String nomCampagne) {
		CampagneDAO campagneDao = new CampagneDAO();
		TreeMap<Integer, Integer> enregistrements = new TreeMap<>(//
				(Integer i1, Integer i2) -> i1.compareTo(i2));
		try {
			enregistrements.putAll(campagneDao
					.getResultatsCampagne(nomCampagne));

			System.out.print("temps (ms) | nombre de prévenus");
			System.out.print("-----------+-------------------");
			for (Entry<Integer, Integer> enregistrement : enregistrements
					.entrySet()) {
				String label = Integer.toString(enregistrement.getKey());
				while (label.length() < 10)
					label = " " + label;
				System.out.print(label + " : ");
				for (int i = 0; i < enregistrement.getValue(); i++)
					System.out.print("*");
				System.out.println("");
			}
		} catch (PasDeCampagneException e) {
			System.out.println("Campagne " + nomCampagne + " pas trouvée.");
		}
	}
}
