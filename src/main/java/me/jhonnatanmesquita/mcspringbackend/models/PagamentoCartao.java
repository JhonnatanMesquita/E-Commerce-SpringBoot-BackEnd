package me.jhonnatanmesquita.mcspringbackend.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import me.jhonnatanmesquita.mcspringbackend.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoCartao")
public class PagamentoCartao extends Pagamento{

    private static final long serialVersionUID = 1L;

    private Integer numParcelas;

    public PagamentoCartao(){}

    public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numParcelas) {
        super(id, estado, pedido);
        this.numParcelas = numParcelas;
    }

    public Integer getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(Integer numParcelas) {
        this.numParcelas = numParcelas;
    }
}
