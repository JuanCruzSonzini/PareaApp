package com.parea.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estado_evento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_evento")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "permite_inscripcion", nullable = false)
    private Boolean permiteInscripcion;
}