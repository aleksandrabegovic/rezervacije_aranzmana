package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.PutnikDto;
import com.mycompany.njtrezervacijearanzmana.servis.PutnikServis;
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
@RequestMapping("/api/putnik")
public class PutnikController {

    private final PutnikServis servis;
    public PutnikController(PutnikServis servis) { this.servis = servis; }

    @GetMapping
    @Operation(summary="Retrieve all Putnik entities.")
    @ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=PutnikDto.class), mediaType="application/json"))
    public ResponseEntity<List<PutnikDto>> getAll() {
        return new ResponseEntity<>(servis.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PutnikDto> getById(@PathVariable("id") Long id) {
        try { return new ResponseEntity<>(servis.findById(id), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PutnikController: " + ex.getMessage());
        }
    }

    @PostMapping
    @Operation(summary="Create a new Putnik.")
    @ApiResponse(responseCode="201", content=@Content(schema=@Schema(implementation=PutnikDto.class), mediaType="application/json"))
    public ResponseEntity<PutnikDto> create(@Valid @RequestBody @NotNull PutnikDto dto) {
        try {
            PutnikDto saved = servis.create(dto);
            return ResponseEntity.created(URI.create("/api/putnik/" + saved.getId())).body(saved);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri čuvanju putnika: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary="Update an existing Putnik.")
    @ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=PutnikDto.class), mediaType="application/json"))
    public ResponseEntity<PutnikDto> update(@PathVariable Long id, @Valid @RequestBody PutnikDto dto) {
        try { dto.setId(id); return new ResponseEntity<>(servis.update(dto), HttpStatus.OK); }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri izmeni putnika: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try { servis.deleteById(id); return new ResponseEntity<>("Putnik obrisan.", HttpStatus.OK); }
        catch (Exception ex) { return new ResponseEntity<>("Putnik ne postoji: " + id, HttpStatus.NOT_FOUND); }
    }
}
