FROM maven:slim as builder
WORKDIR /app
COPY pom.xml /app/

RUN mvn -N -e io.takari:maven:wrapper
COPY src /app/src/
RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -e

FROM openjdk:17-jdk-oraclelinux7


WORKDIR /home/app

COPY --from=builder /app/target/activity-service-0.0.1-SNAPSHOT.jar /home/app/activity-service-0.0.1-SNAPSHOT.jar

EXPOSE 8086

CMD ["java", "-jar", "/home/app/activity-service-0.0.1-SNAPSHOT.jar"]
