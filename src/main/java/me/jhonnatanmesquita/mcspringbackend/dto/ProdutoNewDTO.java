package me.jhonnatanmesquita.mcspringbackend.dto;

import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import me.jhonnatanmesquita.mcspringbackend.models.Produto;

import java.io.Serializable;
import java.util.List;

public class ProdutoNewDTO implements Serializable {
    private static final long serialVersionUID = 1l;

    private Integer id;
    private String nome;
    private Double preco;
    private List<Categoria> categorias;

    public ProdutoNewDTO() {
    }

    public ProdutoNewDTO(Produto obj) {
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
        categorias = obj.getCategorias();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categoria_id) {
        this.categorias = categoria_id;
    }
}
