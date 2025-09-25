package com.mycompany.njtrezervacijearanzmana.mapper.impl;

import com.mycompany.njtrezervacijearanzmana.dto.impl.RezervacijaDto;
import com.mycompany.njtrezervacijearanzmana.dto.impl.StavkaRezervacijeDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Aranzman;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Rezervacija;
import com.mycompany.njtrezervacijearanzmana.entity.impl.StavkaRezervacije;
import com.mycompany.njtrezervacijearanzmana.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RezervacijaMapper implements DtoEntityMapper<RezervacijaDto, Rezervacija> {

    @Override
    public RezervacijaDto toDto(Rezervacija e) {
        if (e == null) return null;

        List<StavkaRezervacijeDto> stavkeDto = e.getStavke() == null ? List.of()
            : e.getStavke().stream().map(s -> new StavkaRezervacijeDto(
                s.getId(), s.getOpis(), s.getKolicina(), s.getCena(), s.getPopustProcenat(), s.getIznos()
            )).collect(Collectors.toList());

        return new RezervacijaDto(
            e.getId(),
            e.getDatumKreiranja(),
            e.getNapomena(),
            e.getUkupno(),
            e.getAranzman() != null ? e.getAranzman().getId() : null,
            stavkeDto
        );
    }

    @Override
    public Rezervacija toEntity(RezervacijaDto t) {
        if (t == null) return null;
        Rezervacija r = new Rezervacija();
        r.setId(t.getId());
        r.setDatumKreiranja(t.getDatumKreiranja()); // može ostati null -> setovaće se default u entitetu
        r.setNapomena(t.getNapomena());
        r.setUkupno(t.getUkupno()); // servis će svakako prepisati preračunatim totalom

        if (t.getAranzmanId() != null) r.setAranzman(new Aranzman(t.getAranzmanId()));

        // map stavki
        List<StavkaRezervacije> items = new ArrayList<>();
        if (t.getStavke() != null) {
            for (StavkaRezervacijeDto sd : t.getStavke()) {
                StavkaRezervacije s = new StavkaRezervacije();
                s.setId(sd.getId());
                s.setOpis(sd.getOpis());
                s.setKolicina(sd.getKolicina());
                s.setCena(sd.getCena());
                s.setPopustProcenat(sd.getPopustProcenat());
                s.setRezervacija(r); // set back-ref
                items.add(s);
            }
        }
        r.setStavke(items);
        return r;
    }
}
