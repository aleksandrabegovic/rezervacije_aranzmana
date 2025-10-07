import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function RequireRole({ role, children }) {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  if (Array.isArray(role) ? !role.includes(user.uloga) : user.uloga !== role) {
    // nema dozvolu , pošalji ga na početnu korisničku stranicu
    return <Navigate to="/app" replace />;
  }
  return children;
}
