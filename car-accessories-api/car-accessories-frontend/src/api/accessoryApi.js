const BASE_URL = "http://localhost:8080/api/accessories";

/**
 * Thin wrapper around the Spring Boot REST API.
 * Every function here maps directly to one endpoint in
 * AccessoryController.java on the backend.
 */

async function handleResponse(response) {
  if (response.status === 204) return null; // No Content (deletes)
  const data = await response.json().catch(() => null);
  if (!response.ok) {
    const message = data?.message || `Request failed (${response.status})`;
    throw new Error(message);
  }
  return data;
}

export async function getAllAccessories() {
  const res = await fetch(BASE_URL);
  return handleResponse(res);
}

export async function addAccessory(accessory) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(accessory),
  });
  return handleResponse(res);
}

export async function deleteAccessory(id) {
  const res = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
  return handleResponse(res);
}

export async function deleteAllAccessories() {
  const res = await fetch(BASE_URL, { method: "DELETE" });
  return handleResponse(res);
}

export async function searchByCategory(category) {
  const res = await fetch(`${BASE_URL}/search/category/${encodeURIComponent(category)}`);
  return handleResponse(res);
}

export async function searchByPrice(price) {
  const res = await fetch(`${BASE_URL}/search/price/${encodeURIComponent(price)}`);
  return handleResponse(res);
}
