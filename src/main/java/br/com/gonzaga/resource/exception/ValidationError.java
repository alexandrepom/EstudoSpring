package br.com.gonzaga.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	

	public List<FieldMessage> getErros() {
		return errors;
	}

	public ValidationError(Integer status, String error, Long timestamp, String message, String path) {
		super(status, error, timestamp, message, path);
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName,message));
	}
	
	
	

}
