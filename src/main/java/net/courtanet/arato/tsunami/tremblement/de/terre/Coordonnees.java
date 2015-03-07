package net.courtanet.arato.tsunami.tremblement.de.terre;

public class Coordonnees {

	private static final int RAYON = 500; // TODO mettre en parametre
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
