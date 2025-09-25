/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.dto.impl;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank public String korisnickoIme;
    @NotBlank public String lozinka;
}
