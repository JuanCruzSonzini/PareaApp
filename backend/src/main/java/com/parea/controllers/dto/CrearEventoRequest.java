package com.parea.controllers.dto;

import com.parea.entities.Modalidad;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearEventoRequest {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    @NotNull(message = "La fecha/hora de inicio es obligatoria")
    private LocalDateTime fhInicio;

    @NotNull(message = "La fecha/hora de fin es obligatoria")
    private LocalDateTime fhFin;

    @NotNull(message = "Debe indicar si el evento es gratuito")
    private Boolean esGratuito;

    @Builder.Default
    @NotEmpty(message = "Debe indicar al menos una categoría")
    private Set<Long> categoriaIds  = new HashSet<>();

    private BigDecimal precio;

    @NotNull(message = "Debe indicar si requiere inscripción")
    private Boolean requiereInscripcion;

    private Integer cupoMax;
}