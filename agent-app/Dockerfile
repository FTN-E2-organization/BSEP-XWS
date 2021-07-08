FROM maven:slim as builder
WORKDIR /app
COPY pom.xml /app/

RUN mvn -N -e io.takari:maven:wrapper
COPY src /app/src/
RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -e

FROM openjdk:17-jdk-oraclelinux7


WORKDIR /home/app

COPY --from=builder /app/target/agent-app-0.0.1-SNAPSHOT.jar /home/app/agent-app-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/classes/ftn.jks /home/app/ftn.jks
COPY --from=builder /app/target/classes/ftn.jks /home/app/src/main/resources/ftn.jks

EXPOSE 8091

CMD ["java", "-jar", "/home/app/agent-app-0.0.1-SNAPSHOT.jar"]
