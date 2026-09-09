package com.parea.config;

import com.parea.entities.Rol;
import com.parea.entities.Usuario;
import com.parea.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
                    .email("admin@parea.com")
                    .password(passwordEncoder.encode("CAMBIAR_ESTA_CLAVE"))
                    .rol(Rol.ADMIN)
                    .build();
            usuarioRepository.save(admin);
        }
    }
}