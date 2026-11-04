package snackflow.model;

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
