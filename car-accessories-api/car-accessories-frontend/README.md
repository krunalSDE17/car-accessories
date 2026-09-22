# Car Accessories Inventory — React Frontend

A React frontend for the Car Accessories Management REST API
(`car-accessories-api`), replacing the original Swing UI entirely.

## Design approach

This isn't a generic dashboard template — a few deliberate choices:

- **Price level as a colored dot** (green/amber/red for Low/Medium/High)
  instead of plain text — communicates meaning at a glance, matching how
  the original app used radio buttons for the same purpose.
- **Slide-in side panel for "Add accessory"** instead of a centered modal —
  keeps the accessory list visible in context while adding a new item.
- **Space Grotesk + IBM Plex Sans/Mono** typography — technical, legible,
  fits an inventory/parts-catalog tool rather than a generic marketing feel.

## Requirements

- Node.js 18+ and npm
- The Spring Boot backend (`car-accessories-api`) running on
  `http://localhost:8080` — **start that first**, this frontend does
  nothing without it.

## Running it

```bash
npm install
npm run dev
```

Open the URL it prints (usually **http://localhost:5173**).

## What it does

- Lists all accessories from `GET /api/accessories`
- Filters by category and exact price (client-side, on the loaded data)
- Adds a new accessory via the slide-in panel (`POST /api/accessories`)
  -- shows the backend's validation errors inline if you leave a required
  field blank or submit a duplicate ID
- Deletes a single accessory or all accessories, with a confirmation
  prompt before either (`DELETE /api/accessories/{id}` and
  `DELETE /api/accessories`)

## Project structure

```
src/
  api/
    accessoryApi.js       -- all fetch() calls to the Spring Boot API
  components/
    AccessoryTable.jsx    -- the main data table
    AddAccessoryPanel.jsx -- slide-in form for adding a new accessory
    PriceLevelBadge.jsx   -- the colored dot + label for price level
    SearchBar.jsx         -- category + price filter controls
  App.jsx                 -- ties everything together, holds state
  App.css                 -- all styling (design tokens in index.css)
```

## If you see a "Couldn't reach the API" error on load

That means the Spring Boot backend isn't running, or isn't on port 8080.
Start `CarAccessoriesApplication.java` first, confirm
`http://localhost:8080/api/accessories` returns JSON in your browser,
then reload this frontend.

## Note on CORS

The backend already has `@CrossOrigin(origins = "*")` on
`AccessoryController`, specifically so this frontend (running on a
different port) can call it without the browser blocking the request.
If you ever deploy either of these publicly, tighten that to your
actual frontend's domain instead of the wildcard.
