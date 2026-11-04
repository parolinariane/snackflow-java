package snackflow.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Guarda os produtos oferecidos pela lanchonete.
 * Nesta etapa os produtos são cadastrados diretamente no código (em memória).
 */
public class Cardapio {

    private ArrayList<Produto> produtos;

    public Cardapio() {
        this.produtos = new ArrayList<>();
        carregarProdutosIniciais();
    }

    private void carregarProdutosIniciais() {
        produtos.add(new Produto(1, "Hambúrguer",
                "Pão brioche, carne bovina de 150 g, queijo cheddar e molho especial.", 15.00, Categoria.LANCHES));
        produtos.add(new Produto(2, "X-Bacon",
                "Carne de 150 g, bacon crocante, queijo prato e maionese da casa.", 19.00, Categoria.LANCHES));
        produtos.add(new Produto(3, "Batata frita",
                "Porção crocante de batatas fritas, sequinhas e com sal na medida.", 12.00,
                Categoria.ACOMPANHAMENTOS));
        produtos.add(new Produto(4, "Onion rings",
                "Anéis de cebola empanados e crocantes, com molho barbecue.", 13.00, Categoria.ACOMPANHAMENTOS));
        produtos.add(new Produto(5, "Refrigerante",
                "Lata de 350 ml bem gelada. Sabores: cola, guaraná ou laranja.", 5.00, Categoria.BEBIDAS));
        produtos.add(new Produto(6, "Suco natural",
                "Copo de 400 ml feito na hora. Sabores: laranja, limão ou maracujá.", 8.00, Categoria.BEBIDAS));
        produtos.add(new Produto(7, "Milk-shake",
                "Cremoso, 400 ml, nos sabores chocolate, morango ou baunilha.", 14.00, Categoria.SOBREMESAS));
        produtos.add(new Produto(8, "Brownie",
                "Brownie de chocolate quentinho com uma bola de sorvete de creme.", 10.00, Categoria.SOBREMESAS));
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    /** Retorna somente os produtos da categoria informada. */
    public List<Produto> buscarPorCategoria(Categoria categoria) {
        ArrayList<Produto> encontrados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria() == categoria) {
                encontrados.add(produto);
            }
        }
        return encontrados;
    }
}
