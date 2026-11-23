package br.edu.exemplo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Carrinho de compras desenvolvido com TDD, seguindo os requisitos 1 a 6 de REQUISITOS.md.
 */
public class Carrinho {

    static final double VALOR_MINIMO_DESCONTO = 500.00;
    static final double PERCENTUAL_DESCONTO = 0.10;

    private final List<Produto> produtos = new ArrayList<>();

    /** Requisitos 2 e 5: adiciona um produto; produto nulo ou com preço negativo lança exceção. */
    public void adicionar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo");
        }
        produtos.add(produto);
    }

    /** Requisito 4: remove o primeiro produto com o nome informado. */
    public boolean remover(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equals(nome)) {
                produtos.remove(produto);
                return true;
            }
        }
        return false;
    }

    /** Requisitos 1 e 3: soma dos preços (sem desconto). */
    public double getSubtotal() {
        double soma = 0;
        for (Produto produto : produtos) {
            soma += produto.getPreco();
        }
        return soma;
    }

    /** Requisito 6: 10% de desconto quando o subtotal é igual ou superior a R$ 500,00. */
    public double getDesconto() {
        double subtotal = getSubtotal();
        if (subtotal >= VALOR_MINIMO_DESCONTO) {
            return subtotal * PERCENTUAL_DESCONTO;
        }
        return 0;
    }

    /** Valor final a pagar: subtotal menos o desconto. */
    public double getTotal() {
        return getSubtotal() - getDesconto();
    }

    public int getQuantidadeDeItens() {
        return produtos.size();
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
}
