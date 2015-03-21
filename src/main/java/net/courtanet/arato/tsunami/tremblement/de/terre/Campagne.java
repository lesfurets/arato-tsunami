package net.courtanet.arato.tsunami.tremblement.de.terre;

import static net.courtanet.arato.tsunami.configuration.Parametres.duree_campagne_max;
import static net.courtanet.arato.tsunami.configuration.Parametres.intervalle_entre_deux_enregistrements;
import static net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre.debutTremblement;

import java.util.concurrent.locks.ReentrantLock;

import net.courtanet.arato.tsunami.configuration.Parametres;
import net.courtanet.arato.tsunami.dao.CampagneDAO;

class Campagne extends Thread {
	/**
	 * TODO mettre en parametre, cette valeur doit etre vraiment haute pour ne
	 * pas terminer l'enregistrement alors que la campagne est toujours en
	 * cours. Elle permet d'arreter une campagne qui tournerait a l'infini.
	 */
	public static final int DUREE_CAMPAGNE_MAX = Parametres
			.valeurDe(duree_campagne_max);
	public static final int INTERVALLE_ENTRE_DEUX_ENREGISTREMENTS = Parametres
			.valeurDe(intervalle_entre_deux_enregistrements);

	private final String nom;
	private final ReentrantLock lock = new ReentrantLock();
	private final CampagneDAO campagneDao = new CampagneDAO();
	private boolean stop = false;
	public int nombrePrevenus = 0;

	public Campagne(String nom) {
		super();
		this.nom = nom;
	}

	public static Campagne lancerCampagne(String nom) {
		Campagne campagne = new Campagne(nom);
		campagne.start();
		return campagne;
	}

	@Override
	// TODO needs test
	public void run() {
		while (!stop
				&& System.currentTimeMillis() - debutTremblement < DUREE_CAMPAGNE_MAX) {
			enregistrer();

			try {
				sleep(INTERVALLE_ENTRE_DEUX_ENREGISTREMENTS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		enregistrer();// Un dernier enregistrement pour etre
						// sur de ne rien oublier
		System.out.println("Fin d'enregistrement pour la campagne " + this.nom);
	}

	private void enregistrer() {
		lock.lock(); // Synchronisation
		long avancement = System.currentTimeMillis() - debutTremblement;
		campagneDao.enregistrerAvancement(this.nom, avancement,
				this.nombrePrevenus);
		lock.unlock();
	}

	// TODO needs test
	public void stopper() {
		this.stop = true;
	}

	public void ajouterNombrePrevenus(int nombrePrevenusSupplementaires) {
		this.nombrePrevenus += nombrePrevenusSupplementaires;
	}

	public int getNombrePrevenus() {
		return this.nombrePrevenus;
	}
}
