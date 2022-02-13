FROM java:8
FROM maven:3.6.0-jdk-8-slim as build
MAINTAINER godric
EXPOSE 80
RUN ls
RUN mvn ./pom.xml clean package
ADD ./code-do-application/target/code-do-application-0.0.1-SNAPSHOT.jar code-do.jar
RUN bash -c 'touch /code-do.jar'
ENTRYPOINT ["java", "-jar", "/code-do.jar"]