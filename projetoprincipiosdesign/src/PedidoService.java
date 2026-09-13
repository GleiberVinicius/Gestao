package projetoprincipiosdesign.src;

import projetoprincipiosdesign.src.desconto.Desconto;
import projetoprincipiosdesign.src.desconto.DescontoPadrao;
import projetoprincipiosdesign.src.dominio.ItemPedido;
import projetoprincipiosdesign.src.dominio.Pedido;
import projetoprincipiosdesign.src.entrega.TipoEntrega;
import projetoprincipiosdesign.src.pagamento.GeraBoleto;
import projetoprincipiosdesign.src.pagamento.Pagamento;
import projetoprincipiosdesign.persistencia.PedidoRepository;

import java.util.Map;

/**
 * SRP: o PedidoService apenas orquestra o fluxo do pedido (calcular,
 * entregar, pagar, persistir). Cada regra concreta vive na sua própria
 * classe/pacote.
 *
 * DIP: o serviço depende só das abstrações (Desconto, TipoEntrega,
 * Pagamento, PedidoRepository), nunca de implementações concretas.
 * Isso substitui o "extends PagamentoCartao" antigo, que era herança
 * usada apenas para reaproveitar código (quebrava LSP/SRP).
 */
public class PedidoService {
    private final Map<String, Desconto> descontosPorTipoCliente;
    private final Map<String, Pagamento> pagamentosPorForma;
    private final TipoEntrega tipoEntrega;
    private final PedidoRepository pedidoRepository;
    private final Desconto descontoPadrao = new DescontoPadrao();

    public PedidoService(
        Map<String, Desconto> descontosPorTipoCliente,
        Map<String, Pagamento> pagamentosPorForma,
        TipoEntrega tipoEntrega,
        PedidoRepository pedidoRepository
    ) {
        this.descontosPorTipoCliente = descontosPorTipoCliente;
        this.pagamentosPorForma = pagamentosPorForma;
        this.tipoEntrega = tipoEntrega;
        this.pedidoRepository = pedidoRepository;
    }

    public double calcularTotal(Pedido pedido, String tipoCliente) {
        double subtotal = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            subtotal += item.getSubtotal();
        }

        Desconto desconto = descontosPorTipoCliente.getOrDefault(tipoCliente, descontoPadrao);
        return desconto.calcular(subtotal);
    }

    public String obterCidadeEntrega(Pedido pedido) {
        return pedido.getCliente().getCidadeEntrega();
    }

    public double calcularFrete(double total) {
        if (!tipoEntrega.isDisponivel(total)) {
            throw new IllegalStateException(
                "Modalidade de entrega indisponível para o valor deste pedido."
            );
        }

        return tipoEntrega.calcularFrete(total);
    }

    public void finalizarPedido(Pedido pedido, String tipoCliente, String formaPagamento) {
        double totalComDesconto = calcularTotal(pedido, tipoCliente);
        double frete = calcularFrete(totalComDesconto);
        double totalFinal = totalComDesconto + frete;

        pedido.setValorTotal(totalFinal);

        System.out.println("Salvando pedido...");
        pedidoRepository.salvar(pedido);

        System.out.println("Gerando resumo do pedido...");
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.printf("Subtotal com desconto: R$ %.2f%n", totalComDesconto);
        System.out.printf("Frete: R$ %.2f%n", frete);
        System.out.printf("Total: R$ %.2f%n", totalFinal);

        processarPagamento(formaPagamento, totalFinal);

        System.out.println(
            "Enviando mensagem para " + pedido.getCliente().getNome() + ": pedido finalizado."
        );
    }

    private void processarPagamento(String formaPagamento, double total) {
        Pagamento pagamento = pagamentosPorForma.get(formaPagamento);

        if (pagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento não suportada: " + formaPagamento);
        }

        if (pagamento instanceof GeraBoleto geradorBoleto) {
            geradorBoleto.gerarBoleto(total);
        } else {
            pagamento.pagar(total);
        }
    }
}
