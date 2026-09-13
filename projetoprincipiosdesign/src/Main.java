package projetoprincipiosdesign.src;

import projetoprincipiosdesign.src.desconto.Desconto;
import projetoprincipiosdesign.src.desconto.DescontoAluno;
import projetoprincipiosdesign.src.desconto.DescontoFuncionario;
import projetoprincipiosdesign.src.desconto.DescontoProfessor;
import projetoprincipiosdesign.src.dominio.Cidade;
import projetoprincipiosdesign.src.dominio.Cliente;
import projetoprincipiosdesign.src.dominio.Endereco;
import projetoprincipiosdesign.src.dominio.ItemPedido;
import projetoprincipiosdesign.src.dominio.Pedido;
import projetoprincipiosdesign.src.entrega.EntregaDomicilio;
import projetoprincipiosdesign.src.pagamento.Pagamento;
import projetoprincipiosdesign.src.pagamento.PagamentoBoleto;
import projetoprincipiosdesign.src.pagamento.PagamentoCartao;
import projetoprincipiosdesign.src.pagamento.PagamentoPix;
import projetoprincipiosdesign.persistencia.PedidoRepositoryArquivo;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== LOJA ACADÊMICA ===");

        Cliente cliente = new Cliente(
            "Ana",
            new Endereco(
                "Rua das Flores",
                "123",
                "Centro",
                new Cidade("Belo Horizonte", "MG")
            )
        );

        Pedido pedido = new Pedido(
            cliente,
            List.of(
                new ItemPedido("Livro de Engenharia de Software", 120.0, 1),
                new ItemPedido("Caderno", 20.0, 2)
            )
        );

        // "Injeção de dependência" manual: o Main é o único lugar que
        // conhece as implementações concretas. O PedidoService só
        // enxerga as interfaces (DIP).
        Map<String, Desconto> descontos = Map.of(
            "ALUNO", new DescontoAluno(),
            "PROFESSOR", new DescontoProfessor(),
            "FUNCIONARIO", new DescontoFuncionario()
        );

        Map<String, Pagamento> pagamentos = Map.of(
            "CARTAO", new PagamentoCartao(),
            "PIX", new PagamentoPix(),
            "BOLETO", new PagamentoBoleto()
        );

        PedidoService servico = new PedidoService(
            descontos,
            pagamentos,
            new EntregaDomicilio(),
            new PedidoRepositoryArquivo()
        );

        System.out.println();
        System.out.println("Cidade de entrega:");
        System.out.println(servico.obterCidadeEntrega(pedido));

        System.out.println();
        System.out.println("Total com desconto:");
        System.out.printf("R$ %.2f%n", servico.calcularTotal(pedido, "ALUNO"));

        System.out.println();
        System.out.println("Finalizando pedido:");
        servico.finalizarPedido(pedido, "ALUNO", "CARTAO");

        System.out.println();
        System.out.println("Programa executado com sucesso.");

        // Exemplo (OCP): para aceitar PagamentoTransferencia, DescontoConvenio
        // ou um novo tipo de persistência, basta criar uma nova classe que
        // implemente a interface correspondente e adicioná-la aqui no Main —
        // nenhuma classe existente precisa ser alterada.
    }
}
