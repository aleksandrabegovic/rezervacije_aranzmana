package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.RezervacijaDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Aranzman;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Putnik;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Rezervacija;
import com.mycompany.njtrezervacijearanzmana.entity.impl.StavkaRezervacije;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.RezervacijaMapper;
import com.mycompany.njtrezervacijearanzmana.repository.impl.RezervacijaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RezervacijaServis {

    private final RezervacijaRepository repo;
    private final RezervacijaMapper mapper;

    @PersistenceContext
    private EntityManager em;

    public RezervacijaServis(RezervacijaRepository repo, RezervacijaMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<RezervacijaDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RezervacijaDto findById(Long id) throws Exception {
        return mapper.toDto(repo.findById(id));
    }

    public RezervacijaDto create(RezervacijaDto dto) {
        Rezervacija r = mapper.toEntity(dto);

        // reference bez SELECT-a
        if (dto.getAranzmanId() != null) {
            r.setAranzman(em.getReference(Aranzman.class, dto.getAranzmanId()));
        }

        // poveži back-ref (ako nije već) i izračunaj total
        BigDecimal total = BigDecimal.ZERO;
        if (r.getStavke() != null) {
            for (StavkaRezervacije s : r.getStavke()) {
                s.setRezervacija(r);
                if (s.getPutnik() != null && s.getPutnik().getId() != null) {
                    s.setPutnik(em.getReference(Putnik.class, s.getPutnik().getId()));
                } else {
                    throw new RuntimeException("putnikId je obavezan za svaku stavku");
                }
                total = total.add(s.getIznos());
            }
        }
        r.setUkupno(total.setScale(2, java.math.RoundingMode.HALF_UP));

        repo.save(r);
        return mapper.toDto(r);
    }

    public RezervacijaDto update(RezervacijaDto dto) {
        Rezervacija r = mapper.toEntity(dto);

        if (dto.getAranzmanId() != null) {
            r.setAranzman(em.getReference(Aranzman.class, dto.getAranzmanId()));
        }

        BigDecimal total = BigDecimal.ZERO;
        if (r.getStavke() != null) {
            for (StavkaRezervacije s : r.getStavke()) {
                s.setRezervacija(r);
                if (s.getPutnik() != null && s.getPutnik().getId() != null) {
                        s.setPutnik(em.getReference(Putnik.class, s.getPutnik().getId()));
                    } else {
                        throw new RuntimeException("putnikId je obavezan za svaku stavku");
                    }
                total = total.add(s.getIznos());
            }
        }
        r.setUkupno(total.setScale(2, java.math.RoundingMode.HALF_UP));

        repo.save(r);
        return mapper.toDto(r);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
