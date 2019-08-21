package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.controllers.PedidoController;
import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoService {

    @Autowired
    private PedidoController controller;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Integer id){

        Pedido obj = controller.find(id);

        return ResponseEntity.ok().body(obj);
    }

}
