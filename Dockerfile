### BUILD image
FROM maven:3-jdk-11 as builder
# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
#Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
COPY src /build/src
COPY ./target/FinalMVC.war /var/lib/jetty/webapps/root.war
COPY ./docker-entrypoint.sh /var/lib/docker-entrypoint.sh

RUN ["chmod", "+x", "/var/lib/docker-entrypoint.sh"]
EXPOSE 8081
#EXPOSE 8080
ENTRYPOINT ["/var/lib/docker-entrypoint.sh"]