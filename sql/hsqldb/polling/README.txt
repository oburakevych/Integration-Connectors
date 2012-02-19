# polling database installation

----------------------------------
-- Install & run hsql database ---
----------------------------------

1) download hsqldb.jar by executing the next command:
sudo wget http://repo1.maven.org/maven2/org/hsqldb/hsqldb/2.2.4/hsqldb-2.2.4.jar

2) make pooling diredctory by executing the next command:
sudo mkdir <PATH>/pooling

3) copy all files (except README.txt file) from this directory to <PATH>/pooling directory:
sudo cp * to <PATH>/pooling

4) install screen tool, please execute the next command 
sudo apt-get install screen

5) make a separate screen for hsqldb:
sudo screen -S hsqldb

6) run hsqldb in server mode by executing the next command:
java -cp hsqldb-2.2.4.jar org.hsqldb.Server -database.0 file:<PATH>/polling -dbname.0 polling -port 9001

7) leave screan
CTRL-A then d

P.S.: to read screan
screen -R hsqldb

8) check if screen is not detached
screen -list 

----------------------------------
--       Stop hsql database    ---
----------------------------------
To stop hsqldb please execute next commands:
1)
screen -R hsqldb
2)
CTRL-C

----------------------------------
--       Run hsqdb UI tool      ---
----------------------------------
1) run hsqdb UI tool
java -cp hsqldb-2.2.4.jar org.hsqldb.util.DatabaseManagerSwing

2) in "type" drop down select 
HSQL Database Engine Server

3) specify the next URL
jdbc:hsqldb:hsql://localhost:9001/polling

4) Credentials
user: SA
password: <leave empty>




