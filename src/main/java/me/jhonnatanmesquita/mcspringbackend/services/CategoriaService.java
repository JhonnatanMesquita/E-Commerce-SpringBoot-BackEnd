package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.exceptions.DataIntegrityException;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.dao.CategoriaDao;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDao dao;

    public Categoria find(Integer id){
        Optional<Categoria> obj = dao.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return dao.save(obj);
    }

    public Categoria update (Categoria obj){
        find(obj.getId());
        return dao.save(obj);
    }

    public void delete(Integer id){
        find(id);
        try {
            dao.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possuí produtos cadastrados nela!");
        }
    }

    public List<Categoria> findAll(){
        return dao.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orederBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orederBy);
        return dao.findAll(pageRequest);
    }
}
