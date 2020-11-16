# TablutAI

First install Gradle

Gradle Installation: https://gradle.org/install/

### Gradle Installation on Debian

```
sudo apt update
```

```
sudo apt install default-jdk
```

```
sudo apt install curl
```

```
sudo apt install zip
```

```
curl -s "https://get.sdkman.io" | bash
```

```
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

```
sdk install gradle
```

### Import and run MarenTablutAI

Clone this project:
```
git clone https://github.com/lucamarini22/TablutAI
```


Go into the project folder:
```
cd TablutAI/Tablut
```

Run the **server** in a shell using the following command:
```
gradle Server
```

To see the **GUI** , run the server using this command:
```
gradle Server --args="-g true"
```


Run the **white player** in a new shell using this command:
```
gradle MarenTablutAI --args="white 60 127.0.0.1"
```
Then, run the **black player** in a new shell using this command:
```
gradle MarenTablutAI --args="black 60 127.0.0.1"
```
