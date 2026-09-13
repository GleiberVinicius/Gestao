package projetoprincipiosdesign.persistencia;

import projetoprincipiosdesign.src.dominio.Pedido;

public interface PedidoRepository {
    void salvar(Pedido pedido);
}
