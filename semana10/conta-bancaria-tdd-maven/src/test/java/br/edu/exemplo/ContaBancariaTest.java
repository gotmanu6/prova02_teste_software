package br.edu.exemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContaBancariaTest {

    // ---------- Testes fornecidos pelo professor (não alterados) ----------

    @Test
    void deveDepositarValorNaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void deveAcumularDepositos() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(50);
        assertEquals(150, conta.getSaldo(), 0.001);
    }

    @Test
    void deveSacarValorDaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(40);
        assertEquals(60, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueMaiorQueSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(150);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    // ---------- Testes adicionais ----------

    @Test
    void contaDeveIniciarComSaldoZero() {
        ContaBancaria conta = new ContaBancaria();
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void depositoValidoDeveRetornarVerdadeiro() {
        ContaBancaria conta = new ContaBancaria();
        assertTrue(conta.depositar(10));
    }

    @Test
    void naoDevePermitirDepositoNegativo() {
        ContaBancaria conta = new ContaBancaria();
        assertFalse(conta.depositar(-50));
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirDepositoDeZero() {
        ContaBancaria conta = new ContaBancaria();
        assertFalse(conta.depositar(0));
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void devePermitirSacarTodoOSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertTrue(conta.sacar(100));
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void saqueMaiorQueSaldoDeveRetornarFalso() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertFalse(conta.sacar(100.01));
    }

    @Test
    void naoDevePermitirSaqueNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertFalse(conta.sacar(-20));
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueDeZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertFalse(conta.sacar(0));
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueComContaZerada() {
        ContaBancaria conta = new ContaBancaria();
        assertFalse(conta.sacar(1));
        assertEquals(0, conta.getSaldo(), 0.001);
    }
}
