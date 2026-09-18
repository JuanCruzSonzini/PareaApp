package com.parea.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;

import com.parea.entities.Modalidad;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

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

    @ElementCollection(targetClass = Categoria.class)
    @CollectionTable(
            name = "evento_categorias",
            joinColumns = @JoinColumn(name = "evento_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    @Builder.Default
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}

