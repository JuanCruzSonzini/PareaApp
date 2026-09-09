package com.parea.services;

import com.parea.controllers.dto.CambiarRolRequest;
import com.parea.controllers.dto.UsuarioResponse;
import com.parea.entities.Usuario;
import com.parea.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioAdminService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @Transactional
    public UsuarioResponse cambiarRol(Long id, CambiarRolRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setRol(request.getRol());
        Usuario actualizado = usuarioRepository.save(usuario);

        return UsuarioResponse.from(actualizado);
    }
}