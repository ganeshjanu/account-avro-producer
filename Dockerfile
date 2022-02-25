# For Java 8, try this
FROM openjdk:8-jdk-alpine

COPY target/account-avro-producer-0.0.1-SNAPSHOT.jar account-avro-producer.jar

ENTRYPOINT ["java","-jar","account-avro-producer.jar"]