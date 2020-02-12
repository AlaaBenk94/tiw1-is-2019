FROM tomcat:9.0-jdk11-openjdk
MAINTAINER "Alaa-Eddine BENKARRAD"
ADD ./target/maintenance-web.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD chmod +x /usr/local/tomcat/bin/catalina.sh
CMD ["catalina.sh", "run"]