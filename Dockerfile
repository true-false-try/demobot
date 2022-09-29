FROM adoptopenjdk/maven-openjdk11
VOLUME /tmp
WORKDIR .
COPY ./ ./
COPY target/*.jar app_demobot.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/app_demobot.jar"]