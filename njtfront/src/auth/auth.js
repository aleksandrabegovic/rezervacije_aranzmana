import http from "../api";
import { setAuth, clearAuth } from "./storage";
 
export async function loginApi({ korisnickoIme, lozinka }) {
  const { data } = await http.post("/auth/login", { korisnickoIme, lozinka });
  setAuth(data); // npr. snimi token/user u storage
  return data;
} 
export async function registerApi(payload) {
  // payload: { ime, prezime, korisnickoIme, lozinka }
  const { data } = await http.post("/auth/register", payload);
  return data;
}
 
export function logoutApi() {
  clearAuth();
  return Promise.resolve();
}
