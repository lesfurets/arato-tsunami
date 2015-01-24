package net.courtanet.arato.tsunami.tremblement.de.terre;

public class Coordonnees {

	private final String latitude;
	private final String longitude;

	public Coordonnees(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
}
