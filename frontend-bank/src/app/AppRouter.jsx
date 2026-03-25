import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/Layout";

import Home from "./pages/Home";
import Clientes from "./pages/Clientes";
import Agencias from "./pages/Agencias";
import Contas from "./pages/Contas";
import Operacoes from "./pages/Operacoes";
import Extrato from "./pages/Extrato";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/clientes" element={<Clientes />} />
          <Route path="/agencias" element={<Agencias />} />
          <Route path="/contas" element={<Contas />} />
          <Route path="/operacoes" element={<Operacoes />} />
          <Route path="/extrato" element={<Extrato />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}