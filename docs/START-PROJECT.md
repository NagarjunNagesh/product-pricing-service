---


## Option 1: Run with Spring Boot Dashboard in VS Code

### Prerequisites
- VS Code with the "Spring Boot Extension Pack" installed
- Java 21 or later
- Gradle (wrapper included)

Ensure that you set the `DB_PASSWORD` if required, else defaults to `sa`
---

## Option 2: Docker Spin Up

### Prerequisites
- Docker installed on your machine
- Uncomment `spring.h2.console.settings.web-allow-others=true` in application.properties to access the h2-console via browser.

### Steps
1. **Build the Docker image** (from the project root):
   ```sh
   docker build -t product-pricing-service .
   ```

2. **Run the Docker container** (with environment variable):
   ```sh
   docker run --name product-pricing-service -e DB_PASSWORD=sa -p 8080:8080 product-pricing-service
   ```
   - `--name product-pricing-service`: Names your container for easy management.
   - `-e DB_PASSWORD=sa`: Sets the database password environment variable (change as needed).
   - `-p 8080:8080`: Maps container port 8080 to your host.

3. **Access the application:**
   - App: [http://localhost:8080](http://localhost:8080)
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Option 3: Local Spin Up (No Docker)

### Prerequisites
- Java 21 or later
- Gradle (wrapper included)

### Steps
1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd product-pricing-service
   ```

2. **Set the DB_PASSWORD environment variable:**
   - On Windows (PowerShell):
     ```powershell
     $env:DB_PASSWORD="your_password"
     ```
   - On Windows (cmd):
     ```cmd
     set DB_PASSWORD=your_password
     ```
   - On Linux/macOS:
     ```sh
     export DB_PASSWORD=your_password
     ```

3. **Build the project:**
   ```sh
   ./gradlew build
   ```
   (On Windows, use `gradlew.bat build`)

4. **Run the application:**
   ```sh
   ./gradlew bootRun
   ```
   (On Windows, use `gradlew.bat bootRun`)

5. **Access the application:**
   - App: [http://localhost:8080](http://localhost:8080)
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Notes
- The `DB_PASSWORD` environment variable is required for both Docker and local runs. If not set, the default is blank.
- The H2 database is in-memory and resets on every restart.
- For security, do not enable H2 remote access in production.
   ./gradlew bootRun
   ```
   (On Windows, use `gradlew.bat bootRun`)

## Accessing the Application
- The app will start on [http://localhost:8080](http://localhost:8080)
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Notes
- Make sure the `DB_PASSWORD` environment variable is set in the same terminal session before running the app.
