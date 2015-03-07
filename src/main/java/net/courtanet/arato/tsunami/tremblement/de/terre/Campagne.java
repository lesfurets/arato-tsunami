package net.courtanet.arato.tsunami.tremblement.de.terre;

import static net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre.debutTremblement;
import static net.courtanet.arato.tsunami.tremblement.de.terre.TremblementDeTerre.nombrePrevenus;

import java.util.concurrent.locks.ReentrantLock;

import net.courtanet.arato.tsunami.dao.CampagneDAO;

public class Campagne extends Thread {

	public static final int DUREE_CAMPAGNE = 120000;// TODO mettre en parametre
	public static final int INTERVALLE_ENTRE_DEUX_ENREGISTREMENTS = 1000;// TODO
																			// mettre
																			// en
																			// parametre

	private final String nom;

	public Campagne(String nom) {
		super();
		this.nom = nom;
	}

	public static void lancerCampagne(String nom) {
		Campagne campagne = new Campagne(nom);
		campagne.start();
	}

	@Override
	public void run() {
		final CampagneDAO campagneDao = new CampagneDAO();
		final ReentrantLock lock = new ReentrantLock();
		while (System.currentTimeMillis() - debutTremblement < DUREE_CAMPAGNE) {
			lock.lock(); // Synchronisation
			long avancement = System.currentTimeMillis() - debutTremblement;
			int nombrePersonnesPrevenus = nombrePrevenus;
			lock.unlock();

			campagneDao.enregistrerAvancement(this.nom, avancement,
					nombrePersonnesPrevenus);
			try {
				sleep(INTERVALLE_ENTRE_DEUX_ENREGISTREMENTS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fin d'enregistrement pour la campagne " + this.nom);
	}
}
