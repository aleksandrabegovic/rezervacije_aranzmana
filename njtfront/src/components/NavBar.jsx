import { NavLink } from "react-router-dom";
import "./navbar.css";

export default function NavBar() {
  return (
    <header className="tn-topbar">
      {/* dekorativni talasi u traci */}
      <div className="tn-topbar-bg" aria-hidden="true">
        <span className="wave w1" />
        <span className="wave w2" />
      </div>

      <div className="tn-inner">
        <div className="tn-brand">
          <span className="logo-dot" />
          <span className="brand-name">MirnaTalasa</span>
        </div>

        <nav className="tn-nav" aria-label="Glavna navigacija">
          <NavLink to="/" end className="tn-link">
            Početna
          </NavLink>
          <NavLink to="/destinacije" className="tn-link">
            Destinacije
          </NavLink>
          <NavLink to="/inspiracija" className="tn-link">
            Inspiracija
          </NavLink>
          <NavLink to="/o-nama" className="tn-link">
            O nama
          </NavLink>
          <NavLink to="/kontakt" className="tn-link">
            Kontakt
          </NavLink>
          <NavLink to="/login" className="tn-link">
            Login
          </NavLink>
          <NavLink to="/register" className="tn-link">
            Registracija
          </NavLink>
        </nav>
      </div>
    </header>
  );
}
