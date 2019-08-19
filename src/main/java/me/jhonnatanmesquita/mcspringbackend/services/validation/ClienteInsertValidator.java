package me.jhonnatanmesquita.mcspringbackend.services.validation;

import me.jhonnatanmesquita.mcspringbackend.dao.ClienteDao;
import me.jhonnatanmesquita.mcspringbackend.dto.ClienteNewDTO;
import me.jhonnatanmesquita.mcspringbackend.enums.TipoCliente;
import me.jhonnatanmesquita.mcspringbackend.exceptions.FieldMessage;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import me.jhonnatanmesquita.mcspringbackend.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteDao repo;

    @Override
    public void initialize(ClienteInsert ann){

    }

    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context){
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
        }

        Cliente aux = repo.findByEmail((objDto.getEmail()));

        if(aux != null) {
            list.add(new FieldMessage("email", "Este e-mail já existe"));
        }

        for (FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
