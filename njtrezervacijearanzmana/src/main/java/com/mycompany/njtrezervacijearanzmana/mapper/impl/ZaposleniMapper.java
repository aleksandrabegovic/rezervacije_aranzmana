package com.mycompany.njtrezervacijearanzmana.mapper.impl;

import com.mycompany.njtrezervacijearanzmana.dto.impl.ZaposleniDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni;
import com.mycompany.njtrezervacijearanzmana.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ZaposleniMapper implements DtoEntityMapper<ZaposleniDto, Zaposleni> {

    @Override
    public ZaposleniDto toDto(Zaposleni e) {
        if (e==null) return null;
        return new ZaposleniDto(e.getId(), e.getIme(), e.getPrezime(), e.getKorisnickoIme(), e.getUloga());
    }

    @Override
    public Zaposleni toEntity(ZaposleniDto t) {
        if (t==null) return null;
        Zaposleni z = new Zaposleni();
        z.setId(t.getId());
        z.setIme(t.getIme());
        z.setPrezime(t.getPrezime());
        z.setKorisnickoIme(t.getKorisnickoIme());
        z.setUloga(t.getUloga());
        return z;
    }
}
