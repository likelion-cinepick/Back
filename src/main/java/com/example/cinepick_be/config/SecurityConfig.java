package com.example.cinepick_be.config;

import com.example.cinepick_be.security.JwtTokenFilter;
import com.example.cinepick_be.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private final JwtTokenProvider jwtTokenProvider;
   private final UserDetailsService userDetailsService;

   public SecurityConfig(JwtTokenProvider jwtTokenProvider, @Lazy UserDetailsService userDetailsService) {
      this.jwtTokenProvider = jwtTokenProvider;
      this.userDetailsService = userDetailsService;
   }

   @Bean
   public JwtTokenFilter jwtTokenFilter() {
      return new JwtTokenFilter(jwtTokenProvider, userDetailsService);
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable())
              .cors() // CORS 활성화
              .and()
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers(
                              "/cinepick/my/**",
                              "/swagger-ui/**",
                              "/v3/api-docs/**",
                              "/swagger-resources/**",
                              "/webjars/**",
                              "/**"
                      ).permitAll() // 회원가입, 로그인, Swagger 관련 경로는 인증 없이 접근 허용
                      .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
              )
              .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가
      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }
}
