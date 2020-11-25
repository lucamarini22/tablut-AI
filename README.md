# MarenTablutAI

### What is Tablut?

How to play -> [here](https://en.wikipedia.org/wiki/Tafl_games)


<p align="center"> 
   <img src="Tablut/src/main/resources/board-complete.png">
</p>



### Run MarenTablutAI

Download the **latest release** -> [here](https://github.com/lucamarini22/TablutAI/releases)

and then run:
`
java -jar Tablut-MarenAI.jar <black|white> <timeout-per-move-in-seconds> <server-ip>
`




### How to run the server

First install Gradle

Gradle Installation [here](https://gradle.org/install/)

Clone this project:
```
git clone https://github.com/lucamarini22/TablutAI
```

Go into the project folder:
```
cd TablutAI/Tablut
```

Run the **server**:
```
gradle Server
```

To visualize the **GUI**, run:
```
gradle Server --args="-g true"
```