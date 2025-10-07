package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.RezervacijaDto;
import com.mycompany.njtrezervacijearanzmana.dto.impl.StavkaRezervacijeDto;
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

        // 1) Reference bez SELECT-a
        if (dto.getAranzmanId() != null) {
            r.setAranzman(em.getReference(Aranzman.class, dto.getAranzmanId()));
        }

        // 2) Ako rezervacija nema putnika, preuzmi iz prve stavke
        Long headerPutnikId = extractHeaderPutnikIdFromItems(dto);
        if (r.getPutnik() == null && headerPutnikId != null) {
            r.setPutnik(em.getReference(Putnik.class, headerPutnikId));
        }
        if (isRezervacijaPutnikRequiredInDb() && (r.getPutnik() == null || r.getPutnik().getId() == null)) {
            throw new RuntimeException("putnikId (u rezervaciji ili prvoj stavci) je obavezan.");
        }

        // 3) Poveži stavke, validiraj putnike i izračunaj total preko GETTER-a (nema setIznos!)
        BigDecimal total = BigDecimal.ZERO;
        if (r.getStavke() != null) {
            for (StavkaRezervacije s : r.getStavke()) {
                s.setRezervacija(r);

                Long sid = (s.getPutnik() != null) ? s.getPutnik().getId() : null;
                if (sid == null) {
                    throw new RuntimeException("putnikId je obavezan za svaku stavku.");
                }
                s.setPutnik(em.getReference(Putnik.class, sid));

                // iznos se računa u entitetu: s.getIznos()
                BigDecimal iznos = s.getIznos();
                total = total.add(iznos != null ? iznos : BigDecimal.ZERO);
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

        Long headerPutnikId = extractHeaderPutnikIdFromItems(dto);
        if (r.getPutnik() == null && headerPutnikId != null) {
            r.setPutnik(em.getReference(Putnik.class, headerPutnikId));
        }
        if (isRezervacijaPutnikRequiredInDb() && (r.getPutnik() == null || r.getPutnik().getId() == null)) {
            throw new RuntimeException("putnikId (u rezervaciji ili prvoj stavci) je obavezan.");
        }

        BigDecimal total = BigDecimal.ZERO;
        if (r.getStavke() != null) {
            for (StavkaRezervacije s : r.getStavke()) {
                s.setRezervacija(r);

                Long sid = (s.getPutnik() != null) ? s.getPutnik().getId() : null;
                if (sid == null) {
                    throw new RuntimeException("putnikId je obavezan za svaku stavku.");
                }
                s.setPutnik(em.getReference(Putnik.class, sid));

                BigDecimal iznos = s.getIznos(); // nema setovanja
                total = total.add(iznos != null ? iznos : BigDecimal.ZERO);
            }
        }
        r.setUkupno(total.setScale(2, java.math.RoundingMode.HALF_UP));

        repo.save(r);
        return mapper.toDto(r);
    }

    public void deleteById(Long id) { repo.deleteById(id); }

    // ===== Helpers =====

    private Long extractHeaderPutnikIdFromItems(RezervacijaDto dto) {
        if (dto == null || dto.getStavke() == null || dto.getStavke().isEmpty()) return null;
        StavkaRezervacijeDto first = dto.getStavke().get(0);
        return first != null ? first.getPutnikId() : null;
    }

    private boolean isRezervacijaPutnikRequiredInDb() {
        // Ako je kolona NOT NULL, neka bude true
        return true;
    }
}
