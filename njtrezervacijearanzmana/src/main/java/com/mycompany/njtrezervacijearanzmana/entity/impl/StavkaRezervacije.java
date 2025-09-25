package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stavka_rezervacije")
public class StavkaRezervacije {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rezervacija_id", nullable = false)
    private Rezervacija rezervacija;

    @Column(length = 200)
    private String opis; // npr. usluga, dodatak, karta itd.

    @Column(nullable = false)
    private int kolicina = 1;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cena = BigDecimal.ZERO;  // jedinična cena

    // popust u %, npr 0, 5, 10... (0-100)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal popustProcenat = BigDecimal.ZERO;

    public StavkaRezervacije() {}
    public StavkaRezervacije(Long id) { this.id = id; }

    public BigDecimal getIznos() {
        // iznos = kolicina * cena * (1 - popust/100)
        BigDecimal qty = new BigDecimal(kolicina);
        BigDecimal bruto = cena.multiply(qty);
        BigDecimal faktor = BigDecimal.ONE.subtract(popustProcenat.divide(new BigDecimal("100")));
        return bruto.multiply(faktor).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Rezervacija getRezervacija() { return rezervacija; }
    public void setRezervacija(Rezervacija rezervacija) { this.rezervacija = rezervacija; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public int getKolicina() { return kolicina; }
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }
    public BigDecimal getCena() { return cena; }
    public void setCena(BigDecimal cena) { this.cena = cena; }
    public BigDecimal getPopustProcenat() { return popustProcenat; }
    public void setPopustProcenat(BigDecimal popustProcenat) { this.popustProcenat = popustProcenat; }
}
