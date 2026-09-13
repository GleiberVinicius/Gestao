package projetoprincipiosdesign.persistencia;

import projetoprincipiosdesign.src.dominio.Pedido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PedidoRepositoryArquivo implements PedidoRepository {
    private static final Path ARQUIVO_PEDIDOS = Path.of("src/pedidos.txt");

    @Override
    public void salvar(Pedido pedido) {
        String linha = pedido.getCliente().getNome() + ";" + pedido.getValorTotal()
            + System.lineSeparator();

        try {
            Files.writeString(
                ARQUIVO_PEDIDOS,
                linha,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o pedido em arquivo.", e);
        }
    }
}
