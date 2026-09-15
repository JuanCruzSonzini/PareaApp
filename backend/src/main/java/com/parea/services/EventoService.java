package com.parea.services;

import com.parea.controllers.dto.CrearEventoRequest;
import com.parea.controllers.dto.EventoResponse;
import com.parea.entities.Evento;
import com.parea.entities.Usuario;
import com.parea.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public EventoResponse crearEvento(CrearEventoRequest request, Usuario creador) {

        if (request.getFhFin().isBefore(request.getFhInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }

        if (!request.getEsGratuito() && request.getPrecio() == null) {
            throw new IllegalArgumentException("Debe indicar un precio si el evento no es gratuito");
        }

        Evento evento = Evento.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .modalidad(request.getModalidad())
                .fhInicio(request.getFhInicio())
                .fhFin(request.getFhFin())
                .esGratuito(request.getEsGratuito())
                .precio(request.getEsGratuito() ? null : request.getPrecio())
                .requiereInscripcion(request.getRequiereInscripcion())
                .cupoMax(request.getCupoMax())
                .verificado(false)
                .estadoVerificacion("PENDIENTE")
                .fhAlta(LocalDateTime.now())
                .creador(usuario)
                .build();

        Evento guardado = eventoRepository.save(evento);

        return EventoResponse.from(guardado);
    }
}