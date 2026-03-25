export function normalizeApiError(err) {
  if (!err) return "Erro desconhecido.";

  // Axios error
  const status = err?.response?.status;
  const data = err?.response?.data;

  // 1) Se backend retorna string (ex: GlobalExceptionHandler retorna String)
  if (typeof data === "string") {
    return status ? `${status} - ${data}` : data;
  }

  // 2) Se backend retorna JSON com "message"
  if (data && typeof data === "object") {
    if (data.message) return status ? `${status} - ${data.message}` : String(data.message);
    if (data.error) return status ? `${status} - ${data.error}` : String(data.error);
  }

  // 3) fallback: msg do axios / js
  if (err.message) {
    return status ? `${status} - ${err.message}` : err.message;
  }

  try {
    return JSON.stringify(err);
  } catch {
    return "Erro inesperado.";
  }
}
