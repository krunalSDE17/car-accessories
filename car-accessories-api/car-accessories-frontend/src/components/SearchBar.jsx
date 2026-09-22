const CATEGORIES = ["All", "Media", "Ambience", "Comfort", "Interior"];

export default function SearchBar({ category, onCategoryChange, price, onPriceChange, onClear }) {
  return (
    <div className="search-bar">
      <label className="search-field">
        Category
        <select value={category} onChange={(e) => onCategoryChange(e.target.value)}>
          {CATEGORIES.map((c) => (
            <option key={c} value={c}>{c}</option>
          ))}
        </select>
      </label>

      <label className="search-field">
        Exact price
        <input
          type="number"
          placeholder="e.g. 1500"
          value={price}
          onChange={(e) => onPriceChange(e.target.value)}
        />
      </label>

      {(category !== "All" || price) && (
        <button className="text-button" onClick={onClear}>Clear filters</button>
      )}
    </div>
  );
}
