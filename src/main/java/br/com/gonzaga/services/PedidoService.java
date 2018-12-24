package br.com.gonzaga.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gonzaga.domain.ItemPedido;
import br.com.gonzaga.domain.PagamentoComBoleto;
import br.com.gonzaga.domain.Pedido;
import br.com.gonzaga.domain.enums.EstadoPagamento;
import br.com.gonzaga.repositories.ItemPedidoRepository;
import br.com.gonzaga.repositories.PagamentoRepository;
import br.com.gonzaga.repositories.PedidoRepository;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) throws ObjectNotFoundException{
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Pedido.class.getName()));
	}

	
	@Transactional
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		//obj.setCliente(clienteRepository.findById(obj.getCliente().getId()).orElse(null));
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());
		}
		
		obj = pedidoRepository.save(obj);
		System.out.println("Salvei o pedido");
		
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
	
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
		
	}

}
