package org.esangam.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.esangam.entity.Member;

import java.time.Duration;
import java.util.*;

@ApplicationScoped
public class TokenService {

    // Define role hierarchy in a static map
    private static final Map<String, List<String>> ROLE_HIERARCHY = Map.of(
            "ES_ADMIN", List.of("ES_ADMIN", "ADMIN", "MEMBER"),
            "ADMIN", List.of("ADMIN", "MEMBER"),
            "MEMBER", List.of("MEMBER")
    );

    public String generateToken(Member member) {

        // Get groups from hierarchy, default to MEMBER if role is unknown
        List<String> groups = ROLE_HIERARCHY.getOrDefault(member.getRole(), List.of("MEMBER"));

        var builder = Jwt.claims()
                .subject(member.getMobileNumber())
                .upn(member.getMobileNumber())
                .issuer("esangam")
                .expiresIn(Duration.ofHours(8))
                .groups(Set.copyOf(groups))
                .claim("role", member.getRole());

        // Initialize society info if available
        if (member.getSociety() != null) {
            builder.claim("societyId", member.getSociety().getId());
        }

        return builder.sign();
    }
}
