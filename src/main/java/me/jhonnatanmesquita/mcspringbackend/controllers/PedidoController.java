package me.jhonnatanmesquita.mcspringbackend.controllers;

import me.jhonnatanmesquita.mcspringbackend.enums.EstadoPagamento;
import me.jhonnatanmesquita.mcspringbackend.models.ItemPedido;
import me.jhonnatanmesquita.mcspringbackend.models.Pagamento;
import me.jhonnatanmesquita.mcspringbackend.models.PagamentoBoleto;
import me.jhonnatanmesquita.mcspringbackend.repositories.ItemPedidoRepository;
import me.jhonnatanmesquita.mcspringbackend.repositories.PagamentoRepository;
import me.jhonnatanmesquita.mcspringbackend.repositories.PedidoRepository;
import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import me.jhonnatanmesquita.mcspringbackend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoController {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoController boletoController;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());

        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoBoleto){
            PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
            boletoController.preencherPagamentoBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.00);
            ip.setPreco(produtoController.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }

}
