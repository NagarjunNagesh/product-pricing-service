# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and project files
COPY . .

# Build the application (optional, for multi-stage builds)
RUN ./gradlew build

# Expose the port the app runs on
EXPOSE 8080

# Run the Spring Boot app using Gradle bootRun
CMD ["./gradlew", "bootRun"]
