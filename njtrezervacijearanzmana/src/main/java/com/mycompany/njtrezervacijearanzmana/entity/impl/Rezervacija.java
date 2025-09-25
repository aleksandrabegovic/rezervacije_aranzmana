package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
Rezervacija ima stavke (lista) i aranzman (objekat).
Po difoltu su veze LAZY (ne učitavaju se odmah).
Kada uradiš SELECT r FROM Rezervacija r, Hibernate:

učita samo rezervacije,

kad prvi put pristupiš r.getStavke() za svaku rezervaciju → poseban upit za stavke,

kad pristupiš r.getAranzman() → još jedan upit po rezervaciji.



@NamedEntityGraph?

To je spisak veza koje želim da učitam ODMAH uz glavnu tabelu – ali samo kada ja to eksplicitno tražim.

*/


@NamedEntityGraph(
    name = "Rezervacija.stavke.graph",
    attributeNodes = {
        @NamedAttributeNode("stavke"),
        @NamedAttributeNode("aranzman")
    }
)
@Entity
@Table(name = "rezervacija")
public class Rezervacija {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)                       // kada je napravljena
    private LocalDateTime datumKreiranja = LocalDateTime.now();

    @Column(length = 500)
    private String napomena;

    // FIZIČKA KOLONA koja čuva ukupan iznos (računamo u servisu pre save)
    @Column(nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal ukupno = java.math.BigDecimal.ZERO;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aranzman_id", nullable = false)
    private Aranzman aranzman;

    @OneToMany(mappedBy = "rezervacija", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StavkaRezervacije> stavke = new ArrayList<>();

    public Rezervacija() {}
    public Rezervacija(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDatumKreiranja() { return datumKreiranja; }
    public void setDatumKreiranja(LocalDateTime datumKreiranja) { this.datumKreiranja = datumKreiranja; }

    public String getNapomena() { return napomena; }
    public void setNapomena(String napomena) { this.napomena = napomena; }

    public java.math.BigDecimal getUkupno() { return ukupno; }
    public void setUkupno(java.math.BigDecimal ukupno) { this.ukupno = ukupno; }

    public Aranzman getAranzman() { return aranzman; }
    public void setAranzman(Aranzman aranzman) { this.aranzman = aranzman; }

    public List<StavkaRezervacije> getStavke() { return stavke; }
    public void setStavke(List<StavkaRezervacije> stavke) { this.stavke = stavke; }
}
