import { NavLink, useNavigate } from "react-router-dom";
import "./navbar.css";
import { useAuth } from "../auth/AuthContext";
import { logoutApi } from "../auth/auth";
import { FiLogOut, FiLogIn, FiUserPlus } from "react-icons/fi";

export default function NavBar() {
  const { loggedIn, user, logoutLocal } = useAuth();
  const nav = useNavigate();

  const logout = async () => {
    try { await logoutApi(); } finally {
      logoutLocal();
      nav("/", { replace: true });
    }
  };

  return (
    <header className="tn-topbar">
      <div className="tn-topbar-bg" aria-hidden="true">
        <span className="wave w1" />
        <span className="wave w2" />
      </div>

      <div className="tn-inner">
        <div className="tn-brand">
          <span className="logo-dot" />
          <span className="brand-name">RezervacijeAranzmana</span>
        </div>

        <nav className="tn-nav" aria-label="Glavna navigacija">
          <NavLink to="/" end className="tn-link">Početna</NavLink> 

          {!loggedIn ? (
            <>
              <NavLink to="/login" className="tn-link">
                <FiLogIn style={{verticalAlign:"text-bottom"}} /> Login
              </NavLink>
              <NavLink to="/register" className="tn-link">
                <FiUserPlus style={{verticalAlign:"text-bottom"}} /> Registracija
              </NavLink>
            </>
          ) : (
            <span className="tn-link" role="button" onClick={logout} title="Odjavi se">
              <FiLogOut style={{verticalAlign:"text-bottom"}} /> Logout
              {user?.korisnickoIme ? <span style={{marginLeft:8, opacity:.8}}>({user.korisnickoIme})</span> : null}
            </span>
          )}
        </nav>
      </div>
    </header>
  );
}
