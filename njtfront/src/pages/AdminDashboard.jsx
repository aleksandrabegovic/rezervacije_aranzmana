// src/pages/AdminDashboard.jsx
import React, { useEffect, useMemo, useState } from "react";
import "./agent.css";
import http from "../api";

/**
 * ADMIN DASHBOARD
 * Tabovi: Zaposleni | Aranžmani | Tipovi
 */
const TABS = ["Zaposleni", "Aranžmani", "Tipovi"];

export default function AdminDashboard() {
  const [tab, setTab] = useState("Zaposleni");
  return (
    <div className="agent-root">
      <header className="agent-header">
        <h1>Admin Dashboard</h1>
        <p>Pregled i administracija zaposlenih, aranžmana i tipova aranžmana.</p>
      </header>

      <nav className="agent-tabs" role="tablist" aria-label="Admin sekcije">
        {TABS.map((t) => (
          <button
            key={t}
            role="tab"
            aria-selected={tab === t}
            className={`tab-btn ${tab === t ? "active" : ""}`}
            onClick={() => setTab(t)}
          >
            {t}
          </button>
        ))}
      </nav>

      <section className="agent-content">
        {tab === "Zaposleni" && <ZaposleniPane />}
        {tab === "Aranžmani" && <AranzmaniPane />}
        {tab === "Tipovi" && <TipoviPane />}
      </section>
    </div>
  );
}

/* ==================== ZAPOSLENI ==================== */
function ZaposleniPane() {
  const [lista, setLista] = useState([]);
  const [ime, setIme] = useState("");
  const [prezime, setPrezime] = useState("");
  const [korisnickoIme, setKorisnickoIme] = useState("");
  const [uloga, setUloga] = useState("");
  const [lozinka, setLozinka] = useState("");
  const [loading, setLoading] = useState(false);

  // Ako backend nema endpoint za sve uloge, napravi “pametnu” listu:
  const detektovaneUloge = useMemo(() => {
    const set = new Set(lista.map((z) => z.uloga).filter(Boolean));
    // fallback predefinisane ako nijedna nije detektovana
    if (set.size === 0) return ["ADMIN", "AGENT"];
    return Array.from(set);
  }, [lista]);

  const load = async () => {
    const res = await http.get("/zaposleni");
    setLista(res.data || []);
  };

  useEffect(() => { load(); }, []);

  const create = async (e) => {
    e.preventDefault();
    if (!uloga) { alert("Izaberi ulogu."); return; }
    if (!lozinka) { alert("Unesi privremenu lozinku."); return; }
    setLoading(true);
    try {
      const body = { ime, prezime, korisnickoIme, uloga };
      await http.post(`/zaposleni?pwd=${encodeURIComponent(lozinka)}`, body);
      setIme(""); setPrezime(""); setKorisnickoIme(""); setUloga(""); setLozinka("");
      await load();
    } finally { setLoading(false); }
  };

  const remove = async (id) => {
    if (!window.confirm("Obrisati zaposlenog?")) return;
    await http.delete(`/zaposleni/${id}`);
    load();
  };

  const [editRole, setEditRole] = useState({}); // { [id]: "ADMIN" }
  const startEditRole = (id, curr) => setEditRole((p) => ({ ...p, [id]: curr || "" }));
  const changeEditRole = (id, val) => setEditRole((p) => ({ ...p, [id]: val }));
  const saveRole = async (z) => {
    const nova = editRole[z.id];
    if (!nova) { alert("Izaberi ulogu."); return; }
    await http.put(`/zaposleni/${z.id}`, { ...z, uloga: nova });
    setEditRole((p) => { const c = { ...p }; delete c[z.id]; return c; });
    load();
  };
  const cancelRole = (id) => setEditRole((p) => { const c = { ...p }; delete c[id]; return c; });

  return (
    <div className="pane">
      <div className="pane-head">
        <h2>Zaposleni</h2>
        <button className="btn" onClick={load}>Osveži</button>
      </div>

      {/* Dodaj zaposlenog */}
      <form className="mini-form" onSubmit={create}>
        <input value={ime} onChange={e => setIme(e.target.value)} placeholder="Ime" required />
        <input value={prezime} onChange={e => setPrezime(e.target.value)} placeholder="Prezime" required />
        <input value={korisnickoIme} onChange={e => setKorisnickoIme(e.target.value)} placeholder="Korisničko ime" required />
        <select value={uloga} onChange={e => setUloga(e.target.value)} required>
          <option value="">-- Uloga --</option>
          {[...new Set([...detektovaneUloge, "ADMIN", "AGENT"])].map((u) => (
            <option key={u} value={u}>{u}</option>
          ))}
        </select>
        <input value={lozinka} onChange={e => setLozinka(e.target.value)} placeholder="Privremena lozinka" required />
        <button className="btn-primary" type="submit" disabled={loading}>{loading ? "Dodajem..." : "Dodaj"}</button>
      </form>

      {/* Tabela */}
      <div className="table-wrap">
        <table className="tbl">
          <thead>
            <tr>
              <th>ID</th>
              <th>Ime</th>
              <th>Prezime</th>
              <th>Korisničko ime</th>
              <th>Uloga</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {lista.map((z) => {
              const editing = editRole.hasOwnProperty(z.id);
              return (
                <tr key={z.id}>
                  <td>{z.id}</td>
                  <td>{z.ime}</td>
                  <td>{z.prezime}</td>
                  <td>{z.korisnickoIme}</td>
                  <td style={{ minWidth: 180 }}>
                    {!editing ? (
                      <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
                        <span>{z.uloga || "-"}</span>
                        <button className="btn" type="button" onClick={() => startEditRole(z.id, z.uloga)}>Izmeni</button>
                      </div>
                    ) : (
                      <div style={{ display: "flex", gap: 8 }}>
                        <select value={editRole[z.id] || ""} onChange={(e) => changeEditRole(z.id, e.target.value)}>
                          <option value="">-- Uloga --</option>
                          {[...new Set([...detektovaneUloge, "ADMIN", "AGENT"])].map((u) => (
                            <option key={u} value={u}>{u}</option>
                          ))}
                        </select>
                        <button className="btn-primary" type="button" onClick={() => saveRole(z)}>Sačuvaj</button>
                        <button className="btn" type="button" onClick={() => cancelRole(z.id)}>Otkaži</button>
                      </div>
                    )}
                  </td>
                  <td>
                    <button className="btn danger" onClick={() => remove(z.id)}>Obriši</button>
                  </td>
                </tr>
              );
            })}
            {lista.length === 0 && <tr><td colSpan={6} className="empty">Nema podataka.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}

/* ==================== TIPOVI ARANŽMANA ==================== */
function TipoviPane() {
  const [lista, setLista] = useState([]);
  const [naziv, setNaziv] = useState("");
  const [loading, setLoading] = useState(false);

  const load = async () => {
    const res = await http.get("/tip-aranzmana");
    setLista(res.data || []);
  };
  useEffect(() => { load(); }, []);

  const create = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await http.post("/tip-aranzmana", { naziv });
      setNaziv("");
      await load();
    } finally { setLoading(false); }
  };

  const remove = async (id) => {
    if (!window.confirm("Obrisati tip aranžmana?")) return;
    await http.delete(`/tip-aranzmana/${id}`);
    load();
  };

  return (
    <div className="pane">
      <div className="pane-head">
        <h2>Tipovi aranžmana</h2>
        <button className="btn" onClick={load}>Osveži</button>
      </div>

      <form className="mini-form" onSubmit={create}>
        <input value={naziv} onChange={e => setNaziv(e.target.value)} placeholder="Naziv tipa" required />
        <button className="btn-primary" type="submit" disabled={loading}>{loading ? "Dodajem..." : "Dodaj"}</button>
      </form>

      <div className="table-wrap">
        <table className="tbl">
          <thead>
            <tr>
              <th>ID</th>
              <th>Naziv</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {lista.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{t.naziv}</td>
                <td><button className="btn danger" onClick={() => remove(t.id)}>Obriši</button></td>
              </tr>
            ))}
            {lista.length === 0 && <tr><td colSpan={3} className="empty">Nema podataka.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}

/* ==================== ARANŽMANI ==================== */
function AranzmaniPane() {
  const [lista, setLista] = useState([]);
  const [tipovi, setTipovi] = useState([]);
  const [naziv, setNaziv] = useState("");
  const [destinacija, setDestinacija] = useState("");
  const [opis, setOpis] = useState("");
  const [tipId, setTipId] = useState("");
  const [loading, setLoading] = useState(false);

  const load = async () => {
    const res = await http.get("/aranzman");
    setLista(res.data || []);
  };
  const loadTipovi = async () => {
    const res = await http.get("/tip-aranzmana");
    setTipovi(res.data || []);
  };
  useEffect(() => { load(); loadTipovi(); }, []);

  const create = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await http.post("/aranzman", {
        naziv,
        destinacija,
        opis: opis || null,
        tipId: Number(tipId)
      });
      setNaziv(""); setDestinacija(""); setOpis(""); setTipId("");
      await load();
    } finally { setLoading(false); }
  };

  const remove = async (id) => {
    if (!window.confirm("Obrisati aranžman?")) return;
    await http.delete(`/aranzman/${id}`);
    load();
  };

  return (
    <div className="pane">
      <div className="pane-head">
        <h2>Aranžmani</h2>
        <button className="btn" onClick={load}>Osveži</button>
      </div>

      <form className="mini-form" onSubmit={create}>
        <input value={naziv} onChange={e => setNaziv(e.target.value)} placeholder="Naziv" required />
        <input value={destinacija} onChange={e => setDestinacija(e.target.value)} placeholder="Destinacija" required />
        <input value={opis} onChange={e => setOpis(e.target.value)} placeholder="Opis (opciono)" />
        <select value={tipId} onChange={e => setTipId(e.target.value)} required>
          <option value="">-- Tip aranžmana --</option>
          {tipovi.map(t => (<option key={t.id} value={t.id}>{t.naziv}</option>))}
        </select>
        <button className="btn-primary" type="submit" disabled={loading}>{loading ? "Dodajem..." : "Dodaj"}</button>
      </form>

      <div className="table-wrap">
        <table className="tbl">
          <thead>
            <tr>
              <th>ID</th>
              <th>Naziv</th>
              <th>Destinacija</th>
              <th>Tip</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {lista.map(a => (
              <tr key={a.id}>
                <td>{a.id}</td>
                <td>{a.naziv}</td>
                <td>{a.destinacija}</td>
                <td>{a.tipId}</td>
                <td><button className="btn danger" onClick={() => remove(a.id)}>Obriši</button></td>
              </tr>
            ))}
            {lista.length === 0 && <tr><td colSpan={5} className="empty">Nema podataka.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
