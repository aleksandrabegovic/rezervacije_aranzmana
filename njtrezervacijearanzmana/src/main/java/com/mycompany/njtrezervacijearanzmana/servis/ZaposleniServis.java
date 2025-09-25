package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.ZaposleniDto;
import com.mycompany.njtrezervacijearanzmana.dto.impl.RegisterRequest;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.ZaposleniMapper;
import com.mycompany.njtrezervacijearanzmana.repository.impl.ZaposleniRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ZaposleniServis {

    private final ZaposleniRepository repo;
    private final ZaposleniMapper mapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ZaposleniServis(ZaposleniRepository repo, ZaposleniMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<ZaposleniDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ZaposleniDto findById(Long id) throws Exception {
        return mapper.toDto(repo.findById(id));
    }

    public ZaposleniDto create(ZaposleniDto dto, String plainPassword) {
        if (repo.existsByKorisnickoImeIgnoreCase(dto.getKorisnickoIme()))
            throw new RuntimeException("Korisničko ime već postoji: " + dto.getKorisnickoIme());
        Zaposleni z = mapper.toEntity(dto);
        z.setLozinkaHash(encoder.encode(plainPassword));
        repo.save(z);
        return mapper.toDto(z);
    }

    public ZaposleniDto update(ZaposleniDto dto) {
        Zaposleni z = mapper.toEntity(dto);
        // lozinka se ne menja ovde
        Zaposleni stari = repo.findByKorisnickoIme(dto.getKorisnickoIme());
        if (stari != null) z.setLozinkaHash(stari.getLozinkaHash());
        repo.save(z);
        return mapper.toDto(z);
    }

    public void deleteById(Long id) { repo.deleteById(id); }

    // registracija “sve u jednom”
    public ZaposleniDto register(RegisterRequest req) {
        ZaposleniDto dto = new ZaposleniDto(null, req.ime, req.prezime, req.korisnickoIme, req.uloga);
        return create(dto, req.lozinka);
    }

    public Zaposleni loadByUsername(String username) {
        Zaposleni z = repo.findByKorisnickoIme(username);
        if (z == null) throw new RuntimeException("Nepostojeće korisničko ime");
        return z;
    }

    public boolean checkPassword(Zaposleni z, String raw) {
        return encoder.matches(raw, z.getLozinkaHash());
    }
}
