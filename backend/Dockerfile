# Use the official Gradle image as the base image
FROM gradle:8.1.1-jdk17 AS build

# Set the working directory
WORKDIR /home/gradle/src

# Copy the build files to the working directory
COPY build.gradle.kts settings.gradle.kts ./

# Copy the source code to the working directory
COPY src src

# Run the Gradle build
RUN gradle build --no-daemon -x test

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Set the entrypoint command
ENTRYPOINT ["java", "-jar", "app.jar"]
