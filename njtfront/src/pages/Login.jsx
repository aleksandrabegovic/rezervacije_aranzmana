// src/pages/Login.jsx
import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiEye, FiEyeOff } from "react-icons/fi";
import "./auth.css";
import { loginApi } from "../auth/auth";
import { useAuth } from "../auth/AuthContext";

export default function Login() {
  const [showPass, setShowPass] = useState(false);
  const [err, setErr] = useState("");
  const nav = useNavigate();
  const { setLoggedIn, setUser } = useAuth();

  const onSubmit = async (e) => {
    e.preventDefault();
    setErr("");
    const fd = new FormData(e.currentTarget);
    const korisnickoIme = fd.get("korisnickoIme")?.toString().trim();
    const lozinka = fd.get("lozinka")?.toString();

    try {
      const { user } = await loginApi({ korisnickoIme, lozinka });
      setLoggedIn(true);
      setUser(user || null);

      // ✅ Redirect po ulozi
      if (user?.uloga === "AGENT") {
        nav("/agent", { replace: true });
      } else {
        nav("/app", { replace: true });
      }
    } catch (ex) {
      const msg = ex?.response?.data?.message || "Neuspešna prijava. Proveri podatke.";
      setErr(msg);
    }
  };

  return (
    <main className="ts-root auth-root">
      <div className="ts-waves" aria-hidden="true">
        <div className="ts-wave ts-wave1" />
        <div className="ts-wave ts-wave2" />
        <div className="ts-wave ts-wave3" />
      </div>
      <div className="ts-clouds" aria-hidden="true">
        <span className="ts-cloud c1" />
        <span className="ts-cloud c2" />
        <span className="ts-cloud c3" />
      </div>

      <section className="auth-wrap">
        <header className="auth-header">
          <h1 className="ts-title" style={{ fontSize: "clamp(28px,5vw,48px)" }}>
            Dobrodošli nazad
          </h1>
          <p className="ts-subtitle" style={{ maxWidth: 560 }}>
            Ulogujte se i nastavite planiranje putovanja.
          </p>
        </header>

        <form className="auth-card" onSubmit={onSubmit} noValidate>
          {err && <div className="auth-error">{err}</div>}

          <div className="field">
            <label htmlFor="korisnickoIme">Korisničko ime</label>
            <input
              id="korisnickoIme"
              name="korisnickoIme"
              type="text"
              placeholder="korisnik123"
              required
              autoComplete="username"
            />
          </div>

          <div className="field">
            <label htmlFor="lozinka">Lozinka</label>
            <div className="password-row">
              <input
                id="lozinka"
                name="lozinka"
                type={showPass ? "text" : "password"}
                placeholder="********"
                required
                minLength={6}
                autoComplete="current-password"
              />
              <span
                className="toggle"
                role="button"
                tabIndex={0}
                onClick={() => setShowPass((v) => !v)}
                onKeyDown={(e) => (e.key === "Enter" || e.key === " ") && setShowPass((v) => !v)}
                aria-label={showPass ? "Sakrij lozinku" : "Prikaži lozinku"}
                title={showPass ? "Sakrij lozinku" : "Prikaži lozinku"}
              >
                {showPass ? <FiEyeOff /> : <FiEye />}
              </span>
            </div>
          </div>

          <div className="actions">
            <button className="btn-primary" type="submit">Uloguj se</button>
            <p className="muted">
              Nemaš nalog? <Link className="link" to="/register">Napravi nalog</Link>
            </p>
          </div>
        </form>
      </section>
    </main>
  );
}
