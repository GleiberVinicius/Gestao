package projetoprincipiosdesign.src.desconto;

public class DescontoFuncionario implements Desconto {
    private static final double PERCENTUAL_DESCONTO = 0.20;

    @Override
    public double calcular(double total) {
        return total * (1 - PERCENTUAL_DESCONTO);
    }
}
