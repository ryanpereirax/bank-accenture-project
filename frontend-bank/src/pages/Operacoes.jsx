import React, { useMemo, useState } from "react";
import PageHeader from "../components/PageHeader";
import LoadingOverlay from "../components/LoadingOverlay";

import contasApi from "../services/contas";
import { normalizeApiError } from "../utils/errors";
import { toNumber, toMoney } from "../utils/format";
import { isPositiveMoney } from "../utils/validators";

export default function Operacoes() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [deposito, setDeposito] = useState({ idConta: "", valor: "" });
  const [saque, setSaque] = useState({ idConta: "", valor: "" });
  const [transfer, setTransfer] = useState({
    origemId: "",
    destinoId: "",
    valor: "",
  });

  const [contaAtual, setContaAtual] = useState(null);

  const depositoValido = useMemo(() => {
    return deposito.idConta && isPositiveMoney(deposito.valor);
  }, [deposito]);

  const saqueValido = useMemo(() => {
    return saque.idConta && isPositiveMoney(saque.valor);
  }, [saque]);

  const transferValida = useMemo(() => {
    return (
      transfer.origemId &&
      transfer.destinoId &&
      transfer.origemId !== transfer.destinoId &&
      isPositiveMoney(transfer.valor)
    );
  }, [transfer]);

  async function refreshConta(idConta) {
    try {
      const c = await contasApi.buscarPorId(Number(idConta));
      setContaAtual(c);
    } catch {}
  }

  async function onDepositar(e) {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await contasApi.depositar(Number(deposito.idConta), {
        valor: toNumber(deposito.valor),
      });

      // 🔥 ATUALIZA DASHBOARD
      window.dispatchEvent(new Event("updateDashboard"));

      await refreshConta(deposito.idConta);
      setDeposito({ idConta: "", valor: "" });
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  async function onSacar(e) {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await contasApi.sacar(Number(saque.idConta), {
        valor: toNumber(saque.valor),
      });

      // 🔥 ATUALIZA DASHBOARD
      window.dispatchEvent(new Event("updateDashboard"));

      await refreshConta(saque.idConta);
      setSaque({ idConta: "", valor: "" });
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  async function onTransferir(e) {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await contasApi.transferir({
        contaOrigem: Number(transfer.origemId),
        contaDestino: Number(transfer.destinoId),
        valor: toNumber(transfer.valor),
      });

      // 🔥 ATUALIZA DASHBOARD
      window.dispatchEvent(new Event("updateDashboard"));

      await refreshConta(transfer.origemId);
      setTransfer({ origemId: "", destinoId: "", valor: "" });
    } catch (e) {
      setError(normalizeApiError(e));
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      <PageHeader
        title="Operações"
        subtitle="Depósito, Saque e Transferência"
      />

      <LoadingOverlay show={loading} />

      {error && (
        <div className="card" style={{ borderLeft: "4px solid #d32f2f" }}>
          <b>Erro:</b> {error}
        </div>
      )}

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
          gap: 12,
        }}
      >
        {/* Depósito */}
        <div className="card">
          <h3>Depósito</h3>
          <form onSubmit={onDepositar}>
            <input
              value={deposito.idConta}
              onChange={(e) =>
                setDeposito((d) => ({ ...d, idConta: e.target.value }))
              }
              placeholder="ID da conta"
            />
            <input
              value={deposito.valor}
              onChange={(e) =>
                setDeposito((d) => ({ ...d, valor: e.target.value }))
              }
              placeholder="Valor"
            />
            <button disabled={!depositoValido}>Depositar</button>
          </form>
        </div>

        {/* Saque */}
        <div className="card">
          <h3>Saque</h3>
          <form onSubmit={onSacar}>
            <input
              value={saque.idConta}
              onChange={(e) =>
                setSaque((s) => ({ ...s, idConta: e.target.value }))
              }
              placeholder="ID da conta"
            />
            <input
              value={saque.valor}
              onChange={(e) =>
                setSaque((s) => ({ ...s, valor: e.target.value }))
              }
              placeholder="Valor"
            />
            <button disabled={!saqueValido}>Sacar</button>
          </form>
        </div>

        {/* Transferência */}
        <div className="card">
          <h3>Transferência</h3>
          <form onSubmit={onTransferir}>
            <input
              value={transfer.origemId}
              onChange={(e) =>
                setTransfer((t) => ({ ...t, origemId: e.target.value }))
              }
              placeholder="Conta origem"
            />
            <input
              value={transfer.destinoId}
              onChange={(e) =>
                setTransfer((t) => ({ ...t, destinoId: e.target.value }))
              }
              placeholder="Conta destino"
            />
            <input
              value={transfer.valor}
              onChange={(e) =>
                setTransfer((t) => ({ ...t, valor: e.target.value }))
              }
              placeholder="Valor"
            />
            <button disabled={!transferValida}>Transferir</button>
          </form>
        </div>
      </div>

      <div className="card" style={{ marginTop: 12 }}>
        <h3>Última conta</h3>
        {contaAtual ? (
          <>
            <div>ID: {contaAtual.idContaCorrente ?? contaAtual.id}</div>
            <div>Saldo: {toMoney(contaAtual.saldo ?? 0)}</div>
          </>
        ) : (
          <div>Faça uma operação...</div>
        )}
      </div>
    </>
  );
}