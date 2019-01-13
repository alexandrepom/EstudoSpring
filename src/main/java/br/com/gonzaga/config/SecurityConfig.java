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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled=true)
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
			"/produtos/**",
			"/categorias/**",
			"/estados/**"
		};
	
	private static final String[] PUBLIC_MATCHES_POST = {
			"/clientes/**",
			"/auth/forgot/**"
		};
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
		"/swagger-ui.html", "/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {//testa se estou no ambiente de teste.
			//caso esteja, permite o acesso à url do H2
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();//desabilita a proteção contra ataque csrf
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET ,PUBLIC_MATCHES_GET).permitAll()//libera GET para esses matches
			.antMatchers(HttpMethod.POST ,PUBLIC_MATCHES_POST).permitAll()//libera POST para esses matches
			.antMatchers(PUBLIC_MATCHES).permitAll()//libera qualquer método para esses matches
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
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST","GET", "PUT","DELETE","OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration );
		return source;
	}
	

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
