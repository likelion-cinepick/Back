package com.example.cinepick_be.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
      info = @Info(title = "API Documentation", version = "v1"),
      security = @SecurityRequirement(name = "bearerAuth"),
      servers = {
            @Server(url = "http://localhost:8080"), // 로컬 개발 서버 URL
            @Server(url = "http://3.105.163.214:8080") // 원격 서버 URL
      }
)
@SecurityScheme(
      name = "bearerAuth",
      type = SecuritySchemeType.HTTP,
      scheme = "bearer",
      bearerFormat = "JWT"
)
public class SwaggerConfig {
}