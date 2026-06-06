# Gram Panchayat Website Deployment Guide

This document explains how the frontend and backend of the Gram Panchayat website were deployed and connected for 24/7 public access.

---

## 1. Project Deployment Overview

The project has three main parts:

| Part | Technology | Deployed On |
|---|---|---|
| Frontend | React + Vite | Netlify |
| Backend | Spring Boot | Render |
| Database | MySQL | Railway |

Final live setup:

```text
Frontend URL: https://gpjambharun.netlify.app
Backend URL : https://grampanchat-website-backend.onrender.com
Database    : Railway MySQL
```

---

## 2. Database Deployment on Railway

First, an online MySQL database was created on Railway.

Railway MySQL provides database variables such as:

```text
MYSQLHOST
MYSQLPORT
MYSQLDATABASE
MYSQLUSER
MYSQLPASSWORD
MYSQL_PUBLIC_URL
MYSQL_URL
```

For external tools like MySQL Workbench, the public URL was used.

Example format:

```text
mysql://username:password@host:port/database
```

For Spring Boot, the JDBC URL format is required:

```text
jdbc:mysql://host:port/database?useSSL=false&serverTimezone=UTC
```

Example:

```text
DB_URL=jdbc:mysql://kodama.proxy.rlwy.net:33740/railway?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_railway_password
```

The local MySQL database was exported using MySQL Workbench and imported into Railway MySQL.

Verification query:

```sql
USE railway;
SHOW TABLES;
```

---

## 3. Backend Configuration

The backend was changed from local database configuration to environment-variable based configuration.

### Final `application.yml`

```yaml
spring:
  application:
    name: GRAMPANCHAT-WEBSITE

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          ssl:
            trust: smtp.gmail.com

server:
  port: ${PORT:8082}
```

### Why environment variables are used

Do not hardcode database passwords or mail passwords in GitHub.

Wrong:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gram_panchayat
    username: root
    password: Amol@1234
```

Correct:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

At runtime, Render provides the actual values.

---

## 4. Backend CORS Configuration

Because the frontend is deployed on Netlify and backend is deployed on Render, CORS must allow the Netlify domain.

Recommended global CORS configuration:

```java
package com.jambharun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://gpjambharun.netlify.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
```

After adding this, controller-level `@CrossOrigin` annotations are not required.

---

## 5. Dockerfile for Backend Deployment

Render detected Docker, so the backend was deployed using Docker.

Final recommended Dockerfile:

```dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```

This Dockerfile:

1. Builds the Spring Boot project using Maven.
2. Creates the `.jar` file.
3. Runs the generated `.jar` file.

---

## 6. Backend Deployment on Render

Steps followed:

1. Open Render.
2. Create a new Web Service.
3. Connect GitHub backend repository.
4. Select Docker as the runtime.
5. Keep branch as `main`.
6. Add environment variables.
7. Deploy the service.

### Render Environment Variables

```text
DB_URL=jdbc:mysql://kodama.proxy.rlwy.net:33740/railway?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_railway_password
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_gmail_app_password
```

Do not manually add `PORT` unless required. Render provides it automatically.

Backend live URL:

```text
https://grampanchat-website-backend.onrender.com
```

Example API test:

```text
https://grampanchat-website-backend.onrender.com/api/contact
```

---

## 7. Frontend Configuration

Frontend was updated to stop using localhost backend URL.

Wrong:

```js
const API_BASE = "http://localhost:8082";
```

Correct:

```js
const API_BASE = import.meta.env.VITE_API_URL;
```

Example Axios call:

```js
axios.get(`${API_BASE}/api/contact`);
```

Frontend `.env` file:

```env
VITE_API_URL=https://grampanchat-website-backend.onrender.com
```

Important: In Vite, environment variables must start with `VITE_`.

---

## 8. Frontend Deployment on Netlify

Steps followed:

1. Open Netlify.
2. Click Add New Site.
3. Select Import from GitHub.
4. Select frontend repository.
5. Add build settings.

### Netlify Build Settings

```text
Build command: npm run build
Publish directory: dist
```

### Netlify Environment Variable

```text
VITE_API_URL=https://grampanchat-website-backend.onrender.com
```

Frontend live URL:

```text
https://gpjambharun.netlify.app
```

---

## 9. Git Commands Used

After every code change:

```bash
git add .
git commit -m "message here"
git push
```

Example:

```bash
git add .
git commit -m "Add global CORS configuration"
git push
```

Render and Netlify automatically redeploy when new code is pushed to GitHub.

---

## 10. Final Request Flow

When a user opens the website:

```text
User Browser
   ↓
Netlify Frontend
   ↓ API Request
Render Backend
   ↓ Database Query
Railway MySQL
```

Example:

```text
https://gpjambharun.netlify.app
        ↓
https://grampanchat-website-backend.onrender.com/api/contact
        ↓
Railway MySQL Database
```

---

## 11. Important Security Notes

Never push these values to GitHub:

```text
Database password
Gmail app password
API keys
Secret tokens
```

Use environment variables instead.

Add these files to `.gitignore`:

```gitignore
.env
application-local.yml
```

If any password was shared publicly, rotate/change it immediately.

---

## 12. Common Errors and Fixes

### CORS Error

Problem:

```text
Access to XMLHttpRequest has been blocked by CORS policy
```

Fix:

Add Netlify URL in backend CORS config:

```java
"https://gpjambharun.netlify.app"
```

---

### Backend Cannot Connect to Database

Problem:

```text
Communications link failure
```

Fix:

Check Render environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Make sure `DB_URL` starts with:

```text
jdbc:mysql://
```

---

### Frontend Still Calling Localhost

Problem:

Browser console shows:

```text
http://localhost:8082
```

Fix:

Use:

```js
import.meta.env.VITE_API_URL
```

Also add `VITE_API_URL` in Netlify environment variables.

---

### Render Free Instance Sleeps

Render free instance may sleep after inactivity. First request after sleep can take extra time.

For true always-on 24/7 access, use a paid instance or VPS.

---

## 13. Final Deployment Checklist

- [x] Railway MySQL database created
- [x] Local database imported into Railway
- [x] Backend uses environment variables
- [x] Backend deployed on Render
- [x] Frontend uses deployed backend URL
- [x] Frontend deployed on Netlify
- [x] CORS updated for Netlify URL
- [x] GitHub code does not contain passwords

---

## 14. Final Live URLs

```text
Frontend: https://gpjambharun.netlify.app
Backend : https://grampanchat-website-backend.onrender.com
Database: Railway MySQL
```
