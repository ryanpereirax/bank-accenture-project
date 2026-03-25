import axios from "axios";

/**
 * Base URL da API:
 * - Preferência: REACT_APP_API_URL no .env
 * - Fallback: http://localhost:8080
 */
const baseURL =
  process.env.REACT_APP_API_URL?.trim() || "http://localhost:8080";

export const api = axios.create({
  baseURL,
  timeout: 15000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor simples (opcional): você pode logar requests aqui
api.interceptors.request.use(
  (config) => config,
  (error) => Promise.reject(error)
);

// Interceptor de response: mantém o erro “cru” pro normalizeApiError tratar
api.interceptors.response.use(
  (response) => response,
  (error) => Promise.reject(error)
);

// Helpers (opcional, mas deixa o service mais limpo)
export const http = {
  get: (url, config) => api.get(url, config).then((r) => r.data),
  post: (url, data, config) => api.post(url, data, config).then((r) => r.data),
  put: (url, data, config) => api.put(url, data, config).then((r) => r.data),
  del: (url, config) => api.delete(url, config).then((r) => r.data),
};

export default api;
