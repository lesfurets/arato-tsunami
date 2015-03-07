package net.courtanet.arato.tsunami.configuration;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void test() {
		Configuration config = Configuration
				.getConfiguration(ConfigurationTest.class
						.getResourceAsStream("test_config.properties"));
		assertThat(config.getParametre("param")).isEqualTo("valeur");
		assertThat(config.getParametre("paramDouble")).isEqualTo("2");
		assertThat(config.getParametre("para")).isNull();
		assertThat(config.getParametre("paramdouble")).isNull();
	}

}
