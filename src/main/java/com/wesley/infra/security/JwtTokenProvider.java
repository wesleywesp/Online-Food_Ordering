package com.wesley.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Service
public class JwtTokenProvider {
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication autentication) {
        Collection<? extends GrantedAuthority> authorities = autentication.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME))
                .claim("email", autentication.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        return jwt;

    }

    public String getEmailFromJwtToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private String populateAuthorities(Collection <? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}
