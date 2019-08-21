package me.jhonnatanmesquita.mcspringbackend.repositories;

import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository <Categoria, Integer>{



}
