package snackflow.model;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private Categoria categoria;

    public Produto(int id, String nome, String descricao, double preco, Categoria categoria) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria do produto é obrigatória.");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return nome;
    }
}
