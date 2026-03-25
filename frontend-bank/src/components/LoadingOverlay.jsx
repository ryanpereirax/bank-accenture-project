export default function LoadingOverlay({ show, open }) {
  const visible = show ?? open;

  if (!visible) return null;

  return (
    <div style={{
      position: "fixed",
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      background: "rgba(0,0,0,0.5)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      color: "#fff",
      zIndex: 9999
    }}>
      Carregando...
    </div>
  );
}