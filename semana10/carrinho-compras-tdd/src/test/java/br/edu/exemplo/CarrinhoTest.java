package br.edu.exemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Testes escritos em ordem, um requisito por vez (ciclo RED → GREEN → REFACTOR),
 * conforme REQUISITOS.md.
 */
class CarrinhoTest {

    private static final double DELTA = 0.001;

    // ---------- Requisito 1 — Carrinho vazio ----------

    @Test
    void carrinhoVazioDeveTerTotalZero() {
        Carrinho carrinho = new Carrinho();

        assertEquals(0.0, carrinho.getTotal(), DELTA);
        assertEquals(0, carrinho.getQuantidadeDeItens());
    }

    // ---------- Requisito 2 — Adicionar um produto ----------

    @Test
    void deveAdicionarUmProduto() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
        assertEquals(1, carrinho.getQuantidadeDeItens());
    }

    // ---------- Requisito 3 — Adicionar vários produtos ----------

    @Test
    void deveSomarVariosProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));
        carrinho.adicionar(new Produto("Mouse", 50.00));

        assertEquals(150.00, carrinho.getTotal(), DELTA);
        assertEquals(2, carrinho.getQuantidadeDeItens());
    }

    @Test
    void deveAceitarProdutoGratuito() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Brinde", 0.00));

        assertEquals(0.00, carrinho.getTotal(), DELTA);
        assertEquals(1, carrinho.getQuantidadeDeItens());
    }

    // ---------- Requisito 4 — Remover produto ----------

    @Test
    void deveRemoverProduto() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));
        carrinho.adicionar(new Produto("Mouse", 50.00));

        assertTrue(carrinho.remover("Mouse"));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
        assertEquals(1, carrinho.getQuantidadeDeItens());
        assertEquals("Teclado", carrinho.getProdutos().get(0).getNome());
    }

    @Test
    void removerProdutoInexistenteNaoAlteraOCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertFalse(carrinho.remover("Monitor"));

        assertEquals(100.00, carrinho.getTotal(), DELTA);
    }

    @Test
    void removerDeCarrinhoVazioRetornaFalso() {
        assertFalse(new Carrinho().remover("Mouse"));
    }

    @Test
    void removerProdutoRepetidoRemoveApenasUmaUnidade() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Mouse", 50.00));
        carrinho.adicionar(new Produto("Mouse", 50.00));

        carrinho.remover("Mouse");

        assertEquals(50.00, carrinho.getTotal(), DELTA);
        assertEquals(1, carrinho.getQuantidadeDeItens());
    }

    // ---------- Requisito 5 — Produto com preço inválido ----------
    // Decisão: o carrinho lança IllegalArgumentException e não adiciona o produto.

    @Test
    void naoDeveAdicionarProdutoComPrecoNegativo() {
        Carrinho carrinho = new Carrinho();

        assertThrows(IllegalArgumentException.class,
                () -> carrinho.adicionar(new Produto("Teclado", -100.00)));

        assertEquals(0.0, carrinho.getTotal(), DELTA);
        assertEquals(0, carrinho.getQuantidadeDeItens());
    }

    @Test
    void naoDeveAdicionarProdutoNulo() {
        Carrinho carrinho = new Carrinho();

        assertThrows(IllegalArgumentException.class, () -> carrinho.adicionar(null));
        assertEquals(0, carrinho.getQuantidadeDeItens());
    }

    // ---------- Requisito 6 — Desconto de 10% a partir de R$ 500,00 ----------

    @Test
    void deveAplicarDescontoDe10PorCentoAcimaDe500() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 600.00));

        assertEquals(600.00, carrinho.getSubtotal(), DELTA);
        assertEquals(60.00, carrinho.getDesconto(), DELTA);
        assertEquals(540.00, carrinho.getTotal(), DELTA);
    }

    @Test
    void naoDeveAplicarDescontoEm499e99() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Cadeira", 499.99));

        assertEquals(0.0, carrinho.getDesconto(), DELTA);
        assertEquals(499.99, carrinho.getTotal(), DELTA);
    }

    @Test
    void deveAplicarDescontoExatamenteEm500() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Cadeira", 500.00));

        assertEquals(50.00, carrinho.getDesconto(), DELTA);
        assertEquals(450.00, carrinho.getTotal(), DELTA);
    }

    @Test
    void deveAplicarDescontoEm500e01() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Cadeira", 500.01));

        assertEquals(50.001, carrinho.getDesconto(), DELTA);
        assertEquals(450.009, carrinho.getTotal(), DELTA);
    }

    @Test
    void descontoDeveConsiderarASomaDosProdutos() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 300.00));
        carrinho.adicionar(new Produto("Mouse", 200.00));

        assertEquals(450.00, carrinho.getTotal(), DELTA);
    }

    @Test
    void descontoDeveSumirQuandoORemoverDeixaOSubtotalAbaixoDe500() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 300.00));
        carrinho.adicionar(new Produto("Mouse", 200.00));

        carrinho.remover("Mouse");

        assertEquals(0.0, carrinho.getDesconto(), DELTA);
        assertEquals(300.00, carrinho.getTotal(), DELTA);
    }

    // ---------- Encapsulamento ----------

    @Test
    void listaDeProdutosNaoPodeSerAlteradaPorFora() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertThrows(UnsupportedOperationException.class,
                () -> carrinho.getProdutos().add(new Produto("Mouse", 50.00)));
    }

    @Test
    void produtoDeveGuardarNomeEPreco() {
        Produto produto = new Produto("Teclado", 100.00);

        assertEquals("Teclado", produto.getNome());
        assertEquals(100.00, produto.getPreco(), DELTA);
    }
}
