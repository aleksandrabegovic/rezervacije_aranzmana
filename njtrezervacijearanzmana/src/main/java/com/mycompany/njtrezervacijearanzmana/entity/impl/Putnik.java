package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "putnik",
    uniqueConstraints = {
        @UniqueConstraint(name="uk_putnik_email", columnNames = "email")
    }
)
public class Putnik {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=80)
    private String ime;

    @Column(nullable=false, length=80)
    private String prezime;

    @Column(nullable=false, length=160)
    private String email;

    @Column(length=32)
    private String telefon;

    // dvosmerno (opciono): kad već imamo Rezervacija
    @OneToMany(mappedBy = "putnik", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Rezervacija> rezervacije = new ArrayList<>();

    public Putnik() {}
    public Putnik(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public List<Rezervacija> getRezervacije() { return rezervacije; }
    public void setRezervacije(List<Rezervacija> rezervacije) { this.rezervacije = rezervacije; }
}
