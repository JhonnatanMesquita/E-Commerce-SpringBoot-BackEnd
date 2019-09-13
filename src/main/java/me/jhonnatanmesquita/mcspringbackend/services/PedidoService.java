package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.enums.EstadoPagamento;
import me.jhonnatanmesquita.mcspringbackend.exceptions.AuthorizationException;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import me.jhonnatanmesquita.mcspringbackend.models.ItemPedido;
import me.jhonnatanmesquita.mcspringbackend.models.PagamentoBoleto;
import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import me.jhonnatanmesquita.mcspringbackend.repositories.ItemPedidoRepository;
import me.jhonnatanmesquita.mcspringbackend.repositories.PagamentoRepository;
import me.jhonnatanmesquita.mcspringbackend.repositories.PedidoRepository;
import me.jhonnatanmesquita.mcspringbackend.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

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

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());

        obj.setCliente(clienteService.find(obj.getCliente().getId()));

        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoBoleto){
            PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
            boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.00);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());

        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){

        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso Negado!");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return repo.findByCliente(cliente, pageRequest);

    }
}
