# Car Accessories Management API (Spring Boot)

A Spring Boot REST API rebuild of the original NetBeans/Swing desktop app
(`CAD_Info.java`). The original app stored all data in an in-memory
`JTable`, with no real database despite the resume description claiming
JDBC/MySQL — this version adds a genuine persistence layer and exposes
the same functionality as a REST API, ready for a web frontend later.

## What changed from the original

| Original (Swing) | This version (Spring Boot) |
|---|---|
| Data lived only in `DefaultTableModel` (JTable) | Real persistence via Spring Data JPA (H2 for dev, MySQL for production) |
| Manual binary search for price lookup | Indexed database query (`findByPrice`) |
| `Idconfirm()` duplicate-ID check | Same check, now returns HTTP 409 Conflict |
| `JOptionPane` popups for errors | Structured JSON error responses (`GlobalExceptionHandler`) |
| Swing UI (buttons, JTable) | REST endpoints, callable from Postman, curl, or a future web frontend |
| Typo: "By Commmunity" | Fixed: `BY_COMMUNITY` |

## Requirements

- Java 17+
- Maven 3.8+
- (Optional) MySQL 8+, if you want real persistence instead of the default H2 in-memory DB

## Running it (fastest way — H2, zero setup)

```bash
cd car-accessories-api
mvn spring-boot:run
```

The app starts on **http://localhost:8080**, pre-seeded with the same 12
sample accessories the original Swing app had. Data resets on restart
(H2 is in-memory) — this is intentional for quick local development.

You can browse the raw data directly at **http://localhost:8080/h2-console**
(JDBC URL: `jdbc:h2:mem:caraccessoriesdb`, username: `sa`, password: blank).

## Switching to real MySQL

1. Create the database:
   ```sql
   CREATE DATABASE car_accessories_db;
   ```
2. Open `src/main/resources/application.properties`, comment out the H2
   block, and uncomment + fill in the MySQL block (credentials, etc.)
3. Run again with `mvn spring-boot:run` — Hibernate will auto-create the
   `accessories` table on startup (`ddl-auto=update`).

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/accessories` | List all accessories |
| GET | `/api/accessories/{id}` | Get one accessory by ID |
| POST | `/api/accessories` | Add a new accessory |
| DELETE | `/api/accessories/{id}` | Delete one accessory |
| DELETE | `/api/accessories` | Delete all accessories |
| GET | `/api/accessories/search/price/{price}` | Find accessories at an exact price |
| GET | `/api/accessories/search/category/{category}` | Find accessories in a category |

### Example: add a new accessory (POST /api/accessories)

```json
{
  "id": 13,
  "name": "Dash Cam",
  "category": "Media",
  "price": 4500.0,
  "priceLevel": "MEDIUM",
  "recommendation": "BY_COMPANY"
}
```

`priceLevel` accepts: `LOW`, `MEDIUM`, `HIGH`
`recommendation` accepts: `BY_COMPANY`, `BY_COMMUNITY`, `NONE`

Test it with curl:
```bash
curl -X POST http://localhost:8080/api/accessories \
  -H "Content-Type: application/json" \
  -d '{"id":13,"name":"Dash Cam","category":"Media","price":4500.0,"priceLevel":"MEDIUM","recommendation":"BY_COMPANY"}'
```

Or just use **Postman** — import each endpoint above manually, it's quick for a project this size.

## Next step: adding a frontend

This API has CORS enabled (`@CrossOrigin(origins = "*")` on the
controller) specifically so a separate frontend project — plain
HTML/JS, or React — can call it directly from a different port
(e.g. React dev server on `localhost:3000`) without the browser
blocking the request. Build the frontend as its own project folder in
IntelliJ, and point its fetch/axios calls at `http://localhost:8080/api/accessories`.

## A note on what to verify

This project was written carefully against standard Spring Boot 3.3.x /
Java 17 conventions, but it has **not been compiled or run** in the
environment that generated it (no Maven or JDK compiler available there).
When you build it locally for the first time, if you hit a dependency
version mismatch or similar first-run error, paste the exact error back
and it can be fixed immediately — that's a completely normal part of
getting a new project running.
