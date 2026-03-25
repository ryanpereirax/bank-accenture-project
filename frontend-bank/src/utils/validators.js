export function onlyDigits(str) {
  return String(str || "").replace(/\D/g, "");
}

// ✅ VALIDAÇÃO REAL DE CPF
export function isCpfValid(cpf) {
  const digits = onlyDigits(cpf);

  if (digits.length !== 11) return false;

  if (/^(\d)\1+$/.test(digits)) return false;

  let sum = 0;
  for (let i = 0; i < 9; i++) {
    sum += Number(digits[i]) * (10 - i);
  }

  let firstDigit = (sum * 10) % 11;
  if (firstDigit === 10) firstDigit = 0;
  if (firstDigit !== Number(digits[9])) return false;

  sum = 0;
  for (let i = 0; i < 10; i++) {
    sum += Number(digits[i]) * (11 - i);
  }

  let secondDigit = (sum * 10) % 11;
  if (secondDigit === 10) secondDigit = 0;

  return secondDigit === Number(digits[10]);
}

export function isPositiveMoney(value) {
  if (value === null || value === undefined) return false;

  const s = String(value).trim();
  if (!s) return false;

  const normalized = s.replace(/\./g, "").replace(",", ".");
  const n = Number(normalized);

  return Number.isFinite(n) && n > 0;
}