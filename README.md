# 🚀 Distributed Task Engine

A **distributed task execution system** built using **Spring Boot + Redis**, supporting asynchronous processing, retry mechanisms, and real-time task tracking via a web dashboard.

---

## 📌 Overview

This project demonstrates a **producer-consumer architecture** where tasks are:

* Submitted via REST API
* Queued in Redis
* Processed asynchronously by worker threads
* Tracked in real-time through a frontend dashboard

---

## 🧠 Architecture

```
Client → Spring Boot API → Redis Queue → Worker Pool → Redis Store → UI
```

* **Producer** → Controller
* **Broker** → Redis (LIST)
* **Consumer** → Worker threads
* **State Store** → Redis (HASH)

---

## ⚙️ Tech Stack

* **Backend:** Spring Boot
* **Queue & Storage:** Redis
* **Concurrency:** ExecutorService (Thread Pool)
* **Frontend:** HTML, CSS, JavaScript
* **Containerization:** Docker

---

## 🔥 Features

* ✅ Asynchronous task processing
* ✅ Redis-based queue (FIFO using LIST)
* ✅ Blocking queue (efficient worker consumption)
* ✅ Multi-threaded worker pool
* ✅ Retry mechanism (max 3 attempts)
* ✅ Task status tracking (PENDING → RETRYING → COMPLETED / FAILED)
* ✅ Frontend dashboard with live updates
* ✅ Dockerized Redis setup

---

## 🔄 Task Lifecycle

```
PENDING → RETRYING → COMPLETED
                ↘ FAILED
```

---

## 🚀 Getting Started

### 1️⃣ Start Redis (Docker)

```bash
docker run -d -p 6379:6379 --name redis-container redis
```

---

### 2️⃣ Run Backend

```bash
mvn spring-boot:run
```

---

### 3️⃣ Open Dashboard

```
http://localhost:8080
```

---

## 📡 API Endpoints

### ➕ Create Task

```
POST /task?type=PROCESS_DATA&data=test
```

---

### 🔍 Get Task Status

```
GET /task/{id}
```

---

## 🧪 Example Flow

1. Create task via API/UI
2. Task enters Redis queue
3. Worker processes task
4. Status updates in Redis
5. UI polls and displays progress

---

## 🧠 Design Decisions

### 🔹 Why Redis?

* Extremely fast (in-memory)
* Supports queue operations (LIST)
* Enables decoupled architecture

### 🔹 Why Blocking Pop?

* Avoids CPU-intensive polling
* Workers wait efficiently for tasks

### 🔹 Why ExecutorService?

* Manages thread pool efficiently
* Avoids overhead of manual thread creation

---

## ⚠️ Limitations

* No worker crash recovery (no visibility timeout)
* Polling-based UI (no WebSockets)
* No priority queue
* No dead-letter queue (can be added for production)

---

## 🚀 Future Improvements

* Add Dead Letter Queue (DLQ)
* Implement WebSocket-based real-time updates
* Add task timeout & reprocessing
* Introduce priority-based queueing
* Add monitoring & metrics (Prometheus/Grafana)

---

## 🎯 Learning Outcomes

* Asynchronous system design
* Distributed architecture basics
* Redis as queue + datastore
* Concurrency handling in Java
* Failure handling with retries

---

## 👨‍💻 Author

**Sagnik Bera**

---

## ⭐ If you like this project

Give it a star ⭐ and feel free to contribute!
