package com.example.cinepick_be.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

   private final JwtTokenProvider jwtTokenProvider;
   private final UserDetailsService userDetailsService;

   public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
      this.jwtTokenProvider = jwtTokenProvider;
      this.userDetailsService = userDetailsService;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
           throws ServletException, IOException {

      try {
         // 1. 토큰 추출
         String token = jwtTokenProvider.resolveToken(request);

         // 2. 토큰 검증
         if (token != null && jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserId(token);

            // 3. 사용자 정보 조회
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            if (userDetails != null) {
               UsernamePasswordAuthenticationToken authentication =
                       new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }
         }

      } catch (Exception e) {
         // 오류 발생 시 로그 추가
         System.out.println("JWT 검증 중 오류 발생: " + e.getMessage());
      }

      // 다음 필터로 이동
      filterChain.doFilter(request, response);
   }
}