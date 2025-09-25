/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.mapper.impl;

import com.mycompany.njtrezervacijearanzmana.dto.impl.TipAranzmanaDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.TipAranzmana;
import com.mycompany.njtrezervacijearanzmana.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TipAranzmanaMapper implements DtoEntityMapper<TipAranzmanaDto, TipAranzmana> {

    @Override
    public TipAranzmanaDto toDto(TipAranzmana e) {
        if (e == null) return null;
        return new TipAranzmanaDto(e.getId(), e.getNaziv());
    }

    @Override
    public TipAranzmana toEntity(TipAranzmanaDto t) {
        if (t == null) return null;
        TipAranzmana x = new TipAranzmana();
        x.setId(t.getId());
        x.setNaziv(t.getNaziv());
        return x;
    }
}
