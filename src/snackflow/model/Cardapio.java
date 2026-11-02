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
                "Pão brioche, carne bovina de 150 g, queijo cheddar e molho especial.", 15.00));
        produtos.add(new Produto(2, "Batata frita",
                "Porção crocante de batatas fritas, sequinhas e com sal na medida.", 12.00));
        produtos.add(new Produto(3, "Refrigerante",
                "Lata de 350 ml bem gelada. Sabores: cola, guaraná ou laranja.", 5.00));
        produtos.add(new Produto(4, "Milk-shake",
                "Cremoso, 400 ml, nos sabores chocolate, morango ou baunilha.", 14.00));
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
}
