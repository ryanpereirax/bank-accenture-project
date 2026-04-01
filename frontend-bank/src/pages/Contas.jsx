import { useEffect, useMemo, useState } from "react";
import PageHeader from "../components/PageHeader";
import { toMoney } from "../utils/format";
import contasApi from "../services/contas";
import clientesApi from "../services/clientes";
import agenciasApi from "../services/agencias";
import ContaDetalheCard from "../components/ContaDetalheCard";

import { normalizeApiError } from "../utils/errors";

export default function Contas() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [clientes, setClientes] = useState([]);
  const [agencias, setAgencias] = useState([]);

  const [form, setForm] = useState({
    numero: "",
    idCliente: "",
    idAgencia: "",
  });

  const [consultaId, setConsultaId] = useState("");
  const [contaEncontrada, setContaEncontrada] = useState(null);

  // Carrega clientes + agencias
  useEffect(() => {
    let alive = true;

    async function loadBase() {
      setLoading(true);
      setError("");

      try {
        const [cli, ags] = await Promise.all([
          clientesApi.listar(),
          agenciasApi.listar(),
        ]);

        if (!alive) return;

        setClientes(Array.isArray(cli) ? cli : []);
        setAgencias(Array.isArray(ags) ? ags : []);
      } catch (e) {
        if (!alive) return;
        setError(normalizeApiError(e));
      } finally {
        if (alive) setLoading(false);
      }
    }

    loadBase();
    return () => {
      alive = false;
    };
  }, []);

  // OnChange correto (NÃO usa "value" fixo)
  function onChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  const formValido = useMemo(() => {
    return (
      form.numero.trim().length > 0 &&
      String(form.idCliente).trim().length > 0 &&
      String(form.idAgencia).trim().length > 0
    );
  }, [form]);

  async function criarConta(e) {
  e.preventDefault();

  setLoading(true);
  setError("");
  setContaEncontrada(null);

  try {
    // 🔥 VALIDAÇÕES RF-03
    if (!form.numero.trim()) throw new Error("Número obrigatório");
    if (!form.idCliente) throw new Error("Cliente obrigatório");
    if (!form.idAgencia) throw new Error("Agência obrigatória");

    const payload = {
      numero: form.numero.trim(),
      cliente: { idCliente: Number(form.idCliente) },
      agencia: { idAgencia: Number(form.idAgencia) },
    };

      const criada = await contasApi.criar(payload);

      // limpa e mostra retorno
      setForm({ numero: "", idCliente: "", idAgencia: "" });
      setContaEncontrada(criada || null);
    } catch (e2) {
      setError(normalizeApiError(e2));
    } finally {
      setLoading(false);
    }
  }

  async function buscarConta() {
  if (!consultaId) return;

  setLoading(true);
  setError("");
  setContaEncontrada(null);

  try {
    const conta = await contasApi.buscarPorId(Number(consultaId));

    // 🔥 busca cliente completo
    let cliente = null;
    if (conta?.cliente?.idCliente) {
      cliente = await clientesApi.buscarPorId(conta.cliente.idCliente);
    }

    // 🔥 busca agencia completa
    let agencia = null;
    if (conta?.agencia?.idAgencia) {
      agencia = await agenciasApi.buscarPorId(conta.agencia.idAgencia);
    }

    setContaEncontrada({
      ...conta,
      cliente,
      agencia,
    });

  } catch (e) {
    setError(normalizeApiError(e));
  } finally {
    setLoading(false);
  }
}

  return (
    <>
      <PageHeader
        title="Contas"
        subtitle="Cadastro e consulta (RF-03)"
      />

      {error ? <div className="alert">{error}</div> : null}

      <div className="card">
        <h3>Criar Conta Corrente</h3>

        <form onSubmit={criarConta} style={{ display: "grid", gap: 12 }}>
          <div>
            <label>Número da Conta *</label>
            <input
              name="numero"
              value={form.numero}
              onChange={onChange}
              placeholder="Ex: 000123-4"
              autoComplete="off"
            />
          </div>

          <div>
            <label>Cliente *</label>
            <select name="idCliente" value={form.idCliente} onChange={onChange}>
              <option value="">Selecione...</option>
              {clientes.map((c) => (
                <option key={c.idCliente ?? c.id} value={c.idCliente ?? c.id}>
                  #{c.idCliente ?? c.id} - {c.nome} ({c.cpf})
                </option>
              ))}
            </select>
          </div>

          <div>
            <label>Agência *</label>
            <select name="idAgencia" value={form.idAgencia} onChange={onChange}>
              <option value="">Selecione...</option>
              {agencias.map((a) => (
                <option key={a.idAgencia ?? a.id} value={a.idAgencia ?? a.id}>
                  #{a.idAgencia ?? a.id} - {a.nome}
                </option>
              ))}
            </select>
          </div>

          <button type="submit" disabled={!formValido || loading}>
            {loading ? "Salvando..." : "Criar Conta"}
          </button>
        </form>
      </div>

      <div className="card">
        <h3>Consultar Conta por ID</h3>

        <div style={{ display: "flex", gap: 10, alignItems: "end" }}>
          <div style={{ flex: 1 }}>
            <label>ID da Conta</label>
            <input
              value={consultaId}
              onChange={(e) => setConsultaId(e.target.value)}
              placeholder="Ex: 1"
            />
          </div>

          <button type="button" onClick={buscarConta} disabled={!consultaId || loading}>
            Buscar
          </button>
        </div>

                {contaEncontrada ? (
                  <ContaDetalheCard conta={contaEncontrada} />
                ) : (
                  <div
                    style={{
                      marginTop: 12,
                      color: "rgba(229,231,235,.70)",
                      fontSize: 13,
                    }}
                  >
                    Nenhuma conta carregada ainda.
                  </div>
                )}
      </div>
    </>
  );
}