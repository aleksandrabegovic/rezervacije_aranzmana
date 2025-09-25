/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.dto.impl;
 
import com.mycompany.njtrezervacijearanzmana.dto.MyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipAranzmanaDto implements MyDto {
    private Long id;

    @NotBlank(message = "naziv je obavezan")
    @Size(max = 120, message = "naziv max 120 karaktera")
    private String naziv;

    public TipAranzmanaDto() {}
    public TipAranzmanaDto(Long id, String naziv) { this.id = id; this.naziv = naziv; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
}
