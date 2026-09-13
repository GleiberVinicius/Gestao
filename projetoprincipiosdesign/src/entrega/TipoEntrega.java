package projetoprincipiosdesign.src.entrega;

public interface TipoEntrega {
    double calcularFrete(double total);

    /**
     * LSP: toda implementação precisa responder de forma previsível se
     * está disponível para o pedido, em vez de lançar exceção dentro de
     * calcularFrete() (como a antiga EntregaRetiradaLoja fazia).
     */
    default boolean isDisponivel(double total) {
        return true;
    }
}
