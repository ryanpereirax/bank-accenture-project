export function normalizeApiError(err) {
  const status = err?.response?.status;
  const data = err?.response?.data;

  if (typeof data === "string") {
    return status ? `${status} - ${data}` : data;
  }

  if (data && typeof data === "object") {
    if (data.message) return status ? `${status} - ${data.message}` : String(data.message);
    if (data.error) return status ? `${status} - ${data.error}` : String(data.error);

    if (Array.isArray(data.errors) && data.errors.length > 0) {
      const msg = data.errors
        .map((e) => e?.message || e?.defaultMessage || (typeof e === "string" ? e : null))
        .filter(Boolean)
        .join(" | ");
      if (msg) return status ? `${status} - ${msg}` : msg;
    }

    if (data.detail) return status ? `${status} - ${data.detail}` : String(data.detail);
    if (data.details) return status ? `${status} - ${data.details}` : String(data.details);
  }

  if (err?.message) {
    return status ? `${status} - ${err.message}` : err.message;
  }

  const name = err?.name ? String(err.name) : "Erro";
  const code = err?.code ? ` (${err.code})` : "";
  return status ? `${status} - ${name}${code}` : `${name}${code}`;
}
