package com.parea.services;

import com.parea.controllers.dto.CrearEventoRequest;
import com.parea.controllers.dto.EventoCardResponse;
import com.parea.controllers.dto.EventoResponse;
import com.parea.entities.Categoria;
import com.parea.entities.EstadoEvento;
import com.parea.entities.Evento;
import com.parea.entities.Modalidad;
import com.parea.entities.Usuario;
import com.parea.repositories.CategoriaRepository;
import com.parea.repositories.EstadoEventoRepository;
import com.parea.repositories.EventoRepository;
import com.parea.repositories.EventoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventoService {

    // Estado con el que nace todo evento nuevo.
    // Ajustar a "publicado" si el equipo decide que nazcan visibles directamente.
    private static final String ESTADO_INICIAL = "borrador";

    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EstadoEventoRepository estadoEventoRepository;

    @Transactional
    public EventoResponse crearEvento(CrearEventoRequest request, Usuario creador) {

        if (request.getFhFin() != null && request.getFhFin().isBefore(request.getFhInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }

        if (!request.getEsGratuito() && request.getPrecio() == null) {
            throw new IllegalArgumentException("Debe indicar un precio si el evento no es gratuito");
        }

        // Resolvemos las categorías reales a partir de los IDs recibidos.
        Set<Categoria> categorias = new HashSet<>(categoriaRepository.findAllById(request.getCategoriaIds()));
        if (categorias.size() != request.getCategoriaIds().size()) {
            throw new IllegalArgumentException("Alguna de las categorías indicadas no existe");
        }

        EstadoEvento estadoInicial = estadoEventoRepository.findByNombre(ESTADO_INICIAL)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el estado de evento '" + ESTADO_INICIAL + "' en la base"));

        Evento evento = Evento.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .modalidad(request.getModalidad())
                .categorias(categorias)
                .fhInicio(request.getFhInicio())
                .fhFin(request.getFhFin())
                .esGratuito(request.getEsGratuito())
                .precio(request.getEsGratuito() ? BigDecimal.ZERO : request.getPrecio())
                .requiereInscripcion(request.getRequiereInscripcion())
                .cupoMax(request.getCupoMax())
                .verificado(false)
                .estadoEvento(estadoInicial)
                .fhAlta(LocalDateTime.now())
                .usuario(creador) // ← antes decía .organizador(creador)
                .build();

        Evento guardado = eventoRepository.save(evento);

        return EventoResponse.from(guardado);
    }

    @Transactional(readOnly = true)
    public Page<EventoCardResponse> buscarEventos(
            String texto,
            Set<Categoria> categorias,
            Modalidad modalidad,
            Boolean esGratuito,
            Boolean requiereInscripcion,
            LocalDateTime desde,
            LocalDateTime hasta,
            Pageable pageable) {
        Specification<Evento> spec = Specification
                .where(EventoSpecifications.activo())
                .and(EventoSpecifications.noFinalizado())
                .and(EventoSpecifications.textoContiene(texto))
                .and(EventoSpecifications.categoriasTieneTodas(categorias))
                .and(EventoSpecifications.modalidadEs(modalidad))
                .and(EventoSpecifications.esGratuitoEs(esGratuito))
                .and(EventoSpecifications.requiereInscripcionEs(requiereInscripcion))
                .and(EventoSpecifications.desde(desde))
                .and(EventoSpecifications.hasta(hasta));

        return eventoRepository.findAll(spec, pageable)
                .map(EventoCardResponse::from);
    }
}