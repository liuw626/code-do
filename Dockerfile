FROM java:8
MAINTAINER godric
EXPOSE 80
ADD code-do/code-do-application/target/code-do-application-0.0.1-SNAPSHOT.jar code-do.jar
RUN bash -c 'touch /code-do.jar'
ENTRYPOINT ["java", "-jar", "/code-do.jar"]