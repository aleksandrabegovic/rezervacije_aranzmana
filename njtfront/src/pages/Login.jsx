import React, { useState } from "react";
import "./auth.css";

export default function Login() {
  const [showPass, setShowPass] = useState(false);

  const onSubmit = (e) => {
    e.preventDefault();
    // TODO: pozovi tvoj API: /auth/login
    // const payload = Object.fromEntries(new FormData(e.currentTarget));
    // await api.post("/auth/login", payload)
    alert("Login submit (demo)");
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
            Dobrodošli nazad
          </h1>
          <p className="ts-subtitle" style={{ maxWidth: 560 }}>
            Ulogujte se i nastavite planiranje putovanja—mirna jutra na pesku čekaju.
          </p>
        </header>

        <form className="auth-card" onSubmit={onSubmit} noValidate>
          <div className="field">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              name="email"
              type="email"
              placeholder="you@example.com"
              required
              autoComplete="email"
            />
            <small className="hint">Koristite email sa kojim ste se registrovali.</small>
          </div>

          <div className="field">
            <label htmlFor="password">Lozinka</label>
            <div className="password-row">
              <input
                id="password"
                name="password"
                type={showPass ? "text" : "password"}
                placeholder="********"
                required
                autoComplete="current-password"
                minLength={6}
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
          </div>

          <div className="actions">
            <button className="btn-primary" type="submit">Uloguj se</button>
            <p className="muted">
              Nemaš nalog? <a className="link" href="/register">Napravi nalog</a>
            </p>
          </div>
        </form>
      </section>
    </main>
  );
}
