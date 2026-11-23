package br.edu.exemplo;

public class ContaBancaria {
    private double saldo;

    public ContaBancaria() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    /**
     * Deposita um valor positivo. Valores zero ou negativos são ignorados.
     *
     * @return true se o depósito foi realizado
     */
    public boolean depositar(double valor) {
        if (valor <= 0) {
            return false;
        }
        saldo += valor;
        return true;
    }

    /**
     * Saca um valor positivo que não ultrapasse o saldo.
     * Operações inválidas são ignoradas e não alteram o saldo.
     *
     * @return true se o saque foi realizado
     */
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            return false;
        }
        saldo -= valor;
        return true;
    }
}
