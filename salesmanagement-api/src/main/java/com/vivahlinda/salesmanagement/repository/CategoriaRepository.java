package com.vivahlinda.salesmanagement.repository;

import com.vivahlinda.salesmanagement.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> getAllCategoria();
}
