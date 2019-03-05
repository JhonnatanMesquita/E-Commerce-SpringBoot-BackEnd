package me.jhonnatanmesquita.mcspringbackend.dao;

import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDao extends JpaRepository <Pedido, Integer>{



}
