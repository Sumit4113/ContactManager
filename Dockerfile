# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory inside container
WORKDIR /SmartContactManager

# Copy JAR into container
COPY target/SmartContManager-0-0.0.1-SNAPSHOOT.jar .

# Set entrypoint to run the JAR
ENTRYPOINT ["java", "-jar", "SmartContManager-0-0.0.1-SNAPSHOOT.jar"]
