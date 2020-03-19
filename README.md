# BPM Project prototype

# Get it
Clone this github repository https://github.com/bpmetc20/ftd-bpm.git
```sh
$ git clone https://github.com/bpmetc20/ftd-bpm.git
```

# Build it
Install (if neccessary) [maven][maven-download].
Here's how to build the application.
```sh
$ cd ftd-bpm
$ mvn clean install
$ mvn package spring-boot:repackage
```
The resulting jar file target\bpm-0.0.1-SNAPSHOT.jar will be in the target directory

# Run it
```sh
$ java -jar target/bpm-0.0.1-SNAPSHOT.jar
```
# Use it
To be added

[maven-download]: http://maven.apache.org/install.html
