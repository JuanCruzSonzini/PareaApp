package com.parea.controllers.dto;

import com.parea.entities.Evento;
import com.parea.entities.Categoria;
import com.parea.entities.Modalidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {

    private Long idEvento;
    private String titulo;
    private String descripcion;
    private Modalidad modalidad;
    private Set<Categoria> categorias;
    private LocalDateTime fhInicio;
    private LocalDateTime fhFin;
    private boolean esGratuito;
    private BigDecimal precio;
    private Boolean requiereInscripcion;
    private Integer cupoMax;
    private Boolean verificado;
    private String estadoVerificacion;
    private LocalDateTime fhAlta;
    private String nombreCreador;

    public static EventoResponse from(Evento evento) {
        return EventoResponse.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .modalidad(evento.getModalidad())
                .categorias(evento.getCategorias())
                .fhInicio(evento.getFhInicio())
                .fhFin(evento.getFhFin())
                .esGratuito(evento.isEsGratuito())
                .precio(evento.getPrecio())
                .requiereInscripcion(evento.getRequiereInscripcion())
                .cupoMax(evento.getCupoMax())
                .verificado(evento.getVerificado())
                .estadoVerificacion(evento.getEstadoVerificacion())
                .fhAlta(evento.getFhAlta())
                .nombreCreador(evento.getUsuario().getNombre())
                .build();
    }
}