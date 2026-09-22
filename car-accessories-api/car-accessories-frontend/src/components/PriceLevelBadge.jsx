const CONFIG = {
  LOW: { label: "Low", color: "var(--success)" },
  MEDIUM: { label: "Medium", color: "var(--accent-amber)" },
  HIGH: { label: "High", color: "var(--danger)" },
};

export default function PriceLevelBadge({ level }) {
  const cfg = CONFIG[level] || { label: level, color: "var(--ink-soft)" };
  return (
    <span className="price-badge">
      <span className="price-dot" style={{ background: cfg.color }} />
      {cfg.label}
    </span>
  );
}
