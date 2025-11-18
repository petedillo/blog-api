FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR
COPY target/api-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
