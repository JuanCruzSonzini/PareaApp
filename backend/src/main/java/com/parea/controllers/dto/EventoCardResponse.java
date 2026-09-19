package com.parea.controllers.dto;

import com.parea.entities.Evento;
import com.parea.entities.Categoria;
import com.parea.entities.Modalidad;

import java.time.LocalDateTime;
import java.util.Set;

public record EventoCardResponse(

        Long idEvento,

        String titulo,

        Set<Categoria> categorias,

        Modalidad modalidad,

        LocalDateTime fhInicio,

        Boolean esGratuito

) {

    public static EventoCardResponse from(Evento evento) {

        return new EventoCardResponse(
                evento.getIdEvento(),
                evento.getTitulo(),
                evento.getCategorias(),
                evento.getModalidad(),
                evento.getFhInicio(),
                evento.getEsGratuito()
        );
    }
}