package projetoprincipiosdesign.src.desconto;

/**
 * Estratégia padrão (sem desconto), usada quando o tipo de cliente
 * não possui uma regra específica cadastrada. Não está no diagrama
 * original, mas evita "if/else" espalhado no PedidoService.
 */
public class DescontoPadrao implements Desconto {
    @Override
    public double calcular(double total) {
        return total;
    }
}
