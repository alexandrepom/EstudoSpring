package br.com.gonzaga.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.gonzaga.domain.enums.TipoCliente;
import br.com.gonzaga.dto.ClienteNewDTO;
import br.com.gonzaga.repositories.ClienteRepository;
import br.com.gonzaga.resource.exception.FieldMessage;
import br.com.gonzaga.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
		//ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		//lista de verificações
		if(objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod())&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CPF Inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","CNPJ Inválido"));
		}
		if(clienteRepository.findByEmail(objDto.getEmail())!=null) {
			list.add(new FieldMessage("email","Email já cadastrado no banco"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
	
	

}
