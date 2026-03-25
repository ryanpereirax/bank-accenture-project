export default function PageHeader({ title, subtitle }) {
  return (
    <div style={{ marginBottom: 20 }}>
      <div style={{ fontSize: 24, fontWeight: 600 }}>{title}</div>
      <div style={{ color: "#9ca3af" }}>{subtitle}</div>
    </div>
  );
}