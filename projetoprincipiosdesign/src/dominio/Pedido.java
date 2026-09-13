package projetoprincipiosdesign.src.dominio;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private Long id;
    private LocalDate data;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private double valorTotal;

    public Pedido(Cliente cliente, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.itens = itens;
        this.data = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
