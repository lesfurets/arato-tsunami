# Arato Tsunami

Un trrrremlbe ment d e trerrre vient de se produire au large du Japon. Sa violence a détruit tous les systèmes informatiques dans un rayon de 500 km autour de l'épicentre. Un tsunami se déclenche et fonce tout droit sur Tokyo. Les habitants n'ont qu'une heure pour se mettre à l'abri avant que la gigantesque vague n'engloutisse la ville. Arato Tsunami doit alerter par SMS les habitants du danger imminent.

Arato Tsunami est un système distribué d'alerte par SMS. Il s'appuie sur la technologie Cassandra. Le tremblement de terre affecte le cluster en détruisant les noeuds le plus proches de l'épicentre, les rendant indisponibles pour alerter les habitants.

Le projet Arato Tsunami a été donné a des étudiants dans le cadre d'un cours sur les basses de données NoSQL et bigdata. Il vient ponctuer le cours et mettre en application les technologies abordées. Les détails du projet se trouvent [ici](https://github.com/lesfurets/arato-tsunami/projet.pdf). Pour plus d'info sur le cours : [projet bigdata](https://github.com/AndreiArion/bigdata)

## Installation

### Environnement

Ce projet nécessite :
* Java 8 [téléchargement](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Maven 3 [téléchargement](http://maven.apache.org/download.cgi)
* Cassandra 2.1 [téléchargement](http://cassandra.apache.org/download)

### Construction

    git clone https://github.com/lesfurets/arato-tsunami.git
    cd arato-tsunami
    mvn install

Le projet peut facilement se lancer dans votre IDE préféré. Il est testé avec Eclipse Luna et Intellij IDEA 14.

Avant d'utiliser le programme, Cassandra doit être lancée.

## Exemple

Pour tester le programme, on peut utiliser ce tremblement de terre :
* latitude : 35
* longitude : 140
* date : 2015/02/27 18:23:32

