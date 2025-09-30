import React from "react";
import "./travel-showcase.css";

export default function TravelShowcase() {
  return (
    <main className="ts-root">
      {/* Dekorativni talasi u pozadini */}
      <div className="ts-waves" aria-hidden="true">
        <div className="ts-wave ts-wave1" />
        <div className="ts-wave ts-wave2" />
        <div className="ts-wave ts-wave3" />
      </div>

      {/* Parallax oblaci */}
      <div className="ts-clouds" aria-hidden="true">
        <span className="ts-cloud c1" />
        <span className="ts-cloud c2" />
        <span className="ts-cloud c3" />
      </div>

      {/* HERO */}
      <header className="ts-hero">
        <div className="ts-hero-glow" aria-hidden="true" />
        <h1 className="ts-title">
          Otkrij<span className="ts-title-accent"> svet</span> bez žurbe
        </h1>
        <p className="ts-subtitle">
          Turistička agencija za dušu — mirna jutra na pesku, zlatni zalasci, i
          sećanja koja mirišu na so i borove.
        </p>

        <ul className="ts-badges" aria-label="Istaknute pogodnosti">
          <li className="ts-badge">All-inclusive</li>
          <li className="ts-badge">Rana letnja putovanja</li>
          <li className="ts-badge">Porodični popusti</li>
          <li className="ts-badge">Egzotične destinacije</li>
        </ul>

        <div className="ts-scroll-indicator" aria-hidden="true">
          <span className="dot" />
          <span className="dot" />
          <span className="dot" />
        </div>
      </header>

      {/* DESTINACIJE */}
      <section className="ts-section">
        <h2 className="ts-h2">Mesta koja volimo</h2>

        <div className="ts-grid">
          {DESTINATIONS.map((d, i) => (
            <article className="ts-card" key={i}>
              <div
                className="ts-card-cover"
                style={{ backgroundImage: `url(${d.image})` }}
                role="img"
                aria-label={d.title}
              />
              <div className="ts-card-body">
                <h3 className="ts-card-title">{d.title}</h3>
                <p className="ts-card-text">{d.desc}</p>
                <div className="ts-card-meta">
                  <span className="ts-chip">{d.season}</span>
                  <span className="ts-chip">{d.vibe}</span>
                </div>
              </div>
            </article>
          ))}
        </div>
      </section>

      {/* STORY STRIP */}
      <section className="ts-strip" aria-label="Morske crtice">
        <div className="ts-strip-track">
          <span>sunrise • sea breeze • barefoot • coconut • hammock • </span>
          <span>sunrise • sea breeze • barefoot • coconut • hammock • </span>
          <span>sunrise • sea breeze • barefoot • coconut • hammock • </span>
        </div>
      </section>

      {/* INSPIRACIJA / MAGAZIN */}
      <section className="ts-section ts-mag">
        <h2 className="ts-h2">Inspiracija za odmor</h2>
        <div className="ts-mag-grid">
          {INSPIRATION.map((x, i) => (
            <article className="ts-mag-item" key={i}>
              <img className="ts-mag-img" src={x.image} alt={x.title} />
              <div className="ts-mag-overlay" />
              <div className="ts-mag-content">
                <h3>{x.title}</h3>
                <p>{x.snippet}</p>
              </div>
            </article>
          ))}
        </div>
      </section>

      {/* TESTIMONIALS */}
      <section className="ts-section ts-testimonials">
        <h2 className="ts-h2">Šta kažu putnici</h2>
        <div className="ts-marquee" aria-label="Utisci putnika">
          <div className="ts-marquee-track">
            {TESTIMONIALS.map((t, i) => (
              <figure className="ts-quote" key={i}>
                <blockquote>“{t.text}”</blockquote>
                <figcaption>— {t.name}</figcaption>
              </figure>
            ))}
            {/* dupliramo za beskonačni loop */}
            {TESTIMONIALS.map((t, i) => (
              <figure className="ts-quote" key={`dup-${i}`}>
                <blockquote>“{t.text}”</blockquote>
                <figcaption>— {t.name}</figcaption>
              </figure>
            ))}
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="ts-footer">
        <div className="ts-footer-wave" aria-hidden="true" />
        <p>
          © {new Date().getFullYear()} MirnaTalasa — putovanja koja ostaju.
        </p>
      </footer>
    </main>
  );
}

const DESTINATIONS = [
  {
    title: "Santorini, Grčka",
    desc: "Bele kućice, kobaltno plavo more i zalasci koji “zaustavljaju” vreme.",
    season: "maj–sept",
    vibe: "romantično",
    image:
      "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcQ_tWsY6tBkFSAVY45xbWkEAgHNEg7jKAifEsdH-sC4DLPWUpAYspvgf-BZCXyF3BHDhnSuvVGO0cCehkI7-C9z3kK0_iJ-Oul84zzX0g",
  },
  {
    title: "Amalfi, Italija",
    desc: "Limunovi, klifovi i mirni zalivi — la dolce vita na obali.",
    season: "april–okt",
    vibe: "gastro",
    image:
      "https://encrypted-tbn1.gstatic.com/licensed-image?q=tbn:ANd9GcQ7s0rrldBkqB7h3ITPcem8mjZny4UddjOxa5nJZSoy9XLpwcF7JLBUFbHLFy8fN1GwS3i1CJGGx3Fqu9uqXzfCke0g7cZ6SWe6LhbzMg",
  },
  {
    title: "Zanzibar, Tanzanija",
    desc: "Tirkizna laguna, začini i pesak koji šušti kao svila.",
    season: "jun–okt",
    vibe: "egzotično",
    image:
      "https://www.vivatravel.rs/wp-content/uploads/16_10-4-128.jpg",
  },
  {
    title: "Maldivi",
    desc: "Bungalovi nad morem, korali i jutra bez budilnika.",
    season: "dec–apr",
    vibe: "luksuz",
    image:
      "https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcQ4YRbiGzT4cm2z657IJPT7IN2TeZgL45XIHqvFOsWNudo4xxpForCwKbHY2VReKxVsoMHZREVI4ZGhXESQe7xV9ywdROE1tJlauQmdaw",
  },
  {
    title: "Antalija, Turska",
    desc: "All-inclusive raj sa dugim plažama i toplim morem.",
    season: "maj–okt",
    vibe: "porodično",
    image:
      "https://encrypted-tbn1.gstatic.com/licensed-image?q=tbn:ANd9GcQi_YjyQiiJh7ZeQZ05DFIVv-l3J6WX-UC5CIDjqAUruW54fHtFIqeznNd6WZ9wPepNtpNvppKV1x7csDC1hjwd-fVMc33TSU9s6A7rsA",
  },
  {
    title: "Hurgada, Egipat",
    desc: "Crveno more, ronjenje i večeri pod zvezdama.",
    season: "sept–maj",
    vibe: "avantura",
    image:
      "https://encrypted-tbn1.gstatic.com/licensed-image?q=tbn:ANd9GcSmQdPxdMAeUbCa2vl63VAf04qvSgiLjw6Ug2H8IQeW99mH43toCluDnjdhb1Rwe603no_gk-oUubVfnPTg6WnmQzrWUkW5uEEcoSHw6g",
  },
];

const INSPIRATION = [
  {
    title: "Kako spakovati lagano za 10 dana",
    snippet: "Kapsula garderoba, trikovi za kozmetiku i pametna obuća.",
    image:
      "https://images.unsplash.com/photo-1531168556467-80aace0d0144?q=80&w=1600&auto=format&fit=crop",
  },
  {
    title: "Top 5 skrivenih plaža Jadrana",
    snippet: "Kamenite uvale, bistra voda i mir čak i u sezoni.",
    image:
      "https://images.unsplash.com/photo-1500375592092-40eb2168fd21?q=80&w=1600&auto=format&fit=crop",
  },
  {
    title: "Mali vodič kroz all-inclusive",
    snippet: "Šta je zaista uključeno, kako birati i na šta obratiti pažnju.",
    image:
       "https://images.unsplash.com/photo-1500375592092-40eb2168fd21?q=80&w=1600&auto=format&fit=crop",
  },
];

const TESTIMONIALS = [
  { name: "Marija", text: "Uspomene vredne svake sekunde — bez stresa!" },
  { name: "Nikola", text: "Organizacija besprekorna, hotel bolji nego na slikama." },
  { name: "Ana", text: "Najlepši zalazak na Santoriniju — hvala vam!" },
  { name: "Vlada", text: "Saveti za izlete su bili pun pogodak." },
];
