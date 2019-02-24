package me.jhonnatanmesquita.mcspringbackend.dao;

import me.jhonnatanmesquita.mcspringbackend.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeDao extends JpaRepository <Cidade, Integer>{



}