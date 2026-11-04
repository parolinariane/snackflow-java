package snackflow.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Pedido {

    private ArrayList<ItemPedido> itens;
    private double valorTotal;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void adicionarItem(ItemPedido novoItem) {
        if (novoItem == null || novoItem.getProduto() == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item inválido.");
        }
        if (novoItem.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemPedido existente = buscarItemPorProduto(novoItem.getProduto());
        if (existente != null) {
            existente.setQuantidade(existente.getQuantidade() + novoItem.getQuantidade());
        } else {
            itens.add(novoItem);
        }
        calcularTotal();
    }

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

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public double getValorTotal() {
        return valorTotal;
    }

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
