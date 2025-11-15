package org.esangam.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Member;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String generateToken(Member member) {
        return Jwt.issuer("esangam-auth")
                .subject(member.getMobileNumber())
                .upn(member.getMobileNumber())
                .claim("name", member.getFullName())
                .groups(Set.of(member.getRole()))
                .expiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign();
    }
}
