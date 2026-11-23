package br.edu.exemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoTest {

    // ---------- Requisitos 1 a 6 (testes fornecidos, não alterados) ----------

    @Test
    void produtoDeveIniciarComEstoqueZero() {
        Produto produto = new Produto("Teclado");

        assertEquals(0, produto.getQuantidade());
    }

    @Test
    void deveAdicionarProdutoAoEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void deveAcumularQuantidadeEmEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.adicionarEstoque(5);

        assertEquals(15, produto.getQuantidade());
    }

    @Test
    void deveRetirarProdutoDoEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(3);

        assertEquals(7, produto.getQuantidade());
    }

    @Test
    void naoDeveRetirarQuantidadeMaiorQueEstoque() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(10);
        produto.retirarEstoque(15);

        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void naoDeveAdicionarQuantidadeNegativa() {
        Produto produto = new Produto("Teclado");

        produto.adicionarEstoque(-10);

        assertEquals(0, produto.getQuantidade());
    }

    // ---------- Testes adicionais ----------

    @Test
    void deveGuardarONomeDoProduto() {
        Produto produto = new Produto("Teclado");

        assertEquals("Teclado", produto.getNome());
    }

    @Test
    void naoDeveAdicionarQuantidadeZero() {
        Produto produto = new Produto("Teclado");

        assertFalse(produto.adicionarEstoque(0));
        assertEquals(0, produto.getQuantidade());
    }

    @Test
    void entradaValidaDeveRetornarVerdadeiro() {
        Produto produto = new Produto("Teclado");

        assertTrue(produto.adicionarEstoque(1));
    }

    @Test
    void devePermitirRetirarTodoOEstoque() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        assertTrue(produto.retirarEstoque(10));
        assertEquals(0, produto.getQuantidade());
    }

    @Test
    void retiradaMaiorQueEstoqueDeveRetornarFalso() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        assertFalse(produto.retirarEstoque(11));
    }

    @Test
    void naoDeveRetirarQuantidadeNegativa() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        assertFalse(produto.retirarEstoque(-5));
        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void naoDeveRetirarQuantidadeZero() {
        Produto produto = new Produto("Teclado");
        produto.adicionarEstoque(10);

        assertFalse(produto.retirarEstoque(0));
        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void naoDeveRetirarDeEstoqueVazio() {
        Produto produto = new Produto("Teclado");

        assertFalse(produto.retirarEstoque(1));
        assertEquals(0, produto.getQuantidade());
    }
}
