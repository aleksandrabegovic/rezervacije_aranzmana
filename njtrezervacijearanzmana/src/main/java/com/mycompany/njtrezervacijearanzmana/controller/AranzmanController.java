package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.AranzmanDto;
import com.mycompany.njtrezervacijearanzmana.servis.AranzmanServis;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/aranzman")
public class AranzmanController {

    private final AranzmanServis servis;
    public AranzmanController(AranzmanServis servis) { this.servis = servis; }

    @GetMapping
    @Operation(summary="Retrieve all Aranzman entities.")
    @ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=AranzmanDto.class), mediaType="application/json"))
    public ResponseEntity<List<AranzmanDto>> getAll() {
        return new ResponseEntity<>(servis.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AranzmanDto> getById(@PathVariable("id") Long id) {
        try { return new ResponseEntity<>(servis.findById(id), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AranzmanController: " + ex.getMessage());
        }
    }

    @PostMapping
    @Operation(summary="Create a new Aranzman.")
    @ApiResponse(responseCode="201", content=@Content(schema=@Schema(implementation=AranzmanDto.class), mediaType="application/json"))
    public ResponseEntity<AranzmanDto> create(@Valid @RequestBody @NotNull AranzmanDto dto) {
        try {
            AranzmanDto saved = servis.create(dto);
            return ResponseEntity.created(URI.create("/api/aranzman/" + saved.getId())).body(saved);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri čuvanju aranžmana: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary="Update an existing Aranzman.")
    @ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=AranzmanDto.class), mediaType="application/json"))
    public ResponseEntity<AranzmanDto> update(@PathVariable Long id, @Valid @RequestBody AranzmanDto dto) {
        try { dto.setId(id); return new ResponseEntity<>(servis.update(dto), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri izmeni aranžmana: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try { servis.deleteById(id); return new ResponseEntity<>("Aranžman obrisan.", HttpStatus.OK); }
        catch (Exception ex) { return new ResponseEntity<>("Aranžman ne postoji: " + id, HttpStatus.NOT_FOUND); }
    }
}
