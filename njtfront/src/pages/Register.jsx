import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiEye, FiEyeOff } from "react-icons/fi";
import "./auth.css";
import { registerApi, loginApi } from "../auth/auth";
import { useAuth } from "../auth/AuthContext";

export default function Register() {
  const [showPass, setShowPass] = useState(false);
  const [showPass2, setShowPass2] = useState(false);
  const [err, setErr] = useState("");
  const nav = useNavigate();
  const { setLoggedIn, setUser } = useAuth();

  const onSubmit = async (e) => {
    e.preventDefault();
    setErr("");
    const fd = new FormData(e.currentTarget);
    const ime = fd.get("ime")?.toString().trim();
    const prezime = fd.get("prezime")?.toString().trim();
    const korisnickoIme = fd.get("korisnickoIme")?.toString().trim();
    const lozinka = fd.get("lozinka")?.toString();
    const confirm = fd.get("confirm")?.toString();

    if (lozinka !== confirm) {
      setErr("Lozinke se ne poklapaju.");
      return;
    }

    const payload = { ime, prezime, korisnickoIme, lozinka, uloga: "AGENT" };

    try {
      await registerApi(payload);
      // opcioni auto-login odmah posle registracije:
      try {
        const { user } = await loginApi({ korisnickoIme, lozinka });
        setLoggedIn(true);
        setUser(user || null);
        nav("/", { replace: true });
      } catch {
        nav("/login", { replace: true });
      }
    } catch (ex) {
      const msg = ex?.response?.data?.message || "Registracija nije uspela.";
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
            Kreiraj nalog
          </h1>
          <p className="ts-subtitle" style={{ maxWidth: 560 }}>
            Registruj se i otključaj personalizovane preporuke destinacija.
          </p>
        </header>

        <form className="auth-card" onSubmit={onSubmit} noValidate>
          {err && <div className="auth-error">{err}</div>}

          <div className="field two-col">
            <div>
              <label htmlFor="ime">Ime</label>
              <input id="ime" name="ime" type="text" placeholder="Vanja" required />
            </div>
            <div>
              <label htmlFor="prezime">Prezime</label>
              <input id="prezime" name="prezime" type="text" placeholder="Vizi" required />
            </div>
          </div>

          <div className="field">
            <label htmlFor="korisnickoIme">Korisničko ime</label>
            <input id="korisnickoIme" name="korisnickoIme" type="text" placeholder="korisnik123" required />
          </div>

          <div className="field two-col">
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
                  autoComplete="new-password"
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
              <small className="hint">Min. 6 karaktera.</small>
            </div>

            <div className="field">
              <label htmlFor="confirm">Potvrdi lozinku</label>
              <div className="password-row">
                <input
                  id="confirm"
                  name="confirm"
                  type={showPass2 ? "text" : "password"}
                  placeholder="********"
                  required
                  minLength={6}
                  autoComplete="new-password"
                />
                <span
                  className="toggle"
                  role="button"
                  tabIndex={0}
                  onClick={() => setShowPass2((v) => !v)}
                  onKeyDown={(e) => (e.key === "Enter" || e.key === " ") && setShowPass2((v) => !v)}
                  aria-label={showPass2 ? "Sakrij lozinku" : "Prikaži lozinku"}
                  title={showPass2 ? "Sakrij lozinku" : "Prikaži lozinku"}
                >
                  {showPass2 ? <FiEyeOff /> : <FiEye />}
                </span>
              </div>
            </div>
          </div>

          <div className="actions">
            <button className="btn-primary" type="submit">Registruj se</button>
            <p className="muted">
              Već imaš nalog? <Link className="link" to="/login">Uloguj se</Link>
            </p>
          </div>
        </form>
      </section>
    </main>
  );
}
