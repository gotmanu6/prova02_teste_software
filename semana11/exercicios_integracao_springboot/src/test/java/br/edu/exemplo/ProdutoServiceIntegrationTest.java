package br.edu.exemplo;

import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercício 1 — Integração entre Service, Repository e banco H2.
 * Nenhum componente é simulado (sem mocks): o ProdutoService real usa o
 * ProdutoRepository real, que grava no H2 em memória.
 */
@SpringBootTest
@Transactional
class ProdutoServiceIntegrationTest {

    @Autowired
    ProdutoService service;

    @Autowired
    ProdutoRepository repository;

    @Test
    @DisplayName("CT01 - Cadastro válido é persistido e recuperado do repositório")
    void deveCadastrarERecuperarProduto() {
        Produto salvo = service.cadastrar("Teclado", 150.0);

        assertNotNull(salvo.getId(), "O produto deve receber um ID gerado pelo banco");

        Produto doBanco = repository.findById(salvo.getId()).orElseThrow();
        assertEquals("Teclado", doBanco.getNome());
        assertEquals(150.0, doBanco.getPreco(), 0.001);
    }

    @Test
    @DisplayName("CT02 - Nome em branco lança exceção e nada é gravado")
    void naoDevePersistirProdutoComNomeEmBranco() {
        long antes = repository.count();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("   ", 80.0));

        assertEquals("Nome obrigatório", erro.getMessage());
        assertEquals(antes, repository.count(), "A contagem de registros não pode aumentar");
    }

    @Test
    @DisplayName("CT03 - Atualização de preço é persistida no banco (desafio)")
    void deveAtualizarPrecoPersistido() {
        Produto mouse = service.cadastrar("Mouse", 80.0);

        service.atualizarPreco(mouse.getId(), 95.0);

        Produto doBanco = repository.findById(mouse.getId()).orElseThrow();
        assertEquals(95.0, doBanco.getPreco(), 0.001);
        assertEquals("Mouse", doBanco.getNome());
    }

    // ---------- Casos complementares ----------

    @Test
    @DisplayName("CT06 - Preço zero ou negativo lança exceção e nada é gravado")
    void naoDevePersistirProdutoComPrecoInvalido() {
        long antes = repository.count();

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar("Monitor", 0.0));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrar("Monitor", -10.0));

        assertEquals(antes, repository.count());
    }

    @Test
    @DisplayName("CT07 - Atualização com preço inválido não altera o valor gravado")
    void naoDeveAtualizarParaPrecoInvalido() {
        Produto mouse = service.cadastrar("Mouse", 80.0);

        assertThrows(IllegalArgumentException.class, () -> service.atualizarPreco(mouse.getId(), -5.0));

        assertEquals(80.0, repository.findById(mouse.getId()).orElseThrow().getPreco(), 0.001);
    }

    @Test
    @DisplayName("CT08 - Buscar ID inexistente lança exceção")
    void deveFalharAoBuscarProdutoInexistente() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.buscar(999_999L));

        assertEquals("Produto não encontrado", erro.getMessage());
    }
}
