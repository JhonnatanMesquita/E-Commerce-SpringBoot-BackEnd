package me.jhonnatanmesquita.mcspringbackend.service;

import me.jhonnatanmesquita.mcspringbackend.dao.CategoriaDao;
import me.jhonnatanmesquita.mcspringbackend.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDao dao;

    public Categoria buscar(Integer id){
        Optional<Categoria> obj = dao.findById(id);
        return obj.orElse(null);
    }

}
