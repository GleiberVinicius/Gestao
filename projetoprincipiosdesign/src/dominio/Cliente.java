package projetoprincipiosdesign.src.dominio;

public class Cliente {
    private Long id;
    private String nome;
    private Endereco endereco;

    public Cliente(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * Lei de Demeter: o Cliente expõe diretamente a cidade de entrega,
     * evitando que outras classes precisem "cavar" através de
     * cliente.getEndereco().getCidade().getNome().
     */
    public String getCidadeEntrega() {
        return endereco.getCidade().getNome();
    }
}
