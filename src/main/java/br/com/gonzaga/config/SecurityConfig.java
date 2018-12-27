/**
 * a classe deve extender WebSecurityConfigurerAdapter
 */

package br.com.gonzaga.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.gonzaga.security.JWTAuthenticationFilter;
import br.com.gonzaga.security.JWTAuthorizationFilter;
import br.com.gonzaga.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//urls públicas
	private static final String[] PUBLIC_MATCHES = {
		"/h2-console/**"
	};
	
	private static final String[] PUBLIC_MATCHES_GET = {
			"/h2-console/**",
			"/produtos/**",
			"/categorias/**"
		};
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {//testa se estou no ambiente de teste.
			//caso esteja, permite o acesso à url do H2
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();//desabilita a proteção contra ataque csrf
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET ,PUBLIC_MATCHES_GET).permitAll()//libera para esses
			.antMatchers(PUBLIC_MATCHES).permitAll()//libera para esses
			.anyRequest().authenticated();//para os demais exige autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtUtil)); //filtro de autenticação
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtUtil,userDetailsService));//filtro de autorização
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //define que o sistema não armazena sessão
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
