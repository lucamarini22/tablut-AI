# TablutAI

Install Gradle.

Gradle Installation: https://gradle.org/install/


Go into the project folder:
```
cd TablutCompetition/Tablut
```

Run the server in a shell using the following command:
```
gradle Server
```

Run the white player in a new shell using this command:
```
gradle MarenTablutAI --args="white 60 127.0.0.1"
```
Then, run the black player in a new shell using this command:
```
gradle MarenTablutAI --args="black 60 127.0.0.1"
```
