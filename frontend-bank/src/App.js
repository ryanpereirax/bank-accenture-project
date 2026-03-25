import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import AppLayout from "./components/AppLayout";

import Dashboard from "./pages/Dashboard";
import Clientes from "./pages/Clientes";
import Agencias from "./pages/Agencias";
import Contas from "./pages/Contas";
import Operacoes from "./pages/Operacoes";
import Extrato from "./pages/Extrato";

import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <AppLayout>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/clientes" element={<Clientes />} />
          <Route path="/agencias" element={<Agencias />} />
          <Route path="/contas" element={<Contas />} />
          <Route path="/operacoes" element={<Operacoes />} />
          <Route path="/extrato" element={<Extrato />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </AppLayout>
    </BrowserRouter>
  );
}