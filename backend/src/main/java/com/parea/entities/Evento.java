package com.parea.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String modalidad;

    @Column(nullable = false)
    private LocalDateTime fhInicio;

    @Column(nullable = false)
    private LocalDateTime fhFin;

    @Column(nullable = false)
    private boolean esGratuito;

    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean requiereInscripcion;

    private Integer cupoMax;

    @Column(nullable = false)
    private Boolean verificado;

    @Column(nullable = false)
    private String estadoVerificacion;

    @Column(nullable = false)
    private LocalDateTime fhAlta;

    private LocalDateTime fhBaja;

    private LocalDateTime fechaVerificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}

