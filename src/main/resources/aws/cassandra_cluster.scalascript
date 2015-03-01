http://ec2-54-152-221-221.compute-1.amazonaws.com:8888/opscenter/index.html

 ssh -i /home/andrei/.ssh/cassandra-key-pair.pem ubuntu@54.152.221.221

sudo apt-get install lynx-cur


wget http://apache.crihan.fr/dist/spark/spark-1.2.0/spark-1.2.0-bin-hadoop2.4.tgz
tar xzf spark-1.2.0-bin-hadoop2.4.tgz



-- cassandra datastax driver compilation sbt/sbt-assembly

scp driver
[andrei@lomi cluster]$ scp  -i /home/andrei/.ssh/cassandra-key-pair.pem spark-cassandra-connector-assembly-1.1.2-SNAPSHOT.jar  ubuntu@54.152.221.221:/home/ubuntu/managed-lib/


-- create tables via cqlsh
CREATE KEYSPACE bigdata1 WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
use  bigdata1
CREATE TABLE positions (cellName text, date timestamp, tel text, PRIMARY KEY ((cellName,date),tel));
 INSERT INTO positions (cellName, date, tel) VALUES ('Osa_61','2014-01-04 17:10', '888888');

CREATE TABLE numerosParHeure ( date timestamp, tel text, PRIMARY KEY (date,tel));

select * from numerosparheure where date = '2015-01-23 18:00:00+0100' ;


-- spark verify access to s3

--- Install awscli + configuration
ubuntu@ip-172-31-57-250:~$ sudo pip install awscli

ubuntu@ip-172-31-57-250:~$ aws configure
ubuntu@ip-172-31-57-250:~$ aws configure
AWS Access Key ID [****************JV4A]:********
AWS Secret Access Key [****************IFoV]:********
Default region name [us-east-1]: us-east-1
Default output format [table]: table


-- aws verify access to s3 bucket (user + profile admin * *)
ubuntu@ip-172-31-57-250:~$ aws s3 cp s3://bigdata-paristech/projet2014/data/data_1MB.csv data_1MB.csv
download: s3://bigdata-paristech/projet2014/data/data_1MB.csv to ./data_1MB.csv


-- Start spark with cassandra test without s3/aws, standalone mode 

spark-1.2.0-bin-hadoop2.4/bin/spark-shell --jars /home/ubuntu/spark-cassandra-connector-assembly-1.1.2-SNAPSHOT.jar




import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector.cql.CassandraConnector
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
sc.stop
val sc = new SparkContext("local[1]", "test", conf) 
val input = sc.textFile("/home/ubuntu/data_1MB.csv").cache
val coordonnes = input.map(x=>{val tok=x.split(";");("'"+tok(1)+"'",tok(0).substring(0,16),"'"+tok(4)+"'")})
coordonnes.saveToCassandra("bigdata1","positions")



-- Read from AWS and save to Cassandra

export AWS_ACCESS_KEY_ID=**************;export AWS_SECRET_ACCESS_KEY=***********;spark-1.2.0-bin-hadoop2.4/bin/spark-shell --jars /home/ubuntu/spark-cassandra-connector-assembly-1.1.2-SNAPSHOT.jar

-- andrei credentials
export AWS_ACCESS_KEY_ID=**************;export AWS_SECRET_ACCESS_KEY=***********;spark-1.2.0-bin-hadoop2.4/bin/spark-shell --jars /home/ubuntu/spark-cassandra-connector-assembly-1.1.2-SNAPSHOT.jar



-- test read AWS
import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector.cql.CassandraConnector
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
sc.stop
val sc = new SparkContext("local[1]", "test", conf) 

val input = sc.textFile("s3n://bigdata-paristech/projet2014/data/data_1GB.csv").cache

val coordonnes = input.map(x=>{val tok=x.split(";");("'"+tok(1)+"'",tok(0).substring(0,14)+"00","'"+tok(4)+"'")})
coordonnes.saveToCassandra("bigdata1","positions")



-- test read AWS 10GB
import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector.cql.CassandraConnector
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
sc.stop
val sc = new SparkContext("local[1]", "test", conf) 

val input = sc.textFile("s3n://bigdata-paristech/projet2014/data/data_10GB.csv").cache

val coordonnes = input.map(x=>{val tok=x.split(";");("'"+tok(1)+"'",tok(0).substring(0,14)+"00","'"+tok(4)+"'")})
coordonnes.saveToCassandra("bigdata1","positions")

=> 6h



--- Query to Cassandra
CassandraConnector(conf).withSessionDo {session=> session.execute(s"select * from bigdata1.positions where cellname='Kaw_18' and date = '2015-01-19 14:00:00+0000'")}



--- Optim

import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector.cql.CassandraConnector
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
sc.stop
val sc = new SparkContext("local[1]", "test", conf) 

val input = sc.textFile("s3n://bigdata-paristech/projet2014/data/data_10GB.csv").cache
val coordonnes = input.map(x=>{val tok=x.split(";");("'"+tok(1)+"'",tok(0).substring(0,14)+"00:00+0000","'"+tok(4)+"'")})
coordonnes.saveToCassandra("bigdata1","positions")



--- Configuration cluster Spark:
ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ cp conf/spark-env.sh.template conf/spark-env.sh
ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ vi conf/spark-env.sh
-->

ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ mkdir ~/managed-lib/
ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ cd
ubuntu@ip-172-31-9-174:~$  mv spark-cassandra-connector-assembly-1.1.2-SNAPSHOT.jar ~/managed-lib/

=> Add
export SPARK_CLASSPATH=/home/ubuntu/managed-lib/*
*/
export SPARK_WORKER_INSTANCES=2 //


=> start master
ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ ./sbin/start-master.sh 

=> Start slave
ubuntu@ip-172-31-9-175:~/spark-1.2.0-bin-hadoop2.4$ ./bin/spark-class org.apache.spark.deploy.worker.Worker spark://ip-172-31-9-174:7077

=> Start shell on Spark master
ubuntu@ip-172-31-9-174:~/spark-1.2.0-bin-hadoop2.4$ export AWS_ACCESS_KEY_ID=****;export AWS_SECRET_ACCESS_KEY=********;./bin/spark-shell --master spark://ip-172-31-9-174:7077  --driver-class-path /home/ubuntu/managed-lib/*.jar



=>Spark on cluster

import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector.cql.CassandraConnector
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
sc.stop
val sc = new SparkContext("spark://ip-172-31-9-174:7077", "test", conf) 

val input = sc.textFile("s3n://bigdata-paristech/projet2014/data/data_1MB.csv")
val coordonnes = input.map(x=>{val tok=x.split(";");("'"+tok(1)+"'",tok(0).substring(0,14)+"00:00+0000","'"+tok(4)+"'")})
coordonnes.saveToCassandra("bigdata1","positions")

