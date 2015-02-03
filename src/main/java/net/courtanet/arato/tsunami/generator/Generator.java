/**
 * Copyright Courtanet
 */
package net.courtanet.arato.tsunami.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator {

    private static final String FILE_NAME = "/mnt/HDD/che/data_1Go.csv";
    private static final String CSV_SEPARATOR = ";";
    private static final String BR = System.lineSeparator();

    private static final long FILE_SIZE = 1; // Go
    private static final int NBR_LINES = 1000;

    private static final List<String> LINES = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File doc = new File(FILE_NAME);
        doc.createNewFile();
        OutputStreamWriter docWriter = new FileWriter(FILE_NAME);
        BufferedWriter bw = new BufferedWriter(docWriter);
        while (getSize(doc) < FILE_SIZE) {
            generateLines();
            writeLines(bw);
        }
        bw.close();
        docWriter.close();
        System.out.println("Done.");
    }

    private static long getSize(File doc) {
        long gigabytes = doc.length() / 1073741824;
        System.out.println(doc.length() / 1024 + "Ko");
        return gigabytes;
    }

    private static void generateLines() {
        LINES.clear();
        while (LINES.size() < NBR_LINES) {
            LINES.add(getTimestamp() + CSV_SEPARATOR //
                            + getCodeCelluleGSM(Coordonnees.getAnyCoordonnees()) + CSV_SEPARATOR //
                            + getTelephone());
        }
    }

    private static final DateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    private static String getTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, (int) (Math.random() * 30));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 23));
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MINUTE, (int) (Math.random() * 59));
        return SDF.format(cal.getTime()) + "";
    }

    private final static Map<String, String> CELLULE_GSM = new HashMap<>();

    private static String getCodeCelluleGSM(Coordonnees coord) {
        int alea = (int) (Math.random() * 100);
        String code = (alea < 10 ? "0" : "") + alea;
        final String codeAntenne = coord.name().substring(0, 3) + "_" + code;
        if (!CELLULE_GSM.containsKey(codeAntenne)) {
            double aleaLat = Math.random() - .5;
            String lat = Double.parseDouble(coord.getLatitude()) + aleaLat + "";
            double aleaLongi = Math.random() - .5;
            String longi = Double.parseDouble(coord.getLongitude()) + aleaLongi + "";
            String value = codeAntenne + CSV_SEPARATOR + lat.substring(0, 9) + CSV_SEPARATOR + longi.substring(0, 10);
            CELLULE_GSM.put(codeAntenne, value);
        }
        return CELLULE_GSM.get(codeAntenne);
    }

    private static String getTelephone() {
        double alea = Math.random();
        String tel = "8" + Double.toString(alea).substring(2, 7);
        return tel;
    }

    private static void writeLines(BufferedWriter bw) throws IOException {
        for (String line : LINES) {
            bw.append(line);
            bw.append(BR);
        }
    }

    private static enum Coordonnees {

        Tokyo("35.732727", "139.722404"), //
        Yokohama("35.462635", "139.774854"), //
        Osaka("34.705359", "135.500729"), //
        Nagoya("35.193866", "136.907394"), //
        Sapporo("43.179025", "141.388028"), //
        Kobe("34.699714", "135.187619"), //
        Fukuoka("33.643127", "130.355035"), //
        Kyoto("35.043493", "135.771593"), //
        Kawasaki("35.557485", "139.698357"), //
        Saitama("35.867481", "139.642576"), //

        ;

        private final String latitude;
        private final String longitude;

        private Coordonnees(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Coordonnees getAnyCoordonnees() {
            int random = (int) (Math.random() * Coordonnees.values().length);
            for (Coordonnees coord : Coordonnees.values()) {
                if (coord.ordinal() == random)
                    return coord;
            }
            return Coordonnees.Tokyo;
        }

        private String getLatitude() {
            return this.latitude;
        }

        private String getLongitude() {
            return this.longitude;
        }
    }
}
