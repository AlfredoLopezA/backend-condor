package com.condor.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
        "condor-rfid-operational-security-key-2026-super-secure";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;
    private final SecretKey key =
        Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String generateToken(
        Long deviceId,
        Short plantId,
        Short roleDeviceId,
        String hostname,
        String osName
    ) {
    return Jwts.builder()
        .claim("deviceId", deviceId)
        .claim("plantId", plantId)
        .claim("roleDeviceId", roleDeviceId)
        .claim("hostname", hostname)
        .claim("osName", osName)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key)
        .compact();
    }
    public long getExpirationTimeSeconds() {
        return EXPIRATION_TIME / 1000;
    }
}