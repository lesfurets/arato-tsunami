package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.courtanet.arato.tsunami.dao.CampagneDAO;
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
				.ofPattern("yyyy-MM-dd HH:mm:ss");
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
		enregistrements.putAll(campagneDao.getResultatsCampagne(nomCampagne));

		for (Entry<Integer, Integer> enregistrement : enregistrements
				.entrySet()) {
			Integer moment = enregistrement.getKey();
			String label = Integer.toString(moment);
			while (label.length() < 10)
				label = " " + label;
			System.out.print(label + " : ");
			for (int i = 0; i < enregistrement.getValue(); i++)
				System.out.print("*");
			System.out.println("");
		}
	}
}
