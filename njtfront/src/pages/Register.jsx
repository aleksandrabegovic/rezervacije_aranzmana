import React, { useState } from "react";
import "./auth.css";

export default function Register() {
  const [showPass, setShowPass] = useState(false);
  const [showPass2, setShowPass2] = useState(false);

  const onSubmit = (e) => {
    e.preventDefault();
    const fd = new FormData(e.currentTarget);
    const pass = fd.get("password");
    const confirm = fd.get("confirm");
    if (pass !== confirm) {
      alert("Lozinke se ne poklapaju.");
      return;
    }
    // TODO: pozovi tvoj API: /auth/register
    // const payload = Object.fromEntries(fd);
    // await api.post("/auth/register", payload)
    alert("Register submit (demo)");
  };

  return (
    <main className="ts-root auth-root">
      {/* Dekoracija: talasi + oblaci */}
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
          <div className="field two-col">
            <div>
              <label htmlFor="firstName">Ime</label>
              <input id="firstName" name="firstName" type="text" placeholder="Vanja" required />
            </div>
            <div>
              <label htmlFor="lastName">Prezime</label>
              <input id="lastName" name="lastName" type="text" placeholder="Vizi" required />
            </div>
          </div>

          <div className="field">
            <label htmlFor="email">Email</label>
            <input id="email" name="email" type="email" placeholder="you@example.com" required />
          </div>

          <div className="field">
            <label htmlFor="phone">Telefon (opciono)</label>
            <input id="phone" name="phone" type="tel" placeholder="+381 6x xxx xxxx" />
          </div>

          <div className="field two-col">
            <div className="field">
              <label htmlFor="password">Lozinka</label>
              <div className="password-row">
                <input
                  id="password"
                  name="password"
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
                  {showPass ? "🙈" : "👁️"}
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
                  {showPass2 ? "🙈" : "👁️"}
                </span>
              </div>
            </div>
          </div>

          <div className="actions">
            <button className="btn-primary" type="submit">Registruj se</button>
            <p className="muted">
              Već imaš nalog? <a className="link" href="/login">Uloguj se</a>
            </p>
          </div>
        </form>
      </section>
    </main>
  );
}
