import { createContext, useContext, useMemo, useState, useEffect } from "react";
import { getUser, isLoggedIn, clearAuth } from "./storage";

const AuthCtx = createContext(null);

export function AuthProvider({ children }) {
  const [loggedIn, setLoggedIn] = useState(isLoggedIn());
  const [user, setUser] = useState(getUser());

  useEffect(() => {
    setLoggedIn(isLoggedIn());
    setUser(getUser());
  }, []);

  const value = useMemo(() => ({
    loggedIn,
    user,
    setLoggedIn,
    setUser,
    logoutLocal: () => { clearAuth(); setLoggedIn(false); setUser(null); },
  }), [loggedIn, user]);

  return <AuthCtx.Provider value={value}>{children}</AuthCtx.Provider>;
}

export function useAuth() {
  return useContext(AuthCtx);
}
