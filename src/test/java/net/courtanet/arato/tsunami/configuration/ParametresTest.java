package net.courtanet.arato.tsunami.configuration;

import static net.courtanet.arato.tsunami.configuration.Parametres.test;
import static net.courtanet.arato.tsunami.configuration.Parametres.test2;
import static net.courtanet.arato.tsunami.configuration.Parametres.testString;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ParametresTest {

	private Configuration configuration = Configuration
			.getConfiguration(ParametresTest.class
					.getResourceAsStream("test_param.properties"));

	@Test
	public void testType() {
		for (Parametres param : Parametres.values())
			param.getType().cast(param.getValeurParDefaut());
	}

	@Test
	public void testValeurDansEnum() {
		int param = Parametres.valeurDe(test, configuration);
		assertThat(param).isEqualTo(1);
	}

	@Test
	public void testValeurDansFichierConfig() {
		int param = Parametres.valeurDe(test2, configuration);
		assertThat(param).isEqualTo(4);
	}

	@Test
	public void testString() {
		String param = Parametres.valeurDe(testString, configuration);
		assertThat(param).isEqualTo("test");
	}

}
