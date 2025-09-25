package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PutnikDto implements MyDto {
    private Long id;

    @NotBlank @Size(max=80)
    private String ime;

    @NotBlank @Size(max=80)
    private String prezime;

    @NotBlank @Email @Size(max=160)
    private String email;

    @Size(max=32)
    private String telefon;

    public PutnikDto() {}
    public PutnikDto(Long id, String ime, String prezime, String email, String telefon) {
        this.id=id; this.ime=ime; this.prezime=prezime; this.email=email; this.telefon=telefon;
    }

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
}
