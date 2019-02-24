package me.jhonnatanmesquita.mcspringbackend.dao;

import me.jhonnatanmesquita.mcspringbackend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDao extends JpaRepository <Categoria, Integer>{



}
