package com.mycompany.njtrezervacijearanzmana.mapper.impl;

import com.mycompany.njtrezervacijearanzmana.dto.impl.AranzmanDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Aranzman;
import com.mycompany.njtrezervacijearanzmana.entity.impl.TipAranzmana;
import com.mycompany.njtrezervacijearanzmana.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class AranzmanMapper implements DtoEntityMapper<AranzmanDto, Aranzman> {

    @Override
    public AranzmanDto toDto(Aranzman e) {
        if (e == null) return null;
        return new AranzmanDto(
            e.getId(),
            e.getNaziv(),
            e.getDestinacija(),
            e.getOpis(),
            e.getTip() != null ? e.getTip().getId() : null
        );
    }

    @Override
    public Aranzman toEntity(AranzmanDto t) {
        if (t == null) return null;
        Aranzman a = new Aranzman();
        a.setId(t.getId());
        a.setNaziv(t.getNaziv());
        a.setDestinacija(t.getDestinacija());
        a.setOpis(t.getOpis());
        if (t.getTipId() != null) a.setTip(new TipAranzmana(t.getTipId()));
        return a;
    }
}
