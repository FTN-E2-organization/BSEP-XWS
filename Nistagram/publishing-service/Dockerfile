FROM maven:slim as builder
WORKDIR /app
COPY pom.xml /app/

RUN mvn -N -e io.takari:maven:wrapper
COPY src /app/src/
RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -e

FROM openjdk:17-jdk-oraclelinux7


WORKDIR /home/app

COPY --from=builder /app/target/publishing-service-0.0.1-SNAPSHOT.jar /home/app/publishing-service-0.0.1-SNAPSHOT.jar

EXPOSE 8083

CMD ["java", "-jar", "/home/app/publishing-service-0.0.1-SNAPSHOT.jar"]
