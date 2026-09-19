package com.parea.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizador", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_evento", nullable = false)
    private EstadoEvento estadoEvento;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Modalidad modalidad;

    @Column(name = "fh_inicio", nullable = false)
    private LocalDateTime fhInicio;

    @Column(name = "fh_fin")
    private LocalDateTime fhFin;

    @Column(name = "es_gratuito", nullable = false)
    private Boolean esGratuito;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "requiere_inscripcion", nullable = false)
    private Boolean requiereInscripcion;

    @Column(name = "cupo_max")
    private Integer cupoMax;

    @Column(nullable = false)
    private Boolean verificado;

    @Column(name = "fh_alta", nullable = false)
    private LocalDateTime fhAlta;

    @Column(name = "fh_baja")
    private LocalDateTime fhBaja;

    @ManyToMany
    @JoinTable(
        name = "evento_categoria",
        joinColumns = @JoinColumn(name = "id_evento"),
        inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @Builder.Default
    private Set<Categoria> categorias = new HashSet<>();
}