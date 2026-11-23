package br.edu.exemplo;

public class Produto {

    private String nome;
    private int quantidade;

    public Produto(String nome) {
        this.nome = nome;
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Adiciona unidades ao estoque. Quantidades zero ou negativas são ignoradas.
     *
     * @return true se a entrada foi registrada
     */
    public boolean adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        this.quantidade += quantidade;
        return true;
    }

    /**
     * Retira unidades do estoque. A operação é ignorada se a quantidade for
     * zero, negativa ou maior que o estoque disponível.
     *
     * @return true se a retirada foi realizada
     */
    public boolean retirarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > this.quantidade) {
            return false;
        }
        this.quantidade -= quantidade;
        return true;
    }
}
