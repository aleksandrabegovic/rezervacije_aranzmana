package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AranzmanDto implements MyDto {
    private Long id;

    @NotBlank @Size(max = 160)
    private String naziv;

    @NotBlank @Size(max = 160)
    private String destinacija;

    private String opis; // TEXT

    @NotNull
    private Long tipId;

    public AranzmanDto() {}
    public AranzmanDto(Long id, String naziv, String destinacija, String opis, Long tipId) {
        this.id = id; this.naziv = naziv; this.destinacija = destinacija; this.opis = opis; this.tipId = tipId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public String getDestinacija() { return destinacija; }
    public void setDestinacija(String destinacija) { this.destinacija = destinacija; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public Long getTipId() { return tipId; }
    public void setTipId(Long tipId) { this.tipId = tipId; }
}
