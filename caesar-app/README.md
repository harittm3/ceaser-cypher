# Caesar Cipher Tool 🔐

A full-stack web app that lets you encrypt, decrypt, and brute-force crack
messages using the classic Caesar Cipher.

## What It Does

The Caesar Cipher shifts each letter in a message by a fixed number of positions:

```
Encrypt:  E(x) = (x + k) mod 26
Decrypt:  D(x) = (x − k) mod 26
```

This app wraps that logic in a Spring Boot REST API with a clean web UI on top.

## Screenshots

Open `http://localhost:8080` after running — you'll see the input area, shift
slider, alphabet visualization, and encrypt/decrypt/brute-force buttons.

## How to Run

```bash
cd caesar-app
mvn spring-boot:run
```

Then open **http://localhost:8080**.

To run the tests:

```bash
mvn test
```

## Architecture

```
Browser (HTML/CSS/JS)
        │
        │  fetch() → POST /api/*
        ▼
Spring Boot :8080
├── /static/*       → serves the frontend files
├── /api/encrypt    → CaesarController → CaesarService.encrypt()
├── /api/decrypt    → CaesarController → CaesarService.decrypt()
└── /api/bruteforce → CaesarController → CaesarService.bruteForce()
```

## API

| Method | Endpoint | Body | Response |
|--------|----------|------|----------|
| POST | `/api/encrypt` | `{"text":"HELLO","shift":3}` | `{"result":"KHOOR"}` |
| POST | `/api/decrypt` | `{"text":"KHOOR","shift":3}` | `{"result":"HELLO"}` |
| POST | `/api/bruteforce` | `{"text":"KHOOR"}` | `[{"shift":1,"result":"JGNNQ"}, ...]` |

Invalid input returns HTTP 400:
```json
{"error": "shift: Shift must be at most 25"}
```

## Project Structure

```
caesar-app/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/dsp/caesar/
    │   ├── CaesarApplication.java
    │   ├── CaesarController.java
    │   ├── CaesarService.java
    │   ├── CipherRequest.java
    │   ├── BruteForceRequest.java
    │   ├── BruteForceResult.java
    │   └── GlobalExceptionHandler.java
    ├── main/resources/static/
    │   ├── index.html
    │   ├── style.css
    │   └── script.js
    └── test/java/com/dsp/caesar/
        └── CaesarServiceTest.java
```

## Tech Stack

- Java 17 + Spring Boot 3.2
- Maven
- HTML / CSS / Vanilla JavaScript
- JUnit 5
