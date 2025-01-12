package com.example.cinepick_be.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
   @Value("${jwt.secret}")
   private String secretKey ;

   public String extractUserIdFromToken(String token){
      try{
         token = token.replace("Bearer ", "");
         System.out.println(token);
         Claims claims = Jwts.parser()
               .setSigningKey(secretKey)
               .parseClaimsJws(token)
               .getBody();
         return claims.getSubject();
      }catch(Exception e){
         e.printStackTrace();
         return null;
      }
   }
}
