package snackflow.model;

/**
 * Representa um produto escolhido pelo cliente, junto com a quantidade desejada
 * e uma observação opcional (ex.: "sem cebola").
 */
public class ItemPedido {

    public static final int QUANTIDADE_MAXIMA = 20;
    public static final int TAMANHO_MAXIMO_OBSERVACAO = 60;

    private Produto produto;
    private int quantidade;
    private String observacao;

    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Selecione um produto válido.");
        }
        this.produto = produto;
        this.observacao = "";
        setQuantidade(quantidade);
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (quantidade > QUANTIDADE_MAXIMA) {
            throw new IllegalArgumentException("A quantidade máxima por produto é " + QUANTIDADE_MAXIMA + ".");
        }
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        // Observação vazia (ou nula) significa "sem observação"
        String texto = (observacao == null) ? "" : observacao.trim();
        if (texto.length() > TAMANHO_MAXIMO_OBSERVACAO) {
            throw new IllegalArgumentException(
                    "A observação pode ter no máximo " + TAMANHO_MAXIMO_OBSERVACAO + " caracteres.");
        }
        this.observacao = texto;
    }

    public boolean temObservacao() {
        return !observacao.isEmpty();
    }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }
}
