package com.parea.controllers.dto;

import com.parea.entities.Evento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {

    private Long idEvento;
    private String titulo;
    private String descripcion;
    private String modalidad;
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