package com.parea.repositories;

import com.parea.entities.Categoria;
import com.parea.entities.Evento;
import com.parea.entities.Modalidad;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventoSpecifications {


    public static Specification<Evento> textoContiene(String texto) {

        return (root, query, cb) -> {

            if (texto == null || texto.isBlank()) {
                return null;
            }


            String busqueda =
                    "%" + texto.toLowerCase() + "%";


            return cb.or(
                    cb.like(
                            cb.lower(root.get("titulo")),
                            busqueda
                    ),

                    cb.like(
                            cb.lower(root.get("descripcion")),
                            busqueda
                    )
            );
        };
    }



    public static Specification<Evento> activo() {

        return (root, query, cb) ->
                cb.isNull(root.get("fhBaja"));

    }



    public static Specification<Evento> categoriaEs(Categoria categoria) {

        return (root, query, cb) ->
                categoria == null ? null :
                        cb.equal(root.get("categoria"), categoria);

    }



    public static Specification<Evento> modalidadEs(Modalidad modalidad) {

        return (root, query, cb) ->
                modalidad == null ? null :
                        cb.equal(root.get("modalidad"), modalidad);

    }



    public static Specification<Evento> esGratuitoEs(Boolean esGratuito) {

        return (root, query, cb) ->
                esGratuito == null ? null :
                        cb.equal(root.get("esGratuito"), esGratuito);

    }



    public static Specification<Evento> requiereInscripcionEs(Boolean requiereInscripcion) {

        return (root, query, cb) ->
                requiereInscripcion == null ? null :
                        cb.equal(root.get("requiereInscripcion"), requiereInscripcion);

    }



    public static Specification<Evento> desde(LocalDateTime desde) {

        return (root, query, cb) ->
                desde == null ? null :
                        cb.greaterThanOrEqualTo(
                                root.get("fhInicio"),
                                desde
                        );

    }



    public static Specification<Evento> hasta(LocalDateTime hasta) {

        return (root, query, cb) ->
                hasta == null ? null :
                        cb.lessThanOrEqualTo(
                                root.get("fhInicio"),
                                hasta
                        );

    }

}