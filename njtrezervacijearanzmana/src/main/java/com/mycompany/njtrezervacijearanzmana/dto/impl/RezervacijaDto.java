package com.mycompany.njtrezervacijearanzmana.dto.impl;

import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RezervacijaDto implements MyDto {
    private Long id;

    private LocalDateTime datumKreiranja;     // read-only (postavlja se server-side)

    @Size(max = 500)
    private String napomena;

    // read-only, puni servis
    private BigDecimal ukupno;

    @NotNull(message = "aranzmanId je obavezan")
    private Long aranzmanId;

    @Valid
    private List<StavkaRezervacijeDto> stavke;

    public RezervacijaDto() {}

    public RezervacijaDto(Long id, LocalDateTime datumKreiranja, String napomena,
                          BigDecimal ukupno, Long aranzmanId, List<StavkaRezervacijeDto> stavke) {
        this.id = id; this.datumKreiranja = datumKreiranja; this.napomena = napomena;
        this.ukupno = ukupno; this.aranzmanId = aranzmanId; this.stavke = stavke;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDatumKreiranja() { return datumKreiranja; }
    public void setDatumKreiranja(LocalDateTime datumKreiranja) { this.datumKreiranja = datumKreiranja; }
    public String getNapomena() { return napomena; }
    public void setNapomena(String napomena) { this.napomena = napomena; }
    public BigDecimal getUkupno() { return ukupno; }
    public void setUkupno(BigDecimal ukupno) { this.ukupno = ukupno; }
    public Long getAranzmanId() { return aranzmanId; }
    public void setAranzmanId(Long aranzmanId) { this.aranzmanId = aranzmanId; }
    public List<StavkaRezervacijeDto> getStavke() { return stavke; }
    public void setStavke(List<StavkaRezervacijeDto> stavke) { this.stavke = stavke; }
}
