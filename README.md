# 📔 Journal App API

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-success)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![Build](https://img.shields.io/badge/Maven-3.9-red)
![License](https://img.shields.io/badge/License-MIT-blue)

</p>

A secure, scalable RESTful Journal Management API built using **Spring Boot**, **Spring Security**, **JWT Authentication**, and **MongoDB Atlas**. The application follows modern backend development practices including layered architecture, DTO-based communication, request validation, centralized exception handling, logging, and secure authentication.

---

# ✨ Features

### Authentication & Authorization

- User Registration
- User Login
- JWT Authentication
- Refresh Token Authentication
- BCrypt Password Encryption
- Role-Based Authorization
- Stateless Security
- Secure REST APIs

---

### Journal Management

- Create Journal Entries
- View All Journal Entries
- View Journal Entry by ID
- Update Journal Entries
- Delete Journal Entries
- User-specific journals

---

### Backend Architecture

- Layered Architecture
- DTO Pattern
- Repository Pattern
- Service Layer
- Dependency Injection
- RESTful API Design
- Global Exception Handling
- Input Validation
- Logging
- Environment Profiles
- Clean Package Structure

---

### Database

- MongoDB Atlas
- Spring Data MongoDB
- Document Relationships
- Automatic Index Creation

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security 6 |
| Authentication | JWT + Refresh Tokens |
| Database | MongoDB Atlas |
| Build Tool | Maven |
| API Testing | Postman |
| API Documentation | Swagger / OpenAPI |
| Version Control | Git |
| Repository Hosting | GitHub |

---

# 📁 Project Structure

```
src
│
├── config
│     ├── SecurityConfig
│     └── SwaggerConfig
│
├── controller
│     ├── AuthController
│     ├── JournalController
│     └── UserController
│
├── dto
│     ├── request
│     ├── response
│     └── mapper
│
├── entity
│     ├── User
│     ├── JournalEntry
│     └── RefreshToken
│
├── exception
│     ├── GlobalExceptionHandler
│     └── Custom Exceptions
│
├── filter
│     └── JwtAuthenticationFilter
│
├── repository
│
├── service
│
├── util
│
└── resources
      ├── application.yml
      ├── application-dev.yml
      └── application-prod.yml
```

---

# 🔐 Authentication Flow

```
User Register
       │
       ▼
Password Encrypted using BCrypt
       │
       ▼
User Login
       │
       ▼
Generate Access Token
       │
       ▼
Generate Refresh Token
       │
       ▼
Client Stores Tokens
       │
       ▼
Access Protected APIs
       │
       ▼
Access Token Expires
       │
       ▼
Refresh Token Generates New Access Token
```

---

# 📦 API Modules

## Authentication

| Method | Endpoint | Description |
|----------|-------------------------|----------------|
| POST | /auth/signup | Register User |
| POST | /auth/login | Login |
| POST | /auth/refresh | Refresh Access Token |

---

## User

| Method | Endpoint | Description |
|----------|------------------|----------------|
| GET | /user | Get User |
| PUT | /user | Update User |
| DELETE | /user | Delete User |

---

## Journal

| Method | Endpoint | Description |
|----------|----------------------|----------------|
| GET | /journal | Get All Entries |
| GET | /journal/{id} | Get Entry |
| POST | /journal | Create Entry |
| PUT | /journal/{id} | Update Entry |
| DELETE | /journal/{id} | Delete Entry |

---

# ⚙️ Configuration

The application uses Spring Profiles.

```
application.yml
application-dev.yml
application-prod.yml
```

Sensitive information is configured using environment variables.

Example:

```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}

jwt:
  secret: ${JWT_SECRET}
```

---

# 🚀 Running the Project

## Clone Repository

```bash
git clone https://github.com/your-username/journal-app-api.git
```

```
cd journal-app-api
```

---

## Configure Environment Variables

```
MONGODB_URI=
JWT_SECRET=
JWT_EXPIRATION=
```

---

## Run

```
mvn spring-boot:run
```

or

```
./mvnw spring-boot:run
```

---

# 📖 Swagger Documentation

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🔒 Security Features

- BCrypt Password Hashing
- JWT Authentication
- Refresh Tokens
- Stateless Authentication
- Role-Based Authorization
- Spring Security Filter Chain
- Protected Endpoints
- Secure Password Storage
- Input Validation
- Exception Handling

---

# 📊 Validation

The project validates incoming requests using Jakarta Validation.

Examples:

- Username Required
- Password Required
- Minimum Length
- Custom Error Responses

---

# ⚠️ Exception Handling

Centralized exception handling using `@RestControllerAdvice`.

Examples:

- Resource Not Found
- Invalid Credentials
- Validation Errors
- JWT Errors
- Internal Server Errors

---

# 📝 Logging

Application logging is implemented for:

- Authentication
- API Requests
- Exceptions
- Application Events

Log files are excluded from version control.

---

# 🧪 Testing

The APIs were tested using:

- Postman
- Swagger UI

---

# 🌟 Future Improvements

- Email Verification
- Forgot Password
- OAuth2 Login
- Docker Support
- Redis Caching
- Rate Limiting
- CI/CD Pipeline
- Unit Testing
- Integration Testing
- Monitoring & Metrics
- Cloud Deployment (AWS / Azure)

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push your branch
5. Open a Pull Request

---

# 📄 License

This project is licensed under the MIT License.

---

# 👨‍💻 Author

**Sahil Salve**

Backend Developer | Java | Spring Boot | MongoDB | REST APIs

GitHub: https://github.com/sahil00salve-dotcom

---

⭐ If you found this project useful, consider giving it a star.
