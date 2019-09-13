package me.jhonnatanmesquita.mcspringbackend.services.validation;

import me.jhonnatanmesquita.mcspringbackend.dto.ClienteDTO;
import me.jhonnatanmesquita.mcspringbackend.exceptions.FieldMessage;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import me.jhonnatanmesquita.mcspringbackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteUpdate ann){

    }

    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context){

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repo.findByEmail((objDto.getEmail()));

        if(aux != null && !aux.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Este e-mail já existe"));
        }

        for (FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
