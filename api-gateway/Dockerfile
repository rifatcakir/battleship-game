FROM openjdk:11.0.2-jre-slim
COPY target/api-gateway-1.0-SNAPSHOT.jar .
CMD /usr/bin/java -Dlogging.path=/log/ -Xmx400m -Xms400m -jar  api-gateway-1.0-SNAPSHOT.jar
EXPOSE 8080