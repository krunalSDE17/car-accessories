import PriceLevelBadge from "./PriceLevelBadge";

const RECOMMENDATION_LABELS = {
  BY_COMPANY: "By Company",
  BY_COMMUNITY: "By Community",
  NONE: "—",
};

export default function AccessoryTable({ accessories, onDelete, loading }) {
  if (loading) {
    return <p className="empty-state">Loading accessories…</p>;
  }

  if (accessories.length === 0) {
    return (
      <div className="empty-state">
        <p>No accessories match right now.</p>
        <p className="empty-state-sub">Add one, or clear your search filters.</p>
      </div>
    );
  }

  return (
    <table className="accessory-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Category</th>
          <th>Price</th>
          <th>Price level</th>
          <th>Recommendation</th>
          <th aria-label="Actions" />
        </tr>
      </thead>
      <tbody>
        {accessories.map((acc) => (
          <tr key={acc.id}>
            <td className="mono">{acc.id}</td>
            <td>{acc.name}</td>
            <td>{acc.category}</td>
            <td className="mono">₹{acc.price.toLocaleString("en-IN")}</td>
            <td><PriceLevelBadge level={acc.priceLevel} /></td>
            <td>{RECOMMENDATION_LABELS[acc.recommendation] || acc.recommendation}</td>
            <td>
              <button
                className="delete-button"
                onClick={() => onDelete(acc.id)}
                aria-label={`Delete ${acc.name}`}
              >
                Delete
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
