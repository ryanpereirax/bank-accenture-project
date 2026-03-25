import { useEffect, useMemo, useState } from "react";

import PageHeader from "../components/PageHeader";
import LoadingOverlay from "../components/LoadingOverlay";

import agenciasApi from "../services/agencias";
import clientesApi from "../services/clientes";

import { normalizeApiError } from "../utils/errors";

export default function Agencias() {
  const [loading, setLoading] = useState(false);
  const [agencias, setAgencias] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [error, setError] = useState("");

  const [form, setForm] = useState({
    nome: "",
    endereco: "",
    telefone: "",
    idCliente: "",
  });

  async function carregar() {
    setLoading(true);
    setError("");

    try {
      const [ags, cls] = await Promise.all([
        agenciasApi.listar(),
        clientesApi.listar(),
      ]);

      setAgencias(Array.isArray(ags) ? ags : []);
      setClientes(Array.isArray(cls) ? cls : []);
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  function onChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  const formValido = useMemo(() => {
    return (
      form.nome.trim() &&
      form.endereco.trim() &&
      form.telefone.trim() &&
      form.idCliente
    );
  }, [form]);

  async function criarAgencia(e) {
  e.preventDefault();
  setLoading(true);
  setError("");

  try {
    // 🔥 VALIDAÇÕES RF-02
    if (!form.nome.trim()) throw new Error("Nome obrigatório");
    if (!form.endereco.trim()) throw new Error("Endereço obrigatório");
    if (!form.telefone.trim()) throw new Error("Telefone obrigatório");
    if (!form.idCliente) throw new Error("Cliente obrigatório");

    const payload = {
      nome: form.nome.trim(),
      endereco: form.endereco.trim(),
      telefone: form.telefone.trim(),
      cliente: { idCliente: Number(form.idCliente) },
    };

      await agenciasApi.criar(payload);

      setForm({
        nome: "",
        endereco: "",
        telefone: "",
        idCliente: "",
      });

      await carregar();
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      <PageHeader title="Agências" subtitle="Cadastro e consulta" />

      <LoadingOverlay show={loading} open={loading} />

      {error && <div className="alert">{error}</div>}

      {/* FORM */}
      <div className="card">
        <h3>Nova Agência</h3>

        <form onSubmit={criarAgencia} style={{ display: "grid", gap: 12 }}>
          <input
            name="nome"
            value={form.nome}
            onChange={onChange}
            placeholder="Nome"
          />

          <input
            name="endereco"
            value={form.endereco}
            onChange={onChange}
            placeholder="Endereço"
          />

          <input
            name="telefone"
            value={form.telefone}
            onChange={onChange}
            placeholder="Telefone"
          />

          <select name="idCliente" value={form.idCliente} onChange={onChange}>
            <option value="">Selecione o cliente</option>
            {clientes.map((c) => (
              <option key={c.idCliente ?? c.id} value={c.idCliente ?? c.id}>
                #{c.idCliente ?? c.id} - {c.nome}
              </option>
            ))}
          </select>

          <button disabled={!formValido || loading}>
            {loading ? "Salvando..." : "Cadastrar"}
          </button>
        </form>
      </div>

      {/* TABELA */}
      <div className="card">
        <h3>Lista de Agências</h3>

        {agencias.length === 0 ? (
          <p>Nenhuma agência cadastrada</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Endereço</th>
                <th>Telefone</th>
                <th>Cliente</th>
              </tr>
            </thead>

            <tbody>
              {agencias.map((a) => (
                <tr key={a.idAgencia ?? a.id}>
                  <td>{a.idAgencia ?? a.id}</td>
                  <td>{a.nome}</td>
                  <td>{a.endereco}</td>
                  <td>{a.telefone}</td>
                  <td>
                    {a.cliente?.idCliente
                      ? `#${a.cliente.idCliente} - ${a.cliente.nome ?? ""}`
                      : "Não informado"}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}