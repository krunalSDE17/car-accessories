import { useState } from "react";

const CATEGORIES = ["Media", "Ambience", "Comfort", "Interior"];
const PRICE_LEVELS = ["LOW", "MEDIUM", "HIGH"];
const RECOMMENDATIONS = [
  { value: "BY_COMPANY", label: "By Company" },
  { value: "BY_COMMUNITY", label: "By Community" },
  { value: "NONE", label: "None" },
];

const emptyForm = {
  id: "",
  name: "",
  category: CATEGORIES[0],
  price: "",
  priceLevel: "MEDIUM",
  recommendation: "NONE",
};

export default function AddAccessoryPanel({ open, onClose, onSubmit, error, submitting }) {
  const [form, setForm] = useState(emptyForm);

  function update(field, value) {
    setForm((f) => ({ ...f, [field]: value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    const ok = await onSubmit({
      ...form,
      id: Number(form.id),
      price: Number(form.price),
    });
    if (ok) setForm(emptyForm);
  }

  return (
    <>
      <div className={`scrim ${open ? "scrim--visible" : ""}`} onClick={onClose} />
      <aside className={`panel ${open ? "panel--open" : ""}`}>
        <div className="panel-header">
          <h2>Add accessory</h2>
          <button className="icon-button" onClick={onClose} aria-label="Close">✕</button>
        </div>

        <form className="panel-form" onSubmit={handleSubmit}>
          <label>
            ID
            <input
              type="number"
              required
              value={form.id}
              onChange={(e) => update("id", e.target.value)}
              placeholder="e.g. 13"
            />
          </label>

          <label>
            Name
            <input
              type="text"
              required
              value={form.name}
              onChange={(e) => update("name", e.target.value)}
              placeholder="e.g. Dash Cam"
            />
          </label>

          <label>
            Category
            <select value={form.category} onChange={(e) => update("category", e.target.value)}>
              {CATEGORIES.map((c) => (
                <option key={c} value={c}>{c}</option>
              ))}
            </select>
          </label>

          <label>
            Price (₹)
            <input
              type="number"
              step="0.01"
              required
              value={form.price}
              onChange={(e) => update("price", e.target.value)}
              placeholder="e.g. 4500"
            />
          </label>

          <fieldset>
            <legend>Price level</legend>
            <div className="radio-row">
              {PRICE_LEVELS.map((level) => (
                <label key={level} className="radio-pill">
                  <input
                    type="radio"
                    name="priceLevel"
                    checked={form.priceLevel === level}
                    onChange={() => update("priceLevel", level)}
                  />
                  {level.charAt(0) + level.slice(1).toLowerCase()}
                </label>
              ))}
            </div>
          </fieldset>

          <fieldset>
            <legend>Recommendation</legend>
            <div className="radio-row">
              {RECOMMENDATIONS.map((r) => (
                <label key={r.value} className="radio-pill">
                  <input
                    type="radio"
                    name="recommendation"
                    checked={form.recommendation === r.value}
                    onChange={() => update("recommendation", r.value)}
                  />
                  {r.label}
                </label>
              ))}
            </div>
          </fieldset>

          {error && <p className="form-error">{error}</p>}

          <button type="submit" className="primary-button" disabled={submitting}>
            {submitting ? "Adding…" : "Add accessory"}
          </button>
        </form>
      </aside>
    </>
  );
}
