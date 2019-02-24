package me.jhonnatanmesquita.mcspringbackend.dao;

import me.jhonnatanmesquita.mcspringbackend.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoDao extends JpaRepository <Estado, Integer>{



}