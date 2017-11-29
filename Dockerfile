FROM tomcat:8.0.20-jre8
COPY target/spring-mvc-showcase.war /usr/local/tomcat/webapps/test.war
