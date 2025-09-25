package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.RezervacijaDto;
import com.mycompany.njtrezervacijearanzmana.servis.RezervacijaServis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/rezervacija")
public class RezervacijaController {

    private final RezervacijaServis servis;

    public RezervacijaController(RezervacijaServis servis) { this.servis = servis; }

    @GetMapping
    @Operation(summary = "Retrieve all Rezervacija entities.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = RezervacijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<RezervacijaDto>> getAll() {
        return new ResponseEntity<>(servis.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RezervacijaDto> getById(@PathVariable("id") Long id) {
        try { return new ResponseEntity<>(servis.findById(id), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RezervacijaController: " + ex.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create a new Rezervacija.")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = RezervacijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<RezervacijaDto> create(@Valid @RequestBody @NotNull RezervacijaDto dto) {
        try {
            RezervacijaDto saved = servis.create(dto);
            return ResponseEntity.created(URI.create("/api/rezervacija/" + saved.getId())).body(saved);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri čuvanju rezervacije: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Rezervacija.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = RezervacijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<RezervacijaDto> update(@PathVariable Long id, @Valid @RequestBody RezervacijaDto dto) {
        try { dto.setId(id); return new ResponseEntity<>(servis.update(dto), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri izmeni rezervacije: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try { servis.deleteById(id); return new ResponseEntity<>("Rezervacija obrisana.", HttpStatus.OK); }
        catch (Exception ex) { return new ResponseEntity<>("Rezervacija ne postoji: " + id, HttpStatus.NOT_FOUND); }
    }
}
