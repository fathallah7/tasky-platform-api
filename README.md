# To-Do & Goals Management API Documentation

## 1. Project Overview

**To-Do & Goals Management API**

**Tech Stack:** Java Spring Boot, MySQL, JWT

**Description:**
A backend API system allowing users to register, log in, and manage their personal To-Do items and long-term goals using CRUD operations.

---

## 2. Features

### **User Authentication**

* User registration
* User login
* JWT-based authentication
* Secured endpoints for authenticated users only

### **To-Do List Management**

* Create new to-do items
* Retrieve list of user to-do items
* Update existing to-do items
* Delete to-do items

### **Goals Management**

* Create new goals
* Retrieve list of user goals
* Update existing goals
* Delete goals


### **Pomodoro Sessions**

* User selects a task to focus on
* Starts a pomodoro timer (work session)
* Session stored as a sessions in pomodoro table for analytics and progress tracking

---

## 3. System Architecture

### **Layered Architecture**

* **Controller Layer:** Handles incoming API requests
* **Service Layer:** Business logic implementation
* **Repository Layer:** Database access using JPA
* **Model Layer:** Entity models mapped to database tables
* **Security Layer:** Token validation (JWT)

---

## 4. Database Schema

### **Users Table**

| Field      | Type     | Description     |
| ---------- | -------- | --------------- |
| id         | BIGINT   | Primary key     |
| name   | VARCHAR  | User Name |
| email   | VARCHAR  | Unique email |
| password   | VARCHAR  | Hashed password |

### **Todos Table**

| Field       | Type                | Description                      |
| ----------- | ------------------- | -------------------------------- |
| id          | BIGINT              | Primary key                      |
| user_id     | BIGINT              | Foreign key referencing users.id |
| title       | VARCHAR             | To-do title                      |
| description | VARCHAR                | Details                          |
| completed   | BIT                 | Current status                   |

### **Goals Table**

| Field       | Type    | Description                      |
| ----------- | ------- | -------------------------------- |
| id          | BIGINT  | Primary key                      |
| user_id     | BIGINT  | Foreign key referencing users.id |
| title       | VARCHAR | Goal title                       |
| description    | VARCHAR     | Details       |
| completed | BIT    | Desired completion date          |

### **Pomodoros Table**

| Field       | Type    | Description                      |
| ----------- | ------- | -------------------------------- |
| id          | BIGINT  | Primary key                      |
| start_time    | DATETIME     | Progress percentage       |
| user_id     | BIGINT  | Foreign key referencing users.id |
| todo_id       | BIGINT | Foreign key referencing todo.id                       |

---

## 5. API Endpoints

### **Authentication Endpoints**

| Method | Endpoint         | Description             |
| ------ | ---------------- | ----------------------- |
| POST   | `/auth/register` | Register new user       |
| POST   | `/auth/login`    | Login and receive token |

### **To-Do Endpoints**

| Method | Endpoint          | Description              |
| ------ | ----------------- | ------------------------ |
| GET    | `/api/todo`      | Retrieve all user to-dos |
| POST   | `/api/todo`      | Create new to-do         |
| PUT    | `/api/todo/{id}` | Update specific to-do    |
| DELETE | `/api/todo/{id}` | Delete specific to-do    |

### **Goals Endpoints**

| Method | Endpoint          | Description             |
| ------ | ----------------- | ----------------------- |
| GET    | `/api/goal`      | Retrieve all user goals |
| POST   | `/api/goal`      | Create new goal         |
| PUT    | `/api/goal/{id}` | Update specific goal    |
| DELETE | `/api/goal/{id}` | Delete specific goal    |

---

## 6. Authentication Flow

1. User registers an account
2. User logs in using email/username and password
3. Server returns a JWT token 
4. The user attaches the token in headers:
   `Authorization: Bearer <token>`
5. Server validates token before allowing access to resources

---

## 7. How to Run the Project

### **Requirements:**

* Java 21+
* MySQL
* Maven

### **Steps:**

1. Clone the repository
2. Create a MySQL database
3. Update database credentials in `application.properties`
4. Run the server:

```bash
mvn spring-boot:run
```

5. Backend API starts at: `http://localhost:8080`

