package com.parea.services;

import com.parea.controllers.dto.AuthResponse;
import com.parea.controllers.dto.LoginRequest;
import com.parea.controllers.dto.RegisterRequest;
import com.parea.entities.Usuario;
import com.parea.repositories.UsuarioRepository;
import com.parea.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(
                Map.of("rol", usuarioGuardado.getRol().name()),
                usuarioGuardado
        );

        return AuthResponse.builder()
                .token(jwtToken)
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .email(usuarioGuardado.getEmail())
                .rol(usuarioGuardado.getRol())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(
                Map.of("rol", usuario.getRol().name()),
                usuario
        );

        return AuthResponse.builder()
                .token(jwtToken)
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}
