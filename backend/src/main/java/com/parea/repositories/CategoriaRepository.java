// CategoriaRepository.java
package com.parea.repositories;

import com.parea.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}