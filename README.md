# Study Room Booking System — Backend

- **Student:** MUTONI Patience
- **Student ID:** 28376
- **Project:** Study Room Booking System (INSY 8322 Web Technology — Mid-Term, AUCA)
- **Stack:** Java 21, Spring Boot, Spring Data JPA, PostgreSQL

## Steps to run the API

1. Install Java 21 and PostgreSQL 18, and start the PostgreSQL service.
2. Create the database and user (in `psql -U postgres`):
   CREATE USER studyroom_user WITH PASSWORD 'studyroom123';
   CREATE DATABASE study_room_booking_db OWNER studyroom_user;
3. Clone: `git clone https://github.com/mutonipatience877/study-room-booking-system-backend.git`
4. Optional: set the environment variable `DB_PASSWORD` if your database password differs.
5. Run: `mvnw.cmd spring-boot:run` (Windows) or `./mvnw spring-boot:run` (Linux/macOS). Tables are created automatically.
6. The API runs on `http://localhost:8080`. Requests in Postman are named by requirement ID (A2, B1 … F2).