FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# JAR will be copied by build pipeline
COPY *.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
