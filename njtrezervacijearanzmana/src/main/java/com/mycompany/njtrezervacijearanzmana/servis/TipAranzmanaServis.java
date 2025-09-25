/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.TipAranzmanaDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.TipAranzmana;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.TipAranzmanaMapper;
import com.mycompany.njtrezervacijearanzmana.repository.impl.TipAranzmanaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TipAranzmanaServis {

    private final TipAranzmanaRepository repo;
    private final TipAranzmanaMapper mapper;

    public TipAranzmanaServis(TipAranzmanaRepository repo, TipAranzmanaMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<TipAranzmanaDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public TipAranzmanaDto findById(Long id) throws Exception {
        return mapper.toDto(repo.findById(id));
    }

    public TipAranzmanaDto create(TipAranzmanaDto dto) {
        if (dto.getNaziv() != null && repo.existsByNazivIgnoreCase(dto.getNaziv())) {
            throw new RuntimeException("Tip aranžmana već postoji: " + dto.getNaziv());
        }
        TipAranzmana e = mapper.toEntity(dto);
        repo.save(e);
        return mapper.toDto(e);
    }

    public TipAranzmanaDto update(TipAranzmanaDto dto) {
        TipAranzmana e = mapper.toEntity(dto);
        repo.save(e);
        return mapper.toDto(e);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
