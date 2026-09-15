package com.parea.controllers;

import com.parea.controllers.dto.CrearEventoRequest;
import com.parea.controllers.dto.EventoResponse;
import com.parea.entities.Usuario;
import com.parea.services.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoResponse> crearEvento(
            @Valid @RequestBody CrearEventoRequest request,
            @AuthenticationPrincipal Usuario usuario
    ) {
        EventoResponse response = eventoService.crearEvento(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}