function InfoRow({ label, value }) {
  const v = value === null || value === undefined || value === "" ? "—" : value;
  return (
    <div style={{ display: "flex", gap: 8, padding: "6px 0" }}>
      <strong style={{ width: 140, opacity: 0.8 }}>{label}:</strong>
      <span>{v}</span>
    </div>
  );
}

function maskCPF(cpf) {
  if (!cpf) return "—";
  const d = String(cpf).replace(/\D/g, "");
  if (d.length !== 11) return cpf;
  return d.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export default function ClienteDetalheCard({ cliente }) {
  return (
    <div
        style={{
            background: "#1f2937",      // cinza escuro
            color: "#f9fafb",           // texto claro
            padding: 14,
            borderRadius: 12,
            border: "1px solid #374151",
        }}
>

      <div style={{ fontWeight: 700, marginBottom: 10 }}>Detalhes do Cliente</div>

      <InfoRow label="ID" value={cliente?.idCliente} />
      <InfoRow label="Nome" value={cliente?.nome} />
      <InfoRow label="CPF" value={maskCPF(cliente?.cpf)} />
      <InfoRow label="Fone" value={cliente?.fone} />
    </div>
  );
}
