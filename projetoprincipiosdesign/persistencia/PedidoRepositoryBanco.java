package projetoprincipiosdesign.persistencia;

import projetoprincipiosdesign.src.dominio.Pedido;

/**
 * Nota do professor: o roteiro pede apenas separar o salvamento; aqui
 * mostramos a evolução com Repository. Esta implementação simula uma
 * persistência em banco de dados (sem conexão real), só para provar
 * que trocar o destino dos dados não exige alterar o PedidoService.
 */
public class PedidoRepositoryBanco implements PedidoRepository {
    @Override
    public void salvar(Pedido pedido) {
        System.out.printf(
            "[BANCO] Pedido de %s salvo com total R$ %.2f%n",
            pedido.getCliente().getNome(),
            pedido.getValorTotal()
        );
    }
}
