# Profily
## to run application

can download it from here
[https://drive.google.com/file/d/1tSlAhxHhMUSPahFd8em6QO3xAm2nT3nV/view?usp=sharing](https://drive.google.com/file/d/1tSlAhxHhMUSPahFd8em6QO3xAm2nT3nV/view?usp=sharing)

java -jar boot-react-example-0.0.1-SNAPSHOT.jar
http://localhost:8080/


## to build new .jar from sources
cd profily-ui
npm run build
cd ../profily-core
mvn clean install
copy all from profily-ui/build to profily-core/target/classes/static
mvn package
cd target

