package br.com.gonzaga.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ApiInfo;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				//.apis(RequestHandlerSelectors.any()) //pacotes que serão rastreados para serem documentados
				.apis(RequestHandlerSelectors.basePackage("br.com.gonzaga.resource"))
				.paths(PathSelectors.any())//caminhos a serem rastreados
				.build()
				.apiInfo(apiInfo());
	}
	
	/*
	 * fornece informações da API
	 */
	private ApiInfo apiInfo() {
		 return new ApiInfo(
				 "API do curso Spring Boot",
				 "Esta API é utilizada no curso de Spring Boot do prof. Nelio Alves",
				 "Versão 3.1.34",
				 "https://www.udemy.com/terms",
				 new Contact("Nelio Alves", "udemy.com/user/nelio-alves", "nelio.cursos@gmail.com"),
				 "Permitido uso para estudantes",
				 "https://www.udemy.com/terms",
				 Collections.emptyList() // Vendor Extensions
				 );
	}
	
}
