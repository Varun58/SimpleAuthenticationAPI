package com.example.abillion.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JwtTokenUtil {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "mySecretKey";

    public static boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    public static Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    public static String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("abillionJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        return "Bearer " + token;
    }
}
