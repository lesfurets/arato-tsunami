package net.courtanet.arato.tsunami.tremblement.de.terre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

import net.courtanet.arato.tsunami.cluster.CassandraCluster;
import net.courtanet.arato.tsunami.cluster.SparkCluster;
import net.courtanet.arato.tsunami.dao.TelephoneDAO;

public class ChargementDonnees implements Serializable {

    private final String DATA_PATH = "/mnt/HDD/che/data_1Mo.csv";

    public void charger() {
        System.out.println("Creation des schémas dans Cassandra");

        CassandraCluster cassandraCluster = CassandraCluster.getInstance();
        cassandraCluster.init();

        System.out.println("Chargement des données avec Spark");

        SparkCluster sparkCluster = SparkCluster.getInstance();
        sparkCluster.init();

        JavaRDD<Dto> distributedData = sparkCluster.getContext().textFile(DATA_PATH)
                        .map(str -> new Dto("che", "tranche", new ArrayList<String>()));

        // Insertion des donnees dans Cassandra
        CassandraJavaUtil
                        .javaFunctions(distributedData)
                        .writerBuilder(CassandraCluster.KEY_SPACE, TelephoneDAO.TABLE_TELEPHONE,
                                        CassandraJavaUtil.mapToRow(Dto.class)).saveToCassandra();

    }

    public class Dto implements Serializable {

        private String antenne;
        private String tranchehoraire;
        private List<String> telephones;

        public Dto() {
        }

        public Dto(String antenne, String tranchehoraire, List<String> telephones) {
            this.antenne = antenne;
            this.tranchehoraire = tranchehoraire;
            this.telephones = telephones;
        }

        public String getAntenne() {
            return this.antenne;
        }

        public void setAntenne(String antenne) {
            this.antenne = antenne;
        }

        public String getTranchehoraire() {
            return this.tranchehoraire;
        }

        public void setTranchehoraire(String tranchehoraire) {
            this.tranchehoraire = tranchehoraire;
        }

        public List<String> getTelephones() {
            return this.telephones;
        }

        public void setTelephones(List<String> telephones) {
            this.telephones = telephones;
        }
    }

}
