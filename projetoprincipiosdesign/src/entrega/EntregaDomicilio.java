package projetoprincipiosdesign.src.entrega;

public class EntregaDomicilio implements TipoEntrega {
    private static final double VALOR_FRETE = 15.0;

    @Override
    public double calcularFrete(double total) {
        return VALOR_FRETE;
    }
}
