package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.controllers.CategoriaController;
import me.jhonnatanmesquita.mcspringbackend.dto.CategoriaDTO;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaService {

    @Autowired
    private CategoriaController controller;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id){
        Categoria obj = controller.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert (@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = controller.fromDTO(objDto);
        obj = controller.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update (@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
        Categoria obj = controller.fromDTO(objDto);
        obj.setId(id);
        obj = controller.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete (@PathVariable Integer id){
        controller.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = controller.findAll();
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(value="/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "ordeBy", defaultValue = "nome") String orederBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction)
    {
        Page<Categoria> list = controller.findPage(page, linesPerPage, orederBy, direction);
        Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
