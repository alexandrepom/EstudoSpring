package br.com.gonzaga.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gonzaga.dto.CredenciaisDTO;

/**
 * 
 * @author alexandre.santos
 * Intercepta automaticamente as requisições enviadas para o /login. Este endpoint é 
 * reservado para o o SpringSecurity
 * 
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JWTUtil jwtUtil;
	private AuthenticationManager authenticationManager;
	
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,JWTUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
		
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(),new ArrayList<>());
			
			Authentication auth = authenticationManager.authenticate(authToken);
			
			return auth;	
		}catch (IOException e) {
			throw new RuntimeException();
		}
		
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) 
			throws IOException, ServletException {
		
		String username = ((UserSS) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        res.addHeader("Authorization", "Bearer " + token);
		
	}


	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
	
	}

}


