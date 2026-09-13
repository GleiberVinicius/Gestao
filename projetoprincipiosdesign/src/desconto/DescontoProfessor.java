package projetoprincipiosdesign.src.desconto;

public class DescontoProfessor implements Desconto {
    private static final double PERCENTUAL_DESCONTO = 0.15;

    @Override
    public double calcular(double total) {
        return total * (1 - PERCENTUAL_DESCONTO);
    }
}
