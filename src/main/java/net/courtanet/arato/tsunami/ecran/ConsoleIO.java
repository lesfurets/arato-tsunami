package net.courtanet.arato.tsunami.ecran;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleIO {

	public static Scanner input;

	public ConsoleIO() {
		this(new Scanner(System.in));
	}

	ConsoleIO(Scanner input) {
		ConsoleIO.input = input;
	}

	// TODO needs test
	public int faireUnChoixInt(String type, int min, int max)
			throws MaxTentativeException {
		final String message = "Veuillez entrer une " + type + " entre " + min
				+ " et " + max;
		int choix = 0;
		int nbrTentatives = 0;
		final int maxTentatives = 10;

		while (nbrTentatives < maxTentatives && //
				(choix > max || choix < min)) {
			nbrTentatives++;
			System.out.println(message);

			while (!input.hasNextInt()) {
				nbrTentatives++;
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				input.next();
			}
			choix = input.nextInt();
		}

		if (nbrTentatives == maxTentatives)
			throw new MaxTentativeException("Nombre de tentative max ("
					+ maxTentatives + ") atteint.");
		return choix;
	}

	// TODO needs test
	public double faireUnChoixDouble(String type, double min, double max)
			throws MaxTentativeException {
		final String message = "Veuillez entrer une " + type + " entre " + min
				+ " et " + max;
		double choix = 0;
		int nbrTentatives = 0;
		final int maxTentatives = 10;

		while (nbrTentatives < maxTentatives && //
				(choix > max || choix < min)) {
			nbrTentatives++;
			System.out.println(message);

			while (!input.hasNextDouble()) {
				nbrTentatives++;
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				input.next();
			}
			choix = input.nextDouble();
		}

		if (nbrTentatives == maxTentatives)
			throw new MaxTentativeException("Nombre de tentative max ("
					+ maxTentatives + ") atteint.");
		return choix;
	}

	// TODO needs test
	public String choixCampagne() throws MaxTentativeException {
		System.out.println("De quelle campagne afficher les résultats ?");
		String saisie = "";
		int nbrTentatives = 0;
		final int maxTentatives = 10;
		final DateTimeFormatter formatter = DateTimeFormatter//
				.ofPattern("yyyy-MM-dd HH:mm:ss")//
				.withLocale(Locale.FRANCE);
		final String message = "Veuillez entrer une date de campagne au format aaaa-mm-jj hh:mm:ss";
		boolean encore = true;
		while (nbrTentatives < maxTentatives && //
				encore) {
			System.out.println(message);

			while (!input.hasNext()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				input.next();
			}
			saisie = input.next() + " " + input.next();

			try {
				formatter.parse(saisie);
				encore = false;
			} catch (DateTimeParseException e) {
				// On continue
			}
		}

		if (nbrTentatives == maxTentatives)
			throw new MaxTentativeException("Nombre de tentative max ("
					+ maxTentatives + ") atteint.");
		return "campagne" + saisie;
	}

	// TODO needs test
	public LocalDateTime demanderMoment() {
		System.out.println("Quand vouler-vous faire trembler la terre ?");
		LocalDateTime moment = null;
		String saisie = "";
		String message = "Veuillez entrer une date au format aaaa/mm/jj hh:mm:ss";
		final DateTimeFormatter FORMATTER = DateTimeFormatter//
				.ofPattern("yyyy/MM/dd HH:mm:ss")//
				.withLocale(Locale.FRANCE);
		boolean encore = true;
		while (encore) {
			System.out.println(message);// TODO exit si trop de tentative

			while (!ConsoleIO.input.hasNext()) {
				System.out.println("Erreur de saisie.");
				System.out.println(message);
				ConsoleIO.input.next();
			}
			saisie = ConsoleIO.input.next() + " " + ConsoleIO.input.next();

			try {
				moment = LocalDateTime.from(FORMATTER.parse(saisie));
				encore = false;
			} catch (Exception e) {
				// On continue
			}
		}
		return moment;
	}

	public void quitter() {
		input.close();
	}

}
