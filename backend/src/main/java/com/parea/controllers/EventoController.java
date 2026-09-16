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
import com.parea.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping
    public Page<EventoResponse> buscarEventos(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) Categoria categoria,
        @RequestParam(required = false) String modalidad,
        @RequestParam(required = false) Boolean esGratuito,
        @RequestParam(required = false) Boolean requiereInscripcion,
        @RequestParam(required = false) LocalDateTime desde,
        @RequestParam(required = false) LocalDateTime hasta,
        @PageableDefault(size = 20, sort = "fhInicio") Pageable pageable
    ) { 
    return eventoService.buscarEventos(titulo, categoria, modalidad, esGratuito, requiereInscripcion, desde, hasta, pageable);

}
}