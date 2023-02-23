FROM openjdk:17
EXPOSE 8080
ADD build/libs/signup6-0.0.1-SNAPSHOT.jar signup6-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/signup6-0.0.1-SNAPSHOT.jar"]
