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
import br.com.gonzaga.repositories.PagamentoRepository;
import br.com.gonzaga.repositories.PedidoRepository;
import br.com.gonzaga.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoSpringApplication implements CommandLineRunner{

	@Autowired CategoriaRepository categoriaRepository;	
	@Autowired ProdutoRepository produtoRepository;
	@Autowired CidadeRepository cidadeRepository;
	@Autowired EstadoRepository estadoRepository;
	@Autowired EnderecoRepository enderecoRepository;
	@Autowired ClienteRepository clienteRepository;
	@Autowired PagamentoRepository pagamentoRepository;
	@Autowired PedidoRepository pedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EstudoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia",est1);
		Cidade c2 = new Cidade(null, "São Paulo",est2);
		Cidade c3 = new Cidade(null, "Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1,e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1,e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		
	}
	
	
	
}
