FROM openjdk:11.0.6-jre
MAINTAINER "Alaa-Eddine BENKARRAD"
ADD ./target/banque-0.0.1-SNAPSHOT.jar /apps/
EXPOSE 8080
CMD chmod +x /apps/banque-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod","/apps/banque-0.0.1-SNAPSHOT.jar"]