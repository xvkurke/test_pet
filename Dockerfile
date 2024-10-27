# Use OpenJDK 22 as the base image
FROM openjdk:22-jdk-slim

# Copy the built JAR file
COPY ./build/libs/the-lynxie-web-api.jar ./the-lynxie-web-api.jar

RUN echo ${PWD} && ls -lR

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "the-lynxie-web-api.jar"]
