# Bajaj Finserv API Round - Spring Boot REST API

This is a complete, production-ready Spring Boot REST API built for the Bajaj Finserv API round. It has been prepared for hosting on Render using Maven and Docker.

---

## Developer Information
- **User ID**: `jayant_chugh-21/12/2004`
- **Email**: `jayant0450.be23@chitkara.edu.in`
- **Roll Number**: `2310990450`

---

## API Endpoints

### 1. GET `/bfhl`
Returns the status operation code.
- **Response Format**:
  ```json
  {
    "operation_code": 1
  }
  ```

### 2. POST `/bfhl`
Processes the incoming array of string elements.
- **Request Format**:
  ```json
  {
    "data": ["a", "1", "334", "4", "R", "$"]
  }
  ```
- **Response Format**:
  ```json
  {
    "is_success": true,
    "user_id": "jayant_chugh-21/12/2004",
    "email": "jayant0450.be23@chitkara.edu.in",
    "roll_number": "2310990450",
    "odd_numbers": ["1"],
    "even_numbers": ["334", "4"],
    "alphabets": ["A", "R"],
    "special_characters": ["$"],
    "sum": "339",
    "concat_string": "Ra"
  }
  ```

---

## Local Run Instructions

### Prerequisites
- Java 17
- Maven 3.x

### Run the App
To start the application locally:
```bash
mvn spring-boot:run
```
The server will start at [http://localhost:8080](http://localhost:8080).

### Run Test Cases
To execute the automated JUnit / MockMvc test cases:
```bash
mvn test
```

---

## Render Deployment Instructions

This repository is pre-configured with a `Dockerfile` and dynamic port binding (`server.port=${PORT:8080}`), making deployment to Render extremely straightforward.

### Option A: Deploy via Docker (Recommended)
1. Sign in to your [Render](https://render.com/) account.
2. Click **New** -> **Web Service**.
3. Connect your GitHub repository containing this codebase.
4. Set the following settings:
   - **Language**: `Docker`
   - **Branch**: `main`
5. Click **Deploy Web Service**. Render will automatically build the multi-stage Docker image and deploy the application.

### Option B: Deploy via Native Java Runtime
If you prefer to deploy directly using Render's Java native runtime:
1. Click **New** -> **Web Service**.
2. Connect your GitHub repository.
3. Configure the settings:
   - **Language**: `Java`
   - **Build Command**: `./mvnw clean package -DskipTests` (or `mvn clean package -DskipTests`)
   - **Start Command**: `java -jar target/bfhl-0.0.1-SNAPSHOT.jar`
4. Click **Deploy Web Service**.
