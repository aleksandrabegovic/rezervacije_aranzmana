/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.entity.impl;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tip_aranzmana",
       uniqueConstraints = @UniqueConstraint(name = "uk_tip_aranzmana_naziv", columnNames = "naziv"))
public class TipAranzmana {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String naziv;

    // opcionalno dvosmerno ka aranžmanu (kad dodamo Aranzman entitet)
    @OneToMany(mappedBy = "tip", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Aranzman> aranzmani = new ArrayList<>();

    public TipAranzmana() {}
    public TipAranzmana(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public List<Aranzman> getAranzmani() { return aranzmani; }
    public void setAranzmani(List<Aranzman> aranzmani) { this.aranzmani = aranzmani; }
}
