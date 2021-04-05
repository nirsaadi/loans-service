# loans-service

A java based web service for LoanStreet API.
Java version - 11
Spring Boot verison - 2.4.4
Maven - 3.6.1

## Install
To launch the server at http://localhost:8081/ do the following steps:
1. Download the project to your local machine
2. Navigate to the project's main folder
3. run the following command:
```bash
# Generating the .jar files for the embedded tomcat and project executable
mvn clean package spring-boot:repackage

# Starting web service
java -jar target\loans-service-0.0.1-SNAPSHOT.jar
```

## API
Once server started you can see the API documentation in swagger at http://localhost:8081/swagger-ui/

