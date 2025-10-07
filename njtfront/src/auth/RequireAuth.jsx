import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function RequireAuth({ children }) {
  const { loggedIn } = useAuth();
  const loc = useLocation();
  if (!loggedIn) return <Navigate to="/login" replace state={{ from: loc }} />;
  return children;
}
