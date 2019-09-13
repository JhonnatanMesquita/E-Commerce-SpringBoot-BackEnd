package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.dto.ProdutoDTO;
import me.jhonnatanmesquita.mcspringbackend.dto.ProdutoNewDTO;
import me.jhonnatanmesquita.mcspringbackend.exceptions.AuthorizationException;
import me.jhonnatanmesquita.mcspringbackend.exceptions.DataIntegrityException;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import me.jhonnatanmesquita.mcspringbackend.models.Produto;
import me.jhonnatanmesquita.mcspringbackend.repositories.CategoriaRepository;
import me.jhonnatanmesquita.mcspringbackend.repositories.ProdutoRepository;
import me.jhonnatanmesquita.mcspringbackend.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    public Produto find(Integer id){
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public List<Produto> findAll(){
        return repo.findAll();
    }


    @Transactional
    public Produto insert(Produto obj){
        obj.setId(null);
        obj = repo.save(obj);
        return obj;
    }

    @Transactional
    public Produto update(Produto obj){
        obj = repo.save(obj);
        return obj;
    }

    public void delete(Integer id){
        find(id);
        try {
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir este cliente!");
        }
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        System.out.println(nome);
        if(ids.contains(0) || ids.contains("0")) {
            return new PageImpl<>(repo.findAllByNomeContains(nome), pageRequest, repo.findAll().size());
        }else {
            List<Categoria> categorias = categoriaRepository.findAllById(ids);
            return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
        }
    }

    public Produto fromDTO(ProdutoDTO objDto){
        return new Produto(objDto.getId(), objDto.getNome(), objDto.getPreco(), objDto.getImageUrl());
    }

    public Produto fromDTO(ProdutoNewDTO objDto){
        Produto prod = new Produto(objDto.getId(), objDto.getNome(), objDto.getPreco(), objDto.getImageUrl());
        for(Categoria cat : objDto.getCategorias()){
            cat.getProdutos().add(prod);
            prod.getCategorias().add(cat);
        }
        return prod;
    }

    public URI uploadPicture (MultipartFile multipartFile, String prodNome){

        UserSS user = UserService.authenticated();

        if(user == null){
            throw new AuthorizationException("Acesso Negado!");
        }

        BufferedImage jpgImage = imageService.formatImage(multipartFile);

        String fileName = prodNome.hashCode() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"),"/produtos", fileName, "image");
    }

}
