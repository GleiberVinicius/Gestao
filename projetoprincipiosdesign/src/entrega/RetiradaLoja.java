package projetoprincipiosdesign.src.entrega;

public class RetiradaLoja implements TipoEntrega {
    private static final double VALOR_MINIMO_PEDIDO = 50.0;

    @Override
    public double calcularFrete(double total) {
        return 0.0;
    }

    @Override
    public boolean isDisponivel(double total) {
        return total >= VALOR_MINIMO_PEDIDO;
    }
}
