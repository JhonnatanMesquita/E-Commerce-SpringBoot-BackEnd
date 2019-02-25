package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.dao.ClienteDao;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao dao;

    public Cliente buscar(Integer id){
        Optional<Cliente> obj = dao.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()));
    }

}
