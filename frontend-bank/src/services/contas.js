import { http } from "./http";

const contasApi = {
  // CRUD básico
  criar: (payload) => http.post("/contas", payload),

  buscarPorId: (id) => http.get(`/contas/${id}`),

  // Operações
  depositar: (idConta, payload) => http.post(`/contas/${idConta}/deposito`, payload),

  sacar: (idConta, payload) => http.post(`/contas/${idConta}/saque`, payload),

  transferir: (payload) => http.post("/contas/transferencia", payload),

  // Extrato (endpoint 1)
  extrato: (idConta) => http.get(`/contas/${idConta}/extrato`),

  // Extrato (endpoint 2 - fallback)
  extratoAlt: (idConta) => http.get(`/extratos/conta/${idConta}`),

  /**
   * Opcional: resumo (se você implementar no backend).
   * Mantive aqui porque usei no Dashboard (de forma safe).
   * Se não existir no backend, basta remover.
   */
  resumo: async () => {
    // estratégia simples: não inventa endpoint.
    // você pode criar GET /contas/resumo no backend depois.
    // aqui retornamos um valor default para não quebrar.
    return { total: 0, saldoTotal: 0, ultimaContaId: null };
  },
};

export default contasApi;