package com.parea.controllers;

import com.parea.controllers.dto.CrearEventoRequest;
import com.parea.controllers.dto.EventoResponse;
import com.parea.controllers.dto.EventoCardResponse;
import com.parea.entities.Categoria;
import com.parea.entities.Modalidad;
import com.parea.entities.Usuario;
import com.parea.services.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

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

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public Page<EventoCardResponse> buscarEventos(

            // búsqueda general de la pantalla inicial
            @RequestParam(required = false) String texto,

            @RequestParam(required = false) Set<Categoria> categorias,

            @RequestParam(required = false) Modalidad modalidad,

            @RequestParam(required = false) Boolean esGratuito,

            @RequestParam(required = false) Boolean requiereInscripcion,

            @RequestParam(required = false) LocalDateTime desde,

            @RequestParam(required = false) LocalDateTime hasta,

            @PageableDefault(
                    size = 20,
                    sort = "fhInicio"
            )
            Pageable pageable

    ){

        return eventoService.buscarEventos(
                texto,
                categorias,
                modalidad,
                esGratuito,
                requiereInscripcion,
                desde,
                hasta,
                pageable
        );
    }
}