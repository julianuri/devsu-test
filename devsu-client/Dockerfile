FROM maven:3.9-amazoncorretto-17

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install
RUN mv /home/app/target/devsu-client-*.jar /home/app/devsu-client.jar

ENV DB_HOST=devsu-test-db-1

ENTRYPOINT ["java", "-jar", "/home/app/devsu-client.jar"]
