import api from "../api";
import { setAuth, clearAuth } from "./storage";

export async function loginApi({ korisnickoIme, lozinka }) {
 
  const { data } = await api.post("/auth/login", { korisnickoIme, lozinka });
  setAuth(data);
  return data;
}

export async function registerApi(payload) {
 
  const { data } = await api.post("/auth/register", payload);
  return data;
}

export function logoutApi() {
 
  clearAuth();
  return Promise.resolve();
}
