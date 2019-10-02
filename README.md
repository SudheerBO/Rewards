# Rewards

Instructions to run the code:

Clone the workspace and run following commands
mvn clean install
java -jar target\rewards-0.0.1-SNAPSHOT.jar

Two API's:

Lists supplied user transactions
http://localhost:8080/listtrans

Calculates Rewards based on supplied user transactions:
http://localhost:8080/getRewardPoints
