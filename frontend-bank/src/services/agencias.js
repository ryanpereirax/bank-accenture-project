import { http } from "./http";

export default {
  listar: () => http.get("/agencias"),
  buscarPorId: (id) => http.get(`/agencias/${id}`),
  criar: (payload) => http.post("/agencias", payload),
};