const KEY = "auth_token";
const EXP = "auth_exp";
const USER = "auth_user";

export function setAuth({ token, expiresAtEpochSec, user }) {
  if (token) sessionStorage.setItem(KEY, token);
  if (expiresAtEpochSec) sessionStorage.setItem(EXP, String(expiresAtEpochSec));
  if (user) sessionStorage.setItem(USER, JSON.stringify(user));
}

export function clearAuth() {
  sessionStorage.removeItem(KEY);
  sessionStorage.removeItem(EXP);
  sessionStorage.removeItem(USER);
}

export function getToken() {
  return sessionStorage.getItem(KEY);
}

export function getUser() {
  try { return JSON.parse(sessionStorage.getItem(USER) || "null"); }
  catch { return null; }
}

export function isLoggedIn() {
  const t = getToken();
  if (!t) return false;
  const expStr = sessionStorage.getItem(EXP);
  if (!expStr) return true;
  const exp = Number(expStr);
  const now = Math.floor(Date.now() / 1000);
  return now < exp;
}
