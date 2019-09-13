package me.jhonnatanmesquita.mcspringbackend.dto;

import me.jhonnatanmesquita.mcspringbackend.models.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private  Integer id;

    @NotEmpty(message="Preenchimento obrigatório")
    @Length(min=5, max=80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    private String imageUrl;

    public CategoriaDTO(){
    }

    public CategoriaDTO(Categoria obj){
        id = obj.getId();
        nome = obj.getNome();
        imageUrl = obj.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
