import { useMemo, useState } from "react";
import PageHeader from "../components/PageHeader";
import LoadingOverlay from "../components/LoadingOverlay";

import contasApi from "../services/contas";
import { normalizeApiError } from "../utils/errors";

export default function Extrato() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [idConta, setIdConta] = useState("");
  const [itens, setItens] = useState([]);

  const podeBuscar = useMemo(() => {
  return String(idConta).trim() !== "" && !isNaN(idConta);
}, [idConta]);

  function formatDateTime(value) {
    if (!value) return "-";
    const d = new Date(value);
    if (Number.isNaN(d.getTime())) return String(value);
    return d.toLocaleString("pt-BR");
  }

  function money(value) {
    const n = Number(value);
    return (Number.isFinite(n) ? n : 0).toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  }

  async function buscarExtrato() {
    if (!podeBuscar) {
      alert("Digite um ID válido");
      return;
    }

    setLoading(true);
    setError("");
    setItens([]);

    const id = Number(idConta);

    console.log("Buscando extrato da conta:", id); // DEBUG

    try {
      let data = [];

      try {
        data = await contasApi.extrato(id);
        console.log("Resposta principal:", data);
      } catch (e) {
        console.warn("Erro endpoint principal:", e);

        if (typeof contasApi.extratoAlt === "function") {
          data = await contasApi.extratoAlt(id);
          console.log("Resposta fallback:", data);
        } else {
          throw e;
        }
      }

      if (!Array.isArray(data)) {
        console.warn("API não retornou array:", data);
        setItens([]);
      } else {
        setItens(data);
      }
    } catch (e) {
      console.error("ERRO FINAL:", e);
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      <PageHeader title="Extrato" subtitle="Consulta por conta" />

      <LoadingOverlay show={loading} open={loading} />

      {error && <div className="alert">{error}</div>}

      <div className="card">
        <h3>Buscar extrato</h3>

        <div style={{ display: "flex", gap: 10 }}>
          <input
            value={idConta}
            onChange={(e) => setIdConta(e.target.value)}
            placeholder="ID da conta"
          />

          <button onClick={buscarExtrato} disabled={!podeBuscar || loading}>
            {loading ? "Buscando..." : "Buscar"}
          </button>
        </div>
      </div>

      <div className="card">
        <h3>Movimentações</h3>

        {itens.length === 0 ? (
          <p>Nenhuma movimentação encontrada</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Data/Hora</th>
                <th>Operação</th>
                <th>Valor</th>
              </tr>
            </thead>

            <tbody>
              {itens.map((item, i) => (
                <tr key={item.idExtrato ?? i}>
                  <td>
                    {formatDateTime(
                      item.dataHoraMovimento ||
                        item.dataHoraMovmento ||
                        item.dataHora
                    )}
                  </td>

                  <td>{item.operacao ?? "-"}</td>

                  <td>{money(item.valor)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}