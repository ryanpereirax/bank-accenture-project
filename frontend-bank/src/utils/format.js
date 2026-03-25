export function toNumber(value) {
  if (value === null || value === undefined) return 0;

  // aceita "100,50" ou "100.50"
  const s = String(value).trim().replace(/\./g, "").replace(",", ".");
  const n = Number(s);

  return Number.isFinite(n) ? n : 0;
}

export function toMoney(value) {
  const n = typeof value === "number" ? value : toNumber(value);

  // Evita NaN
  const safe = Number.isFinite(n) ? n : 0;

  return safe.toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
}

export function toDateTimeBR(value) {
  if (!value) return "-";

  // value pode vir como ISO string: "2026-03-24T10:20:30"
  const d = value instanceof Date ? value : new Date(value);

  if (Number.isNaN(d.getTime())) return String(value);

  return d.toLocaleString("pt-BR", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
}