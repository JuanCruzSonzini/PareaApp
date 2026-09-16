package com.parea.config;

import com.parea.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuracion central de Spring Security: define que endpoints son publicos,
// que endpoints requieren autenticacion, y como se valida esa autenticacion (JWT, sin sesiones).
@Configuration
@EnableWebSecurity
// Habilita anotaciones como @PreAuthorize / @Secured en los metodos de los controllers/services.
@EnableMethodSecurity
// Lombok genera un constructor con los campos "final" de abajo (inyeccion por constructor).
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro propio que intercepta cada request, valida el JWT del header Authorization
    // y, si es valido, deja al usuario autenticado en el SecurityContext.
    private final JwtAuthenticationFilter jwtAuthFilter;
    // Provider que sabe como validar credenciales (usuario/contraseña) contra la base,
    // usado en el login (no en cada request, eso lo hace el filtro JWT).
    private final AuthenticationProvider authenticationProvider;

    // Bean principal: arma la cadena de filtros de seguridad que procesa cada request HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Se deshabilita CSRF porque la API es stateless y usa JWT, no cookies de sesion,
                // asi que no aplica la proteccion CSRF tradicional basada en sesiones.
                .csrf(AbstractHttpConfigurer::disable)
                // No se guarda sesion en el servidor: cada request debe traer su propio JWT.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Login/registro: deben ser publicos, si no nadie podria loguearse.
                        .requestMatchers("/api/auth/**").permitAll()
                        // Documentacion de la API (Swagger/OpenAPI): publica para poder consultarla sin token.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**")
                        .permitAll()
                        // Listar/buscar eventos (GET) es publico: cualquiera puede "descubrir" eventos sin loguearse.
                        .requestMatchers(HttpMethod.GET, "/api/eventos")
                        .permitAll()
                        // Cualquier otro endpoint no listado arriba (crear evento, POST, etc.) exige estar autenticado.
                        .anyRequest().authenticated())
                // Le indica a Spring Security como validar usuario/contraseña cuando se hace login.
                .authenticationProvider(authenticationProvider)
                // Inserta el filtro JWT antes del filtro estandar de usuario/contraseña,
                // para que cada request se autentique via token antes de llegar a los controllers.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
