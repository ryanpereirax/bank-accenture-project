import { http } from "./http";

const clientesApi = {
  listar: () => http.get("/clientes"),

  buscarPorId: (id) => http.get(`/clientes/${id}`),

  criar: (payload) => http.post("/clientes", payload),

  // Se seu backend não tiver delete, pode remover este método
  excluir: (id) => http.del(`/clientes/${id}`),
};

export default clientesApi;
