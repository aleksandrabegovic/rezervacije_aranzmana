package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni.Uloga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ZaposleniDto implements MyDto {
    private Long id;

    @NotBlank @Size(max=80)  private String ime;
    @NotBlank @Size(max=80)  private String prezime;
    @NotBlank @Size(max=80)  private String korisnickoIme;
    private Uloga uloga;

    public ZaposleniDto() {}
    public ZaposleniDto(Long id, String ime, String prezime, String korisnickoIme, Uloga uloga) {
        this.id=id; this.ime=ime; this.prezime=prezime; this.korisnickoIme=korisnickoIme; this.uloga=uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

     
}

