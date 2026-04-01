function InfoRow({ label, value }) {
  const v = value === null || value === undefined || value === "" ? "—" : value;
  return (
    <div style={{ display: "flex", gap: 8, padding: "6px 0" }}>
      <strong style={{ width: 160, opacity: 0.8 }}>{label}:</strong>
      <span>{v}</span>
    </div>
  );
}

function formatBRL(value) {
  const v = typeof value === "number" ? value : 0;
  return v.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

function maskCPF(cpf) {
  if (!cpf) return "—";
  const d = String(cpf).replace(/\D/g, "");
  if (d.length !== 11) return cpf;
  return d.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export default function ContaDetalheCard({ conta }) {
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

      <div style={{ fontWeight: 700, marginBottom: 10 }}>Detalhes da Conta</div>

      <InfoRow label="ID Conta" value={conta?.idContaCorrente} />
      <InfoRow label="Número" value={conta?.numero} />
      <InfoRow label="Saldo" value={formatBRL(conta?.saldo)} />

      <hr style={{ border: 0, borderTop: "1px solid #eee", margin: "12px 0" }} />

      <div style={{ fontWeight: 700, marginBottom: 8 }}>Cliente</div>
      <InfoRow label="ID Cliente" value={conta?.cliente?.idCliente} />
      <InfoRow label="Nome" value={conta?.cliente?.nome} />
      <InfoRow label="CPF" value={maskCPF(conta?.cliente?.cpf)} />
      <InfoRow label="Fone" value={conta?.cliente?.fone} />

      <hr style={{ border: 0, borderTop: "1px solid #eee", margin: "12px 0" }} />

      <div style={{ fontWeight: 700, marginBottom: 8 }}>Agência</div>
      <InfoRow label="ID Agência" value={conta?.agencia?.idAgencia} />
      <InfoRow label="Nome" value={conta?.agencia?.nome} />
      <InfoRow label="Endereço" value={conta?.agencia?.endereco} />
      <InfoRow label="Telefone" value={conta?.agencia?.telefone} />
    </div>
  );
}