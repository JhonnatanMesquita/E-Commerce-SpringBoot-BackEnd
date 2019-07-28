package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.dao.ClienteDao;
import me.jhonnatanmesquita.mcspringbackend.dto.ClienteDTO;
import me.jhonnatanmesquita.mcspringbackend.exceptions.DataIntegrityException;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao dao;

    public Cliente find(Integer id){
        Optional<Cliente> obj = dao.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update (Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return dao.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try {
            dao.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir este cliente!");
        }
    }

    public List<Cliente> findAll(){
        return dao.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orederBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orederBy);
        return dao.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
