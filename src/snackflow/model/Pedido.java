package snackflow.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa o pedido do cliente: uma lista de itens e o valor total.
 * Toda alteração nos itens passa por esta classe, garantindo que o total
 * esteja sempre atualizado.
 */
public class Pedido {

    private ArrayList<ItemPedido> itens;
    private double valorTotal;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
    }

    /**
     * Adiciona um item ao pedido. Se o produto já estiver no pedido,
     * apenas soma a quantidade ao item existente.
     */
    public void adicionarItem(ItemPedido novoItem) {
        if (novoItem == null || novoItem.getProduto() == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item inválido.");
        }
        if (novoItem.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemPedido existente = buscarItemPorProduto(novoItem.getProduto());
        if (existente != null) {
            // setQuantidade valida o limite máximo antes de alterar o item
            existente.setQuantidade(existente.getQuantidade() + novoItem.getQuantidade());
        } else {
            itens.add(novoItem);
        }
        calcularTotal();
    }

    /** Altera a quantidade de um item que já está no pedido. */
    public void alterarQuantidade(ItemPedido item, int novaQuantidade) {
        if (!itens.contains(item)) {
            throw new IllegalArgumentException("Este item não faz parte do pedido.");
        }
        item.setQuantidade(novaQuantidade);
        calcularTotal();
    }

    public boolean removerItem(ItemPedido item) {
        boolean removido = itens.remove(item);
        calcularTotal();
        return removido;
    }

    /** Remove todos os itens do pedido. */
    public void limparPedido() {
        itens.clear();
        calcularTotal();
    }

    public double calcularTotal() {
        double soma = 0;
        for (ItemPedido item : itens) {
            soma += item.calcularSubtotal();
        }
        valorTotal = soma;
        return valorTotal;
    }

    /**
     * Retorna os itens apenas para leitura. Assim, as telas não conseguem
     * alterar a lista diretamente e o total nunca fica desatualizado.
     */
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    /** Soma das quantidades de todos os itens (ex.: 2 hambúrgueres + 1 refri = 3). */
    public int getQuantidadeTotal() {
        int total = 0;
        for (ItemPedido item : itens) {
            total += item.getQuantidade();
        }
        return total;
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    private ItemPedido buscarItemPorProduto(Produto produto) {
        for (ItemPedido item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                return item;
            }
        }
        return null;
    }
}
