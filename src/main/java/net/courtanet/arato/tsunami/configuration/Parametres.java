package net.courtanet.arato.tsunami.configuration;


public enum Parametres {
	/**
	 * Pour les tests
	 */
	test(Integer.class, 1), //
	test2(Integer.class, 1), //
	testString(String.class, "test"), //

	/* Cluster Cassandra */
	strategie_de_replication(String.class, "SimpleStrategy"), //
	facteur_de_replication(Integer.class, 1), //
	keyspace(String.class, "arato"), //

	/* Tremblement de terre */
	/**
	 * Rayon dans lequel le se trouvent les noeuds à éteindre ainsi que les
	 * personnes à prévenir. (unité : km)
	 */
	rayon(Integer.class, 500), //

	/* Campagnes */
	/**
	 * Temps limite au bout duquel s'arrête l'enregistrement d'une campagne, si
	 * elle n'a pas été arrêtée avant (unité : ms)
	 */
	duree_campagne_max(Integer.class, 120000), //
	/**
	 * Intervalle entre deux enregistrements de suivi de campagne d'envoi de SMS
	 * (unité : ms)
	 */
	intervalle_entre_deux_enregistrements(Integer.class, 1000), //

	;

	private final Class<?> type;
	private final Object valeurParDefaut;

	Parametres(Class<?> type, Object valeurParDefaut) {
		this.type = type;
		this.valeurParDefaut = valeurParDefaut;
	}

	@SuppressWarnings("unchecked")
	static <T> T valeurDe(Parametres parametre, Configuration configuration) {
		T valeur = null;
		try {
			String param = configuration.getParametre(parametre.name());

			if (parametre.type.getName().equals("java.lang.String"))
				valeur = (T) param;
			else if (parametre.type.getName().equals("java.lang.Integer"))
				valeur = (T) new Integer(Integer.parseInt(param));

		} catch (Exception e) {
			return (T) parametre.valeurParDefaut;
		}
		if (valeur == null)
			return (T) parametre.valeurParDefaut;
		else
			return valeur;
	}

	public static <T> T valeurDe(Parametres parametre) {
		return valeurDe(parametre, Configuration.getConfiguration());
	}

	/**
	 * VisibleForTesting
	 */
	@SuppressWarnings("unchecked")
	<T> T getValeurParDefaut() {
		return (T) this.type.cast(this.valeurParDefaut);
	}

	/**
	 * VisibleForTesting
	 */
	public Class<?> getType() {
		return this.type;
	}

}
