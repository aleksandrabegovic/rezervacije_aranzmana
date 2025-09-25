package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aranzman",
       uniqueConstraints = @UniqueConstraint(name="uk_aranzman_naziv_dest", columnNames = {"naziv","destinacija"}))
public class Aranzman {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String naziv;

    @Column(nullable = false, length = 160)
    private String destinacija;

    @Column(columnDefinition = "TEXT")
    private String opis;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_id", nullable = false)
    private TipAranzmana tip;

    
    @OneToMany(mappedBy = "aranzman", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Rezervacija> rezervacije = new ArrayList<>();

    public Aranzman() {}
    public Aranzman(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public String getDestinacija() { return destinacija; }
    public void setDestinacija(String destinacija) { this.destinacija = destinacija; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public TipAranzmana getTip() { return tip; }
    public void setTip(TipAranzmana tip) { this.tip = tip; }
    public List<Rezervacija> getRezervacije() { return rezervacije; }
    public void setRezervacije(List<Rezervacija> rezervacije) { this.rezervacije = rezervacije; }
}
