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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

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
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/register/**", "/login/**"
                        ,"/image/**"
                        ,"/movies/all"
                        ,"/uploads/**"
                        ,"/movies/{movieId}"
                  ).permitAll()
                  .anyRequest().authenticated()
            )
            .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .cors(withDefaults()); // CORS 설정 적용

      return http.build();
   }

   @Bean
   public UrlBasedCorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.addAllowedOriginPattern("*"); // 모든 도메인 허용
      configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
      configuration.addAllowedHeader("*"); // 모든 헤더 허용
      configuration.setAllowCredentials(true); // 인증 정보 허용

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);

      return source;
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
