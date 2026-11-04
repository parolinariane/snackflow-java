package snackflow.model;

/**
 * Categorias do cardápio. Um enum garante que só existam estas opções.
 */
public enum Categoria {

    LANCHES("Lanches"),
    ACOMPANHAMENTOS("Acompanhamentos"),
    BEBIDAS("Bebidas"),
    SOBREMESAS("Sobremesas");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
