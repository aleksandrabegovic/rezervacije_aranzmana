package com.mycompany.njtrezervacijearanzmana.servis;

import com.mycompany.njtrezervacijearanzmana.dto.impl.AuthResponse;
import com.mycompany.njtrezervacijearanzmana.dto.impl.LoginRequest;
import com.mycompany.njtrezervacijearanzmana.dto.impl.RegisterRequest;
import com.mycompany.njtrezervacijearanzmana.dto.impl.ZaposleniDto;
import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni;
import com.mycompany.njtrezervacijearanzmana.mapper.impl.ZaposleniMapper;
import com.mycompany.njtrezervacijearanzmana.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthServis {

    private final ZaposleniServis zaposleniServis;
    private final ZaposleniMapper mapper;
    private final JwtUtil jwt;

    public AuthServis(ZaposleniServis zServis, ZaposleniMapper mapper, JwtUtil jwt) {
        this.zaposleniServis = zServis; this.mapper = mapper; this.jwt = jwt;
    }

    public ZaposleniDto register(RegisterRequest req) {
        return zaposleniServis.register(req);
    }

    public AuthResponse login(LoginRequest req) {
        Zaposleni z = zaposleniServis.loadByUsername(req.korisnickoIme);
        if (!zaposleniServis.checkPassword(z, req.lozinka))
            throw new RuntimeException("Pogrešno korisničko ime ili lozinka");
        String token = jwt.generate(z.getKorisnickoIme());
        long exp = jwt.getExpiration(token).toInstant().getEpochSecond();
        return new AuthResponse(token, exp, mapper.toDto(z));
    }
}
