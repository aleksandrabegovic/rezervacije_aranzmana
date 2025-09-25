/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.controller;

import com.mycompany.njtrezervacijearanzmana.dto.impl.TipAranzmanaDto;
import com.mycompany.njtrezervacijearanzmana.servis.TipAranzmanaServis;
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
@RequestMapping("/api/tip-aranzmana")
public class TipAranzmanaController {

    private final TipAranzmanaServis servis;

    public TipAranzmanaController(TipAranzmanaServis servis) {
        this.servis = servis;
    }

    @GetMapping
    @Operation(summary = "Retrieve all TipAranzmana entities.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TipAranzmanaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<TipAranzmanaDto>> getAll() {
        return new ResponseEntity<>(servis.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipAranzmanaDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(servis.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TipAranzmanaController: " + ex.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create a new TipAranzmana entity.")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = TipAranzmanaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TipAranzmanaDto> create(@Valid @RequestBody @NotNull TipAranzmanaDto dto) {
        try {
            TipAranzmanaDto saved = servis.create(dto);
            return ResponseEntity.created(URI.create("/api/tip-aranzmana/" + saved.getId())).body(saved);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri čuvanju tipa aranžmana: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing TipAranzmana entity.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TipAranzmanaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TipAranzmanaDto> update(@PathVariable Long id, @Valid @RequestBody TipAranzmanaDto dto) {
        try {
            dto.setId(id);
            return new ResponseEntity<>(servis.update(dto), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greška pri izmeni tipa aranžmana: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            servis.deleteById(id);
            return new ResponseEntity<>("Tip aranžmana obrisan.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Tip aranžmana ne postoji: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
