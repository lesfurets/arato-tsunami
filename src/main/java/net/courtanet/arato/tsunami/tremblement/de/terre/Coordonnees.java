package net.courtanet.arato.tsunami.tremblement.de.terre;

import static net.courtanet.arato.tsunami.configuration.Parametres.rayon;
import net.courtanet.arato.tsunami.configuration.Parametres;

public class Coordonnees {

	private static final int RAYON = Parametres.valeurDe(rayon);
	private final double latitude;
	private final double longitude;

	public Coordonnees(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public static boolean isNextToTheEpicenter(Coordonnees epicentre,
			Coordonnees coord) {
		double lat = coord.getLatitude() - epicentre.getLatitude();
		double lon = coord.getLongitude() - epicentre.getLongitude();
		return lat * lat + lon * lon <= RAYON * RAYON;
	}
}
