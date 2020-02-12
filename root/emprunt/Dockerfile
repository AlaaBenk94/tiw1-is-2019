FROM openjdk:11.0.6-jre
MAINTAINER "Alaa-Eddine BENKARRAD"
ADD ./target/tiw1-tp3.jar /apps/
EXPOSE 8080
CMD chmod +x /apps/tiw1-tp3.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod","/apps/tiw1-tp3.jar"]