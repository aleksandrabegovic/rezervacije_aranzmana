package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.ZaposleniDto;
import com.mycompany.njtrezervacijearanzmana.servis.ZaposleniServis;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/zaposleni")
@CrossOrigin(origins = "http://localhost:3000")
public class ZaposleniController {

    private final ZaposleniServis servis;
    public ZaposleniController(ZaposleniServis s) { this.servis = s; }

    @GetMapping
    @Operation(summary = "Svi zaposleni")
    public ResponseEntity<List<ZaposleniDto>> all() {
        return ResponseEntity.ok(servis.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZaposleniDto> byId(@PathVariable Long id) {
        try { return ResponseEntity.ok(servis.findById(id)); }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); }
    }

    @PostMapping
    @Operation(summary = "Kreiraj zaposlenog (admin) – lozinka kao query param privremeno")
    public ResponseEntity<ZaposleniDto> create(@Valid @RequestBody @NotNull ZaposleniDto dto,
                                               @RequestParam("pwd") String plainPassword) {
        ZaposleniDto saved = servis.create(dto, plainPassword);
        return ResponseEntity.created(URI.create("/api/zaposleni/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZaposleniDto> update(@PathVariable Long id, @Valid @RequestBody ZaposleniDto dto) {
        try { dto.setId(id); return ResponseEntity.ok(servis.update(dto)); }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        servis.deleteById(id);
        return ResponseEntity.ok("Zaposleni obrisan.");
    }
}
