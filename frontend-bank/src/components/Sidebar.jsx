import { NavLink } from "react-router-dom";
import { IGrid, IUsers, IBank, ICard, ISwap, IReceipt } from "./icons";

const items = [
  { to: "/clientes", label: "Clientes", Icon: IUsers },
  { to: "/agencias", label: "Agências", Icon: IBank },
  { to: "/contas", label: "Contas", Icon: ICard },
  { to: "/operacoes", label: "Operações", Icon: ISwap },
  { to: "/extrato", label: "Extrato", Icon: IReceipt },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <span className="brand-icon">≡</span>
        <div className="brand-text">
          <div className="brand-title">ACCENTURE BANK</div>
          <div className="brand-sub">Sistema Bancário Accenture</div>
        </div>
      </div>

      <nav className="nav">
        {items.map(({ to, label, Icon }) => (
          <NavLink
            key={to}
            to={to}
            end={to === "/"}
            className={({ isActive }) => (isActive ? "nav-item active" : "nav-item")}
          >
            <span className="nav-ico">
              <Icon className="svg" />
            </span>
            <span className="nav-label">{label}</span>
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}