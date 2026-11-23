package br.edu.exemplo;

public class BubbleSort {

    private int[] numeros;

    public BubbleSort(int[] numeros) {
        this.numeros = numeros;
    }

    /**
     * Ordena o vetor em ordem crescente usando Bubble Sort.
     * A cada passada, o maior valor ainda desordenado "borbulha" para o fim.
     * Se uma passada inteira não fizer nenhuma troca, o vetor já está ordenado
     * e o algoritmo para mais cedo.
     */
    public void ordenar() {
        int n = numeros.length;
        for (int passada = 0; passada < n - 1; passada++) {
            boolean houveTroca = false;
            for (int i = 0; i < n - 1 - passada; i++) {
                if (numeros[i] > numeros[i + 1]) {
                    trocar(i, i + 1);
                    houveTroca = true;
                }
            }
            if (!houveTroca) {
                return;
            }
        }
    }

    private void trocar(int a, int b) {
        int temporario = numeros[a];
        numeros[a] = numeros[b];
        numeros[b] = temporario;
    }

    public int[] getNumeros() {
        return numeros;
    }
}
