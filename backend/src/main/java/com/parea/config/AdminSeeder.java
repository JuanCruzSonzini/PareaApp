package com.parea.config;

import com.parea.entities.RolConstants;
import com.parea.entities.Usuario;
import com.parea.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByEmail("admin@parea.com")) {
            Usuario admin = Usuario.builder()
                    .nombre("Admin")
                    .apellido("Parea")
                    .email("admin@parea.com")
                    .hashContrasena(passwordEncoder.encode("parea"))
                    .rol(RolConstants.ADMIN)
                    .emailConfirmado(true)
                    .estadoCuenta("activo")
                    .verificado(true)
                    .fhAlta(LocalDateTime.now())
                    .build();
            usuarioRepository.save(admin);
        }
    }
}