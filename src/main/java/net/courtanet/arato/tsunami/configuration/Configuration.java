package net.courtanet.arato.tsunami.configuration;

import java.io.InputStream;
import java.util.Properties;

final class Configuration {

	private static final String FICHIER_DE_CONFIGURATION = "config.properties";

	private static Configuration INSTANCE;
	private final Properties configuration = new Properties();

	private Configuration(InputStream fichier) {
		try {
			configuration.load(fichier);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	static Configuration getConfiguration() {
		return getConfiguration(Configuration.class
				.getResourceAsStream(FICHIER_DE_CONFIGURATION));
	}

	static Configuration getConfiguration(InputStream fichier) {
		if (INSTANCE == null)
			return new Configuration(fichier);
		else
			return INSTANCE;
	}

	String getParametre(String parametre) {
		return this.configuration.getProperty(parametre);
	}
}
