// EstadoEventoRepository.java
package com.parea.repositories;

import com.parea.entities.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoEventoRepository extends JpaRepository<EstadoEvento, Long> {
    Optional<EstadoEvento> findByNombre(String nombre);
}