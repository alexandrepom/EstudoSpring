package br.com.gonzaga.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.gonzaga.security.UserSS;

/**
 * 
 * @author alexandre.santos
 *	Classe de servicço que é utilizada para retornar um usuário logado
 */

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
