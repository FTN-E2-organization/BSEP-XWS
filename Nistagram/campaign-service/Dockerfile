FROM maven:slim as builder
WORKDIR /app
COPY pom.xml /app/

RUN mvn -N -e io.takari:maven:wrapper
COPY src /app/src/
RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -e

FROM openjdk:17-jdk-oraclelinux7


WORKDIR /home/app

COPY --from=builder /app/target/campaign-service-0.0.1-SNAPSHOT.jar /home/app/campaign-service-0.0.1-SNAPSHOT.jar

EXPOSE 8087

CMD ["java", "-jar", "/home/app/campaign-service-0.0.1-SNAPSHOT.jar"]
