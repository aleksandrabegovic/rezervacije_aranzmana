package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.PutnikDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Putnik;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.PutnikMapper;
import com.mycompany.njtrezervacijearanzmana.repository.impl.PutnikRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PutnikServis {

    private final PutnikRepository repo;
    private final PutnikMapper mapper;

    public PutnikServis(PutnikRepository repo, PutnikMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<PutnikDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public PutnikDto findById(Long id) throws Exception {
        return mapper.toDto(repo.findById(id));
    }

    public PutnikDto create(PutnikDto dto) {
        if (dto.getEmail()!=null && repo.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new RuntimeException("Putnik sa tim email-om već postoji: " + dto.getEmail());
        }
        Putnik p = mapper.toEntity(dto);
        repo.save(p);
        return mapper.toDto(p);
    }

    public PutnikDto update(PutnikDto dto) {
        Putnik p = mapper.toEntity(dto);
        repo.save(p);
        return mapper.toDto(p);
    }

    public void deleteById(Long id) { repo.deleteById(id); }
}
