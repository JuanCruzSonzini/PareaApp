package com.parea.services;

import com.parea.controllers.dto.CrearEventoRequest;
import com.parea.controllers.dto.EventoResponse;
import com.parea.entities.Evento;
import com.parea.entities.Modalidad;
import com.parea.entities.Usuario;
import com.parea.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.parea.entities.Categoria;
import com.parea.repositories.EventoSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public EventoResponse crearEvento(CrearEventoRequest request, Usuario creador) {

        System.out.println("ENTRO A CREAR EVENTO");
        System.out.println("Request: " + request);
        System.out.println("Creador: " + creador.getEmail());
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
                .categorias(request.getCategorias())
                .fhInicio(request.getFhInicio())
                .fhFin(request.getFhFin())
                .esGratuito(request.getEsGratuito())
                .precio(request.getEsGratuito() ? null : request.getPrecio())
                .requiereInscripcion(request.getRequiereInscripcion())
                .cupoMax(request.getCupoMax())
                .verificado(false)
                .estadoVerificacion("PENDIENTE")
                .fhAlta(LocalDateTime.now())
                .usuario(creador)
                .build();

        System.out.println("EVENTO CREADO EN MEMORIA");

        Evento guardado = eventoRepository.save(evento);
        System.out.println("EVENTO GUARDADO: " + guardado.getIdEvento());

        return EventoResponse.from(guardado);
    }

    public Page<EventoResponse> buscarEventos(

            String texto,
            Set<Categoria> categorias,
            Modalidad modalidad,
            Boolean esGratuito,
            Boolean requiereInscripcion,
            LocalDateTime desde,
            LocalDateTime hasta,
            Pageable pageable

    ) {

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
                .map(EventoResponse::from);
    }

}