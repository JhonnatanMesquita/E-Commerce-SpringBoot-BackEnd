package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.models.Cidade;
import me.jhonnatanmesquita.mcspringbackend.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findByEstado(Integer estadoId){
        return repo.findCidades(estadoId);
    }
}
