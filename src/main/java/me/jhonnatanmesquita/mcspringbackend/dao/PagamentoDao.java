package me.jhonnatanmesquita.mcspringbackend.dao;

import me.jhonnatanmesquita.mcspringbackend.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoDao extends JpaRepository <Pagamento, Integer>{



}
