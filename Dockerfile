# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.6-openjdk-8


# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Build the application
RUN mvn package

COPY /app/target/*.jar /app/heatpump.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "heatpump.jar"]