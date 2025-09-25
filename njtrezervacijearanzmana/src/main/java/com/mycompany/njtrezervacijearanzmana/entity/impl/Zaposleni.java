package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zaposleni",
       uniqueConstraints = @UniqueConstraint(name="uk_zaposleni_username", columnNames = "korisnicko_ime"))
public class Zaposleni {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=80)
    private String ime;

    @Column(nullable=false, length=80)
    private String prezime;

    @Column(name="korisnicko_ime", nullable=false, length=80)
    private String korisnickoIme;

    @Column(name="lozinka_hash", nullable=false, length=255)
    private String lozinkaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=12)
    private Uloga uloga = Uloga.AGENT;

    @OneToMany(mappedBy = "zaposleni", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Rezervacija> rezervacije = new ArrayList<>();

    public enum Uloga { ADMIN, AGENT }

    public Zaposleni() {}
    public Zaposleni(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getKorisnickoIme() { return korisnickoIme; }
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }
    public String getLozinkaHash() { return lozinkaHash; }
    public void setLozinkaHash(String lozinkaHash) { this.lozinkaHash = lozinkaHash; }
    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }
    public List<Rezervacija> getRezervacije() { return rezervacije; }
    public void setRezervacije(List<Rezervacija> rezervacije) { this.rezervacije = rezervacije; }
}
