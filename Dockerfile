# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine


# Set the working directory in the container
WORKDIR /app


COPY target/*.jar /app/heatpump.jar


# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/heatpump.jar"]