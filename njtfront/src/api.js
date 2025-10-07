import axios from "axios";
import { getToken } from "./auth/storage";
 

 
const http = axios.create({
  baseURL: "http://localhost:8080/api", // po potrebi promeni na /api
  withCredentials: false,
});

 
http.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

 

export default http;
