package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class StavkaRezervacijeDto implements MyDto {
    private Long id;

    @Size(max = 200)
    private String opis;

    @Min(value = 1, message = "kolicina >= 1")
    private int kolicina = 1;

    @NotNull
    private BigDecimal cena;

    // 0–100
    @NotNull
    private BigDecimal popustProcenat = BigDecimal.ZERO;

    // read-only
    private BigDecimal iznos;

    public StavkaRezervacijeDto() {}

    public StavkaRezervacijeDto(Long id, String opis, int kolicina, BigDecimal cena,
                                BigDecimal popustProcenat, BigDecimal iznos) {
        this.id = id; this.opis = opis; this.kolicina = kolicina; this.cena = cena;
        this.popustProcenat = popustProcenat; this.iznos = iznos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public int getKolicina() { return kolicina; }
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }
    public BigDecimal getCena() { return cena; }
    public void setCena(BigDecimal cena) { this.cena = cena; }
    public BigDecimal getPopustProcenat() { return popustProcenat; }
    public void setPopustProcenat(BigDecimal popustProcenat) { this.popustProcenat = popustProcenat; }
    public BigDecimal getIznos() { return iznos; }
    public void setIznos(BigDecimal iznos) { this.iznos = iznos; }
}
