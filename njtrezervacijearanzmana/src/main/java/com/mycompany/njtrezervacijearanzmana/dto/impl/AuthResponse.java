package com.mycompany.njtrezervacijearanzmana.dto.impl;

public class AuthResponse {
    public String token;
    public long expiresAtEpochSec;
    public ZaposleniDto user;

    public AuthResponse(String token, long exp, ZaposleniDto user) {
        this.token = token; this.expiresAtEpochSec = exp; this.user = user;
    }
}
