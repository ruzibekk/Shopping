package com.example.util;



import com.example.dto.JwtDTO;
import com.example.enums.RoleEnum;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;


import javax.management.relation.Role;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24 * 7; // 7-day
    private static final String secretKey = "dean&sdfd534hguz-mazgi";


    public static String encode(String id, String phone, RoleEnum role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("id", id);
        jwtBuilder.claim("phone", phone);
        jwtBuilder.claim("role", role);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Java backend");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        String id = (String) claims.get("id");
        String phone = (String) claims.get("phone");
        String roleStr = (String) claims.get("roles");
        return new JwtDTO(id, phone, Arrays.asList(roleStr.split(",")));
    }

}
