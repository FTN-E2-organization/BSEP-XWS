#syntax=docker/dockerfile:1.0.0-experimental
FROM existdb/existdb:5.3.0-SNAPSHOT
RUN ["java", "org.exist.start.Main", "client", "--no-gui",  "-l", "-u", "admin", "-P", "", "-x", "sm:passwd('admin','123')"]
EXPOSE 8080 8443
CMD [ "java", "-jar", "start.jar", "jetty" ]
