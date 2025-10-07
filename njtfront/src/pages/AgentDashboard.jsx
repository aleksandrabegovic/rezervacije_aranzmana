import React, { useEffect, useMemo, useState } from "react";
import "./agent.css";
import http from "../api";
import { useAuth } from "../auth/AuthContext";

const TABS = ["Putnici", "Aranžmani", "Rezervacije"];

export default function AgentDashboard() {
  const [tab, setTab] = useState("Putnici");

  return (
    <div className="agent-root">
      <header className="agent-header">
        <h1>Agent Dashboard</h1>
        <p>Upravljaj putnicima, aranžmanima i rezervacijama na jednoj stranici.</p>
      </header>

      <nav className="agent-tabs" role="tablist" aria-label="Agent sekcije">
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
        {tab === "Putnici" && <PutniciPane />}
        {tab === "Aranžmani" && <AranzmaniPane />}
        {tab === "Rezervacije" && <RezervacijePane />}
      </section>
    </div>
  );
}

/* ==================== PUTNICI ==================== */
function PutniciPane() {
  const [lista, setLista] = useState([]);
  const [ime, setIme] = useState("");
  const [prezime, setPrezime] = useState("");
  const [email, setEmail] = useState("");
  const [telefon, setTelefon] = useState("");
  const [loading, setLoading] = useState(false);

  const load = async () => {
    const res = await http.get("/putnik");
    setLista(res.data || []);
  };

  const create = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await http.post("/putnik", { ime, prezime, email, telefon: telefon || null });
      setIme(""); setPrezime(""); setEmail(""); setTelefon("");
      await load();
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id) => {
    if (!window.confirm("Obrisati putnika?")) return;
    await http.delete(`/putnik/${id}`);
    load();
  };

  useEffect(() => { load(); }, []);

  return (
    <div className="pane">
      <div className="pane-head">
        <h2>Putnici</h2>
        <button className="btn" onClick={load}>Osveži</button>
      </div>

      <form className="mini-form" onSubmit={create}>
        <input value={ime} onChange={e => setIme(e.target.value)} placeholder="Ime" required />
        <input value={prezime} onChange={e => setPrezime(e.target.value)} placeholder="Prezime" required />
        <input value={email} onChange={e => setEmail(e.target.value)} type="email" placeholder="Email" required />
        <input value={telefon} onChange={e => setTelefon(e.target.value)} placeholder="Telefon (opciono)" />
        <button className="btn-primary" type="submit" disabled={loading}>
          {loading ? "Dodajem..." : "Dodaj"}
        </button>
      </form>

      <div className="table-wrap">
        <table className="tbl">
          <thead>
            <tr><th>ID</th><th>Ime</th><th>Prezime</th><th>Email</th><th>Telefon</th><th>Akcije</th></tr>
          </thead>
          <tbody>
            {lista.map(p => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.ime}</td>
                <td>{p.prezime}</td>
                <td>{p.email}</td>
                <td>{p.telefon || "-"}</td>
                <td><button className="btn danger" onClick={() => remove(p.id)}>Obriši</button></td>
              </tr>
            ))}
            {lista.length === 0 && <tr><td colSpan={6} className="empty">Nema podataka.</td></tr>}
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
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id) => {
    if (!window.confirm("Obrisati aranžman?")) return;
    await http.delete(`/aranzman/${id}`);
    load();
  };

  useEffect(() => { load(); loadTipovi(); }, []);

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
        <button className="btn-primary" type="submit" disabled={loading}>
          {loading ? "Dodajem..." : "Dodaj"}
        </button>
      </form>

      <div className="table-wrap">
        <table className="tbl">
          <thead>
            <tr><th>ID</th><th>Naziv</th><th>Destinacija</th><th>Tip</th><th>Akcije</th></tr>
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

/* ==================== REZERVACIJE ==================== */
function RezervacijePane() {
  const { user } = useAuth();

  const [lista, setLista] = useState([]);
  const [aranzmani, setAranzmani] = useState([]);
  const [putnici, setPutnici] = useState([]);

  // zaglavlje forme
  const [aranzmanId, setAranzmanId] = useState("");
  const [napomena, setNapomena] = useState("");

  // stavke forme
  const emptyRow = useMemo(() => ({ putnikId: "", kolicina: 1, cena: "", popustProcenat: 0, opis: "" }), []);
  const [stavke, setStavke] = useState([{ putnikId: "", kolicina: 1, cena: "", popustProcenat: 0, opis: "" }]);
  const [submitting, setSubmitting] = useState(false);

  // detalji rezervacija (expand)
  const [open, setOpen] = useState({});     // { [rezId]: true/false }
  const [details, setDetails] = useState({});// { [rezId]: RezervacijaDto }
  const [loadingDetails, setLoadingDetails] = useState({}); // { [rezId]: true/false }

  // lookup za ime putnika po id-u
  const putnikNameById = useMemo(() => {
    const m = new Map();
    putnici.forEach(p => m.set(p.id, `${p.ime} ${p.prezime}${p.email ? ` (${p.email})` : ""}`));
    return m;
  }, [putnici]);

  const fmtMoney = (v) => {
    if (v == null) return "-";
    const n = Number(v);
    if (!isFinite(n)) return String(v);
    return n.toFixed(2);
  };

  const load = async () => {
    const res = await http.get("/rezervacija");
    setLista(res.data || []);
  };
  const loadAranzmani = async () => {
    const res = await http.get("/aranzman");
    setAranzmani(res.data || []);
  };
  const loadPutnici = async () => {
    const res = await http.get("/putnik");
    setPutnici(res.data || []);
  };

  useEffect(() => { load(); loadAranzmani(); loadPutnici(); }, []);

  const lastChosenPutnik = useMemo(() => {
    for (let i = stavke.length - 1; i >= 0; i--) {
      if (stavke[i].putnikId) return stavke[i].putnikId;
    }
    return "";
  }, [stavke]);

  const addRow = () => setStavke(prev => [...prev, { ...emptyRow, putnikId: lastChosenPutnik }]);
  const removeRow = (idx) => setStavke(prev => prev.length > 1 ? prev.filter((_, i) => i !== idx) : prev);
  const updateRow = (idx, field, value) => {
    setStavke(prev => prev.map((row, i) => (i === idx ? { ...row, [field]: value } : row)));
  };

  const validateRows = (rows) => {
    const errors = [];
    rows.forEach((s, i) => {
      const pid = parseInt(s.putnikId, 10);
      const kol = Number(s.kolicina);
      const c = Number(s.cena);
      const pop = Number(s.popustProcenat ?? 0);
      if (!Number.isInteger(pid) || pid <= 0) errors.push(`Red ${i + 1}: izaberi putnika.`);
      if (!Number.isFinite(kol) || kol < 1) errors.push(`Red ${i + 1}: količina mora biti ≥ 1.`);
      if (!Number.isFinite(c) || c <= 0) errors.push(`Red ${i + 1}: cena mora biti > 0.`);
      if (!Number.isFinite(pop) || pop < 0 || pop > 100) errors.push(`Red ${i + 1}: popust 0–100%.`);
    });
    return errors;
  };

  const create = async (e) => {
    e.preventDefault();
    if (submitting) return;

    const nonEmpty = stavke.filter(s => String(s.putnikId).trim() !== "" || String(s.cena).trim() !== "");
    if (!aranzmanId || nonEmpty.length === 0) {
      alert("Izaberi aranžman i dodaj bar jednu stavku.");
      return;
    }
    const rowErrors = validateRows(nonEmpty);
    if (rowErrors.length) {
      alert(rowErrors.join("\n"));
      return;
    }

    const cleanStavke = nonEmpty.map(s => ({
      opis: s.opis?.trim() ? s.opis.trim() : null,
      kolicina: parseInt(s.kolicina, 10),
      cena: Number(s.cena),
      popustProcenat: Number(s.popustProcenat ?? 0),
      putnikId: parseInt(s.putnikId, 10)
    }));

    const payload = {
      napomena: napomena?.trim() ? napomena.trim() : null,
      aranzmanId: parseInt(aranzmanId, 10),
      zaposleniId: user?.id ?? null,
      stavke: cleanStavke
    };

    setSubmitting(true);
    try {
      await http.post("/rezervacija", payload);
      setAranzmanId("");
      setNapomena("");
      setStavke([{ ...emptyRow }]);
      await load();
    } finally {
      setSubmitting(false);
    }
  };

  const removeRez = async (id) => {
    if (!window.confirm("Obrisati rezervaciju?")) return;
    await http.delete(`/rezervacija/${id}`);
    load();
  };

  const toggleDetails = async (rezId) => {
    setOpen(prev => ({ ...prev, [rezId]: !prev[rezId] }));
    // Ako otvaramo i nemamo već detalje — dovuci ih
    const willOpen = !open[rezId];
    if (willOpen && !details[rezId]) {
      setLoadingDetails(prev => ({ ...prev, [rezId]: true }));
      try {
        const res = await http.get(`/rezervacija/${rezId}`);
        setDetails(prev => ({ ...prev, [rezId]: res.data }));
      } finally {
        setLoadingDetails(prev => ({ ...prev, [rezId]: false }));
      }
    }
  };

  return (
    <div className="pane">
      <div className="pane-head">
        <h2>Rezervacije</h2>
        <button className="btn" onClick={load}>Osveži</button>
      </div>

      {/* Forma za dodavanje */}
      <form className="mini-form stack" onSubmit={create}>
        <div className="row">
          <select value={aranzmanId} onChange={e => setAranzmanId(e.target.value)} required>
            <option value="">-- Izaberi aranžman --</option>
            {aranzmani.map(a => (
              <option key={a.id} value={a.id}>
                {a.naziv}{a.destinacija ? ` – ${a.destinacija}` : ""}
              </option>
            ))}
          </select>
          <input
            value={napomena}
            onChange={e => setNapomena(e.target.value)}
            placeholder="Napomena (opciono)"
          />
        </div>

        <div className="table-wrap">
         <table className="tbl form">
            <thead>
              <tr>
                <th>#</th>
                <th>Putnik</th>
                <th>Količina</th>
                <th>Cena</th>
                <th>Popust %</th>
                <th>Opis</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {stavke.map((s, idx) => (
                <tr key={idx}>
                  <td>{idx + 1}</td>
                  <td>
                    <select
                      value={s.putnikId}
                      onChange={e => updateRow(idx, "putnikId", e.target.value)}
                      required
                    >
                      <option value="">-- Izaberi putnika --</option>
                      {putnici.map(p => (
                        <option key={p.id} value={p.id}>
                          {p.ime} {p.prezime}{p.email ? ` (${p.email})` : ""}
                        </option>
                      ))}
                    </select>
                  </td>
                  <td>
                    <input
                      type="number" min="1" step="1"
                      value={s.kolicina}
                      onChange={e => updateRow(idx, "kolicina", e.target.value)}
                      required
                    />
                  </td>
                  <td>
                    <input
                      type="number" step="0.01" min="0.01"
                      value={s.cena}
                      onChange={e => updateRow(idx, "cena", e.target.value)}
                      required
                    />
                  </td>
                  <td>
                    <input
                      type="number" min="0" max="100" step="1"
                      value={s.popustProcenat}
                      onChange={e => updateRow(idx, "popustProcenat", e.target.value)}
                    />
                  </td>
                  <td>
                    <input
                      value={s.opis}
                      onChange={e => updateRow(idx, "opis", e.target.value)}
                      placeholder="Opis (opciono)"
                    />
                  </td>
                  <td>
                    <button type="button" className="btn danger" onClick={() => removeRow(idx)} title="Ukloni stavku">✕</button>
                  </td>
                </tr>
              ))}
              {stavke.length === 0 && (
                <tr><td colSpan={7} className="empty">Nema stavki.</td></tr>
              )}
            </tbody>
          </table>
        </div>

        <div className="controls-bar">
          <button type="button" className="btn" onClick={addRow}>+ Dodaj stavku</button>
          <button className="btn-primary" type="submit" disabled={submitting}>
            {submitting ? "Snimam..." : "Snimi rezervaciju"}
          </button>
        </div>
      </form>

      {/* Lista postojećih rezervacija + EXPAND STAVKE */}
      <div className="table-wrap" style={{ marginTop: 16 }}>
        <table className="tbl">
          <thead>
            <tr>
              <th>ID</th>
              <th>AranžmanID</th>
              <th>Ukupno</th>
              <th>Datum</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {lista.map(r => (
              <React.Fragment key={r.id}>
                <tr>
                  <td>{r.id}</td>
                  <td>{r.aranzmanId}</td>
                  <td>{fmtMoney(r.ukupno)}</td>
                  <td>{r.datumKreiranja}</td>
                  <td style={{ display: "flex", gap: 8 }}>
                    <button className="btn" onClick={() => toggleDetails(r.id)}>
                      {open[r.id] ? "Sakrij stavke" : "Stavke"}
                    </button>
                    <button className="btn danger" onClick={() => removeRez(r.id)}>Obriši</button>
                  </td>
                </tr>
                {open[r.id] && (
                <tr>
                    <td colSpan={5}>
                    <div className="tbl-subrow">
                        {loadingDetails[r.id] && <div className="empty">Učitavam stavke...</div>}
                        {!loadingDetails[r.id] && details[r.id] && Array.isArray(details[r.id].stavke) && details[r.id].stavke.length > 0 ? (
                        <div className="table-wrap" style={{ margin: 8 }}>
                            <table className="tbl compact">
                            <thead>
                              <tr>
                                <th>#</th>
                                <th>Putnik</th>
                                <th>Količina</th>
                                <th>Cena</th>
                                <th>Popust %</th>
                                <th>Iznos</th>
                                <th>Opis</th>
                              </tr>
                            </thead>
                            <tbody>
                              {details[r.id].stavke.map((s, i) => (
                                <tr key={s.id ?? i}>
                                  <td>{i + 1}</td>
                                  <td>{putnikNameById.get(s.putnikId) || `ID ${s.putnikId}`}</td>
                                  <td>{s.kolicina}</td>
                                  <td>{fmtMoney(s.cena)}</td>
                                  <td>{Number(s.popustProcenat ?? 0)}</td>
                                  <td>{fmtMoney(s.iznos)}</td>
                                  <td>{s.opis || "-"}</td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                          
                        </div>
                      ) : (!loadingDetails[r.id] && <div className="empty">Nema stavki za prikaz.</div>)}
                        </div>
                    </td>
                    
                  </tr>
                )}
              </React.Fragment>
            ))}
            {lista.length === 0 && <tr><td colSpan={5} className="empty">Nema podataka.</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
