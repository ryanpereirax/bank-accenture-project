import { Link, useLocation } from "react-router-dom";

export default function Layout({ children }) {
  const location = useLocation();

  function isActive(path) {
    return location.pathname === path ? "active" : "";
  }

  const isHome = location.pathname === "/";

  return (
    <div style={{ display: "flex", height: "100vh" }}>
      
      {/* SIDEBAR */}
      <aside style={{
        width: 240,
        background: "#020617",
        padding: 20,
        display: "flex",
        flexDirection: "column",
        gap: 10,
        borderRight: "1px solid #1f2937"
      }}>
        <h2 style={{ color: "#7c3aed" }}>ACCENTURE BANK</h2>
               
        <Link className={isActive("/clientes")} to="/clientes">Clientes</Link>
        <Link className={isActive("/agencias")} to="/agencias">Agências</Link>
        <Link className={isActive("/contas")} to="/contas">Contas</Link>
        <Link className={isActive("/operacoes")} to="/operacoes">Operações</Link>
        <Link className={isActive("/extrato")} to="/extrato">Extrato</Link>
      </aside>

      {/* CONTEÚDO */}
      <main style={{ flex: 1, padding: 20, overflowY: "auto" }}>
        
        {isHome ? (
          <div style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            height: "100%",
            gap: 20
          }}>
            <div style={{ fontSize: 32, fontWeight: 700 }}>
              👋 Bem-vindo ao ACCENTURE BANK
            </div>

            <div style={{ opacity: 0.7 }}>
              Selecione uma opção no menu lateral para começar.
            </div>

            <div className="card" style={{ maxWidth: 400 }}>
              <b>💡 Dica:</b>
              <div style={{ marginTop: 6 }}>
                Gerencie clientes, contas e operações bancárias facilmente.
              </div>
            </div>
          </div>
        ) : (
          children
        )}

      </main>
    </div>
  );
}