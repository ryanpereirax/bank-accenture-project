import { useEffect, useMemo, useState } from "react";
import clientesApi from "../services/clientes";
import { normalizeApiError } from "../utils/errors";
import { isCpfValid } from "../utils/validators";
import ClienteDetalheCard from "../components/ClienteDetalheCard";

export default function Clientes() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [lista, setLista] = useState([]);

  const [form, setForm] = useState({
    nome: "",
    cpf: "",
    fone: "",
  });

  const [buscaId, setBuscaId] = useState("");
  const [clienteDetalhe, setClienteDetalhe] = useState(null);

  async function carregar() {
    setLoading(true);
    setError("");
    try {
      const data = await clientesApi.listar();
      setLista(Array.isArray(data) ? data : []);
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  // ✅ onChange CORRETO (isso evita cpf null)
  function onChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,   // <-- ESSA É A LINHA QUE TAVA ERRADA NO seu código anterior
    }));
  }

  const formValido = useMemo(() => {
    const nomeOk = form.nome.trim().length > 0;
    const foneOk = form.fone.trim().length > 0;
    const cpfOk = form.cpf.trim().length > 0 && form.cpf.trim().length <= 14 && isCpfValid(form.cpf);
    return nomeOk && foneOk && cpfOk;
  }, [form]);

  async function cadastrar(e) {
  e.preventDefault();
  setLoading(true);
  setError("");

  try {
    // 🔥 VALIDAÇÕES RF-01
    if (!form.nome.trim()) throw new Error("Nome obrigatório");
    if (!form.fone.trim()) throw new Error("Fone obrigatório");
    if (!form.cpf.trim()) throw new Error("CPF obrigatório");
    if (form.cpf.length > 14) throw new Error("CPF maior que 14 caracteres");
    if (!isCpfValid(form.cpf)) throw new Error("CPF inválido");

    const payload = {
      nome: form.nome.trim(),
      cpf: form.cpf.trim(),
      fone: form.fone.trim(),
    };

      await clientesApi.criar(payload);

      setForm({ nome: "", cpf: "", fone: "" });
      setClienteDetalhe(null);
      setBuscaId("");
      await carregar();
    } catch (e2) {
      setError(normalizeApiError(e2));
    } finally {
      setLoading(false);
    }
  }

  async function buscarPorId() {
    if (!buscaId) return;
    setLoading(true);
    setError("");
    setClienteDetalhe(null);

    try {
      const c = await clientesApi.buscarPorId(Number(buscaId));
      setClienteDetalhe(c);
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      {error && <div className="alert">{error}</div>}

      <div className="card">
        <div className="h1">Clientes</div>
        <div className="sub">Cadastro e consulta (RF-01)</div>
      </div>

      <div style={{ height: 14 }} />

      <div className="card">
        <div className="h1" style={{ fontSize: 18 }}>Novo Cliente</div>
        <div style={{ height: 10 }} />

        <form onSubmit={cadastrar} style={{ display: "grid", gap: 12 }}>
          <div>
            <label>Nome *</label>
            <input
              name="nome"
              value={form.nome}
              onChange={onChange}
              placeholder="Ex: Maria Silva"
              autoComplete="off"
            />
          </div>

          <div>
            <label>CPF * (até 14 caracteres)</label>
            <input
              name="cpf"
              value={form.cpf}
              onChange={onChange}
              placeholder="Ex: 123.456.789-10"
              autoComplete="off"
            />
            <div className="sub" style={{ marginTop: 6 }}>
              Dica: use pontuação ou só números.
            </div>
          </div>

          <div>
            <label>Fone *</label>
            <input
              name="fone"
              value={form.fone}
              onChange={onChange}
              placeholder="Ex: (81) 99999-9999"
              autoComplete="off"
            />
          </div>

          <button disabled={!formValido || loading} type="submit">
            {loading ? "Salvando..." : "Cadastrar"}
          </button>
        </form>
      </div>

      <div style={{ height: 14 }} />

      <div className="card">
        <div className="h1" style={{ fontSize: 18 }}>Consultar Cliente por ID</div>
        <div style={{ height: 10 }} />

        <div style={{ display: "flex", gap: 10, alignItems: "end" }}>
          <div style={{ flex: 1 }}>
            <label>ID do Cliente</label>
            <input
              value={buscaId}
              onChange={(e) => setBuscaId(e.target.value)}
              placeholder="Ex: 1"
            />
          </div>
          <button type="button" onClick={buscarPorId} disabled={!buscaId || loading}>
            Buscar
          </button>
        </div>

        {clienteDetalhe && (
          <ClienteDetalheCard cliente={clienteDetalhe} />
        )}

      </div>

      <div style={{ height: 14 }} />

      <div className="card">
        <div className="h1" style={{ fontSize: 18 }}>Lista de Clientes</div>
        <div className="sub">Total: {lista.length}</div>
        <div style={{ height: 10 }} />

        {lista.length === 0 ? (
          <div className="sub">Nenhum cliente cadastrado.</div>
        ) : (
          <div style={{ overflow: "auto" }}>
            <table width="100%" cellPadding="10" style={{ borderCollapse: "collapse" }}>
              <thead>
                <tr>
                  <th align="left">ID</th>
                  <th align="left">Nome</th>
                  <th align="left">CPF</th>
                  <th align="left">Fone</th>
                </tr>
              </thead>
              <tbody>
                {lista.map((c) => (
                  <tr key={c.idCliente ?? c.id}>
                    <td>{c.idCliente ?? c.id}</td>
                    <td>{c.nome}</td>
                    <td>{c.cpf}</td>
                    <td>{c.fone}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </>
  );
}
