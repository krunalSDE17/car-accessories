import { useEffect, useMemo, useState } from "react";
import {
  getAllAccessories,
  addAccessory,
  deleteAccessory,
  deleteAllAccessories,
} from "./api/accessoryApi";
import AccessoryTable from "./components/AccessoryTable";
import AddAccessoryPanel from "./components/AddAccessoryPanel";
import SearchBar from "./components/SearchBar";
import "./App.css";

export default function App() {
  const [accessories, setAccessories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState(null);

  const [panelOpen, setPanelOpen] = useState(false);
  const [formError, setFormError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const [categoryFilter, setCategoryFilter] = useState("All");
  const [priceFilter, setPriceFilter] = useState("");

  async function loadAccessories() {
    setLoading(true);
    setLoadError(null);
    try {
      const data = await getAllAccessories();
      setAccessories(data || []);
    } catch (err) {
      setLoadError(
        "Couldn't reach the API. Make sure the Spring Boot backend is running on http://localhost:8080."
      );
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadAccessories();
  }, []);

  async function handleAdd(newAccessory) {
    setFormError(null);
    setSubmitting(true);
    try {
      await addAccessory(newAccessory);
      await loadAccessories();
      setPanelOpen(false);
      return true;
    } catch (err) {
      setFormError(err.message);
      return false;
    } finally {
      setSubmitting(false);
    }
  }

  async function handleDelete(id) {
    const confirmed = window.confirm("Delete this accessory? This can't be undone.");
    if (!confirmed) return;
    try {
      await deleteAccessory(id);
      setAccessories((prev) => prev.filter((a) => a.id !== id));
    } catch (err) {
      alert(err.message);
    }
  }

  async function handleDeleteAll() {
    const confirmed = window.confirm("Delete ALL accessories? This can't be undone.");
    if (!confirmed) return;
    try {
      await deleteAllAccessories();
      setAccessories([]);
    } catch (err) {
      alert(err.message);
    }
  }

  const visibleAccessories = useMemo(() => {
    return accessories.filter((acc) => {
      const matchesCategory =
        categoryFilter === "All" || acc.category.toLowerCase() === categoryFilter.toLowerCase();
      const matchesPrice = !priceFilter || acc.price === Number(priceFilter);
      return matchesCategory && matchesPrice;
    });
  }, [accessories, categoryFilter, priceFilter]);

  return (
    <div className="app-shell">
      <header className="app-header">
        <div>
          <h1>Car Accessories Inventory</h1>
          <p className="app-subtitle">
            {accessories.length} accessor{accessories.length === 1 ? "y" : "ies"} in stock
          </p>
        </div>
        <div className="header-actions">
          <button className="text-button" onClick={handleDeleteAll} disabled={accessories.length === 0}>
            Delete all
          </button>
          <button className="primary-button" onClick={() => setPanelOpen(true)}>
            + Add accessory
          </button>
        </div>
      </header>

      <SearchBar
        category={categoryFilter}
        onCategoryChange={setCategoryFilter}
        price={priceFilter}
        onPriceChange={setPriceFilter}
        onClear={() => {
          setCategoryFilter("All");
          setPriceFilter("");
        }}
      />

      <main className="app-main">
        {loadError ? (
          <div className="empty-state error-state">
            <p>{loadError}</p>
            <button className="text-button" onClick={loadAccessories}>Try again</button>
          </div>
        ) : (
          <AccessoryTable
            accessories={visibleAccessories}
            onDelete={handleDelete}
            loading={loading}
          />
        )}
      </main>

      <AddAccessoryPanel
        open={panelOpen}
        onClose={() => {
          setPanelOpen(false);
          setFormError(null);
        }}
        onSubmit={handleAdd}
        error={formError}
        submitting={submitting}
      />
    </div>
  );
}
