# Use OpenJDK 22 as the base image
FROM openjdk:22-jdk-slim

# Copy the built JAR file into the container
COPY ./build/libs/the-lynxie-web-api.jar /app/the-lynxie-web-api.jar

# Set the working directory
WORKDIR /app

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "the-lynxie-web-api.jar"]
