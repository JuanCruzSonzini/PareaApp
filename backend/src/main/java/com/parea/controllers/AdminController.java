package com.parea.controllers;

import com.parea.controllers.dto.CambiarRolRequest;
import com.parea.controllers.dto.UsuarioResponse;
import com.parea.services.UsuarioAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioAdminService usuarioAdminService;

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioAdminService.listarUsuarios();
    }

    @PatchMapping("/{id}/rol")
    public UsuarioResponse cambiarRol(
            @PathVariable Long id,
            @Valid @RequestBody CambiarRolRequest request
    ) {
        return usuarioAdminService.cambiarRol(id, request);
    }
}