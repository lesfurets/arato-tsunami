package net.courtanet.arato.tsunami.ecran;

import net.courtanet.arato.tsunami.AratoTsunami;

public class Quitter extends Ecran {

    Quitter() {
        super("Quitter");
    }

    @Override
    public void action() {
        System.out.println("Sayonara.");
        AratoTsunami.input.close();
        System.exit(0);
    }

}
