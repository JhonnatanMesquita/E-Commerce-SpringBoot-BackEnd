package me.jhonnatanmesquita.mcspringbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1l;

    @NotEmpty(message = "Preenchimento Obrigatório")
    @Email(message = "E-mail inválido!")
    private String email;

    public EmailDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
