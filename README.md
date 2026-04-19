# 🚀 Distributed Task Execution Engine

## 🧠 Overview

A scalable backend system that processes tasks asynchronously using a **Redis-based distributed queue** and **multi-threaded workers**.
Designed to simulate real-world task processing systems like job schedulers, background workers, and distributed execution pipelines.

---

## ⚙️ Features

* ✅ Asynchronous task processing
* ✅ Redis-based distributed queue
* ✅ Multi-threaded worker system
* ✅ Blocking queue behavior (efficient, no CPU polling)
* ✅ Retry mechanism with max retry limit
* ✅ Task lifecycle tracking

  * `PENDING`
  * `RETRYING`
  * `COMPLETED`
  * `FAILED`
* ✅ REST APIs for task creation and tracking

---

## 🏗️ System Architecture

```plaintext
Client → Controller → Redis Queue → Workers → Processing → Status Update
```

### 🔄 Flow

1. Client submits task via API
2. Task is pushed to Redis queue
3. Workers continuously listen (blocking)
4. Task is processed asynchronously
5. On failure → retried up to limit
6. Final status stored and returned via API

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Redis (Docker)**
* **Multithreading**
* **Concurrent Data Structures**
* **REST APIs**

---

## 🔌 API Endpoints

### ➕ Create Task

```http
POST /task?data=example
```

**Response:**

```json
{
  "id": "task-id",
  "status": "PENDING"
}
```

---

### 🔍 Get Task Status

```http
GET /task/{id}
```

**Response:**

```json
{
  "id": "task-id",
  "status": "COMPLETED"
}
```

---

## 🔁 Retry Mechanism

* Tasks are retried automatically on failure
* Max retry limit: **3 attempts**
* After limit → marked as `FAILED`

---

## ⚡ Key Highlights

* Uses **Redis as external queue** → enables scalability
* Implements **blocking queue behavior (BLPOP)** → efficient processing
* Handles failures using **retry strategy**
* Demonstrates **real-world distributed system design patterns**

---

## 🚀 How to Run

### 1️⃣ Start Redis (Docker)

```bash
docker run -d -p 6379:6379 redis
```

---

### 2️⃣ Run Application

```bash
mvn spring-boot:run
```

---

### 3️⃣ Test API

```http
POST http://localhost:8080/task?data=test
```

---

## 🧪 Sample Output (Console)

```plaintext
Worker-1 Processing: 123
Retrying: 123 attempt 1
Worker-2 Processing: 123
COMPLETED
```

---

## 📌 Future Improvements

* 🔥 Replace threads with **ExecutorService (thread pool)**
* 🔥 Store task status fully in Redis (persistent system)
* 🔥 Implement Dead Letter Queue (DLQ)
* 🔥 Add priority queue support
* 🔥 Add monitoring (metrics/logging dashboard)

---

## 🎯 Learning Outcomes

* Distributed system design basics
* Asynchronous processing
* Queue-based architectures
* Fault tolerance & retries
* Redis integration in backend systems

---

## 💡 Author

**Sagnik Bera**
Computer Science Student | Backend & Systems Enthusiast

---

## ⭐ If you found this useful

Give it a ⭐ on GitHub and feel free to fork!
