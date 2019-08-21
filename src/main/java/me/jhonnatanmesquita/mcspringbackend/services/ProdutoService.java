package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.controllers.ProdutoController;
import me.jhonnatanmesquita.mcspringbackend.dto.CategoriaDTO;
import me.jhonnatanmesquita.mcspringbackend.dto.ProdutoDTO;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import me.jhonnatanmesquita.mcspringbackend.models.Produto;
import me.jhonnatanmesquita.mcspringbackend.services.utils.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoService {

    @Autowired
    private ProdutoController controller;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id){

        Produto obj = controller.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "0") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "ordeBy", defaultValue = "nome") String orederBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction)
    {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = controller.search(nomeDecoded, ids, page, linesPerPage, orederBy, direction);
        Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
