package me.jhonnatanmesquita.mcspringbackend.controllers;

import me.jhonnatanmesquita.mcspringbackend.repositories.PedidoRepository;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoController {

    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
    }

}
