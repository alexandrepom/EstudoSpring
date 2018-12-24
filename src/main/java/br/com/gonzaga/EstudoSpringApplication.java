package br.com.gonzaga;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.gonzaga.domain.Categoria;
import br.com.gonzaga.domain.Cidade;
import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.domain.Endereco;
import br.com.gonzaga.domain.Estado;
import br.com.gonzaga.domain.ItemPedido;
import br.com.gonzaga.domain.Pagamento;
import br.com.gonzaga.domain.PagamentoComBoleto;
import br.com.gonzaga.domain.PagamentoComCartao;
import br.com.gonzaga.domain.Pedido;
import br.com.gonzaga.domain.Produto;
import br.com.gonzaga.domain.enums.EstadoPagamento;
import br.com.gonzaga.domain.enums.TipoCliente;
import br.com.gonzaga.repositories.CategoriaRepository;
import br.com.gonzaga.repositories.CidadeRepository;
import br.com.gonzaga.repositories.ClienteRepository;
import br.com.gonzaga.repositories.EnderecoRepository;
import br.com.gonzaga.repositories.EstadoRepository;
import br.com.gonzaga.repositories.ItemPedidoRepository;
import br.com.gonzaga.repositories.PagamentoRepository;
import br.com.gonzaga.repositories.PedidoRepository;
import br.com.gonzaga.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoSpringApplication implements CommandLineRunner{


	
	public static void main(String[] args) {
		SpringApplication.run(EstudoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		
		
	}
	
	
	
}
