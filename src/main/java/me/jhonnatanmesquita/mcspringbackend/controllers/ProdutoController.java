package me.jhonnatanmesquita.mcspringbackend.controllers;

import me.jhonnatanmesquita.mcspringbackend.controllers.utils.URL;
import me.jhonnatanmesquita.mcspringbackend.dto.ProdutoDTO;
import me.jhonnatanmesquita.mcspringbackend.dto.ProdutoNewDTO;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import me.jhonnatanmesquita.mcspringbackend.models.ItemPedido;
import me.jhonnatanmesquita.mcspringbackend.models.Produto;
import me.jhonnatanmesquita.mcspringbackend.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id){

        Produto obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "0") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction)
    {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert (@Valid @RequestBody ProdutoNewDTO objDto){
        Produto obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update (@Valid @RequestBody ProdutoNewDTO objDto){
        Produto obj = service.fromDTO(objDto);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete (@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
