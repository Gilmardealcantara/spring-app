# Spring boot Api

## Run
```
mvn clean install
mvn spring-boot:run
```

## Adding Classpath Dependencies
```
mvn dependency:tree
```

## Create jar
```
mvn package
java -jar target/myproject-0.0.1-SNAPSHOT.jar
```

## Test
```
curl localhost:8080
mvn test
```

## Docker
```
docker build -t forum .   
docker run -p 8080:8080 forum
```

## Technologies
- API Rest
- Swagger
- Heath checks 
    - Actuator
    - spring boot admin
- Unit and Integration Tests
- Security with JWT tokens 
- Bean Validation
- Sprint data for repositories
- Sprint cache