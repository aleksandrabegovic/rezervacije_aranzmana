package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni.Uloga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank @Size(max=80) public String ime;
    @NotBlank @Size(max=80) public String prezime;
    @NotBlank @Size(max=80) public String korisnickoIme;
    @NotBlank @Size(min=6, max=120) public String lozinka;
    public Uloga uloga = Uloga.AGENT;
}
