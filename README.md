# Event Management System — Backend

A Spring Boot REST API for user accounts, events, and event registrations. It uses Spring Data JPA with MySQL and is designed to work with the [Event Management Frontend](https://github.com/srikanthmala3631-ops/Event_Management_Frontend).

## Requirements

- Java 17 or later
- Maven 3.6 or later
- MySQL

## Configure the database

The default database URL is:

```text
jdbc:mysql://localhost:3306/event_management_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

By default, the application uses the MySQL username `root`. Set the database password in the `DB_PASSWORD` environment variable before starting the application. Do not commit database credentials.

PowerShell:

```powershell
$env:DB_PASSWORD = "your-mysql-password"
```

macOS/Linux:

```bash
export DB_PASSWORD="your-mysql-password"
```

To use a different database URL or username, set `SPRING_DATASOURCE_URL` or `SPRING_DATASOURCE_USERNAME`.

## Run locally

1. Clone this repository and open its directory:

   ```bash
   git clone https://github.com/srikanthmala3631-ops/Event_Management_Backend.git
   cd Event_Management_Backend
   ```

2. Set `DB_PASSWORD` as described above.

3. Start the application:

   ```bash
   mvn spring-boot:run
   ```

The API listens on port `8080` by default. Hibernate is configured to update the database schema from the entity classes on startup.

## API endpoints

All endpoints are prefixed with `/api`.

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/auth/register` | Create a user account |
| `POST` | `/auth/login` | Sign in with email and password |
| `GET` | `/events` | List events |
| `GET` | `/events/{id}` | Get event details |
| `POST` | `/events` | Create an event |
| `PUT` | `/events/{id}` | Update an event |
| `DELETE` | `/events/{id}` | Delete an event |
| `POST` | `/registrations` | Register a user for an event |
| `GET` | `/registrations/user/{userId}` | List a user's registrations |
| `GET` | `/registrations/event/{eventId}` | List registrations for an event |
| `GET` | `/users` | List users |

Event registration checks that the user and event exist, prevents duplicate registrations, and enforces the event participant limit.

## Frontend connection

The frontend development server runs at `http://localhost:3000`; the backend CORS configuration allows requests from that origin. The frontend's API base URL is `http://localhost:8080/api`.

## Security note

This project is intended as a learning/demo application. The current login checks plaintext passwords and does not use Spring Security or token-based authentication; API endpoints do not enforce authorization. Add password hashing and server-side authentication/authorization before exposing it to real users.
