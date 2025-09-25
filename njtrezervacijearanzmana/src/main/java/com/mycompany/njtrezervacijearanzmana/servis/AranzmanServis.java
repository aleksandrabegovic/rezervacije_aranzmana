package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.AranzmanDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Aranzman;
import com.mycompany.njtrezervacijearanzmana.entity.impl.TipAranzmana;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.AranzmanMapper;
import com.mycompany.njtrezervacijearanzmana.repository.impl.AranzmanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AranzmanServis {

    private final AranzmanRepository repo;
    private final AranzmanMapper mapper;

    @PersistenceContext
    private EntityManager em;

    public AranzmanServis(AranzmanRepository repo, AranzmanMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<AranzmanDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public AranzmanDto findById(Long id) throws Exception {
        return mapper.toDto(repo.findById(id));
    }

    public AranzmanDto create(AranzmanDto dto) {
        if (dto.getNaziv()!=null && dto.getDestinacija()!=null &&
            repo.existsByNazivAndDestinacijaIgnoreCase(dto.getNaziv(), dto.getDestinacija())) {
            throw new RuntimeException("Aranžman već postoji: " + dto.getNaziv() + " / " + dto.getDestinacija());
        }
        Aranzman a = mapper.toEntity(dto);
        if (dto.getTipId()!=null) a.setTip(em.getReference(TipAranzmana.class, dto.getTipId()));
        repo.save(a);
        return mapper.toDto(a);
    }

    public AranzmanDto update(AranzmanDto dto) {
        Aranzman a = mapper.toEntity(dto);
        if (dto.getTipId()!=null) a.setTip(em.getReference(TipAranzmana.class, dto.getTipId()));
        repo.save(a);
        return mapper.toDto(a);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
