package util;

import modelos.Atendimento;

/**
 * Operações MANUAIS com arrays de Atendimento: percurso, busca e ordenação simples.
 * Nada de Arrays.sort nem ArrayList: cada algoritmo foi escrito à mão para fins didáticos.
 *
 * Todos os métodos são estáticos e têm uma única responsabilidade (programação modular).
 */
public class OperacoesArray {

    /** CRIAÇÃO: devolve uma cópia independente do array (ordenar a cópia não afeta o original). */
    public static Atendimento[] copiar(Atendimento[] origem) {
        Atendimento[] copia = new Atendimento[origem.length];
        for (int i = 0; i < origem.length; i++) {
            copia[i] = origem[i];
        }
        return copia;
    }

    /** PERCURSO: imprime todos os elementos, numerados a partir de 1. */
    public static void percorrer(Atendimento[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.println("  " + (i + 1) + ") " + vetor[i]);
        }
    }

    /**
     * BUSCA LINEAR: examina posição por posição até achar o nome.
     * Funciona em array desordenado. Custo: O(n). Devolve o índice ou -1.
     */
    public static int buscaLinearPorNome(Atendimento[] vetor, String nome) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i].getCliente().getNome().equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ORDENAÇÃO 1 — BUBBLE SORT por nome do cliente (ordem alfabética).
     * Compara vizinhos e troca os que estão fora de ordem; a cada volta o maior
     * "borbulha" para o fim. Escolhido pela simplicidade; custo O(n²) é aceitável
     * porque a fila da clínica é pequena.
     */
    public static void bubbleSortPorNome(Atendimento[] vetor) {
        for (int volta = 0; volta < vetor.length - 1; volta++) {
            boolean trocou = false;
            for (int i = 0; i < vetor.length - 1 - volta; i++) {
                if (compararPorNome(vetor[i], vetor[i + 1]) > 0) {
                    trocar(vetor, i, i + 1);
                    trocou = true;
                }
            }
            if (!trocou) {
                return; // nenhuma troca nesta volta: já está ordenado
            }
        }
    }

    /**
     * ORDENAÇÃO 2 — SELECTION SORT por tipo de procedimento (nome do procedimento;
     * em caso de empate, desempata pelo nome do cliente).
     * A cada passo seleciona o menor elemento da parte não ordenada e o coloca na posição certa.
     */
    public static void selectionSortPorProcedimento(Atendimento[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                if (compararPorProcedimento(vetor[j], vetor[menor]) < 0) {
                    menor = j;
                }
            }
            if (menor != i) {
                trocar(vetor, i, menor);
            }
        }
    }

    // ---------- métodos auxiliares ----------

    /** Comparador usado na ordenação por nome E na busca binária (mesma regra nos dois). */
    public static int compararPorNome(Atendimento a, Atendimento b) {
        return a.getCliente().getNome().compareToIgnoreCase(b.getCliente().getNome());
    }

    private static int compararPorProcedimento(Atendimento a, Atendimento b) {
        int resultado = a.getProcedimento().getNome().compareToIgnoreCase(b.getProcedimento().getNome());
        if (resultado != 0) {
            return resultado;
        }
        return compararPorNome(a, b);
    }

    private static void trocar(Atendimento[] vetor, int i, int j) {
        Atendimento temporario = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temporario;
    }
}
