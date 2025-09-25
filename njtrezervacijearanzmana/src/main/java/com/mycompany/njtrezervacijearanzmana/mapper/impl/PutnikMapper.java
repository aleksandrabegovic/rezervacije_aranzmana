package com.mycompany.njtrezervacijearanzmana.mapper.impl;

import com.mycompany.njtrezervacijearanzmana.dto.impl.PutnikDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Putnik;
import com.mycompany.njtrezervacijearanzmana.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class PutnikMapper implements DtoEntityMapper<PutnikDto, Putnik> {

    @Override
    public PutnikDto toDto(Putnik e) {
        if (e == null) return null;
        return new PutnikDto(e.getId(), e.getIme(), e.getPrezime(), e.getEmail(), e.getTelefon());
    }

    @Override
    public Putnik toEntity(PutnikDto t) {
        if (t == null) return null;
        Putnik p = new Putnik();
        p.setId(t.getId());
        p.setIme(t.getIme());
        p.setPrezime(t.getPrezime());
        p.setEmail(t.getEmail());
        p.setTelefon(t.getTelefon());
        return p;
    }
}
