package util;

import estruturas.Lista;
import modelos.Atendimento;
import modelos.StatusAtendimento;

/**
 * Métodos RECURSIVOS do projeto.
 * Todo método recursivo tem: (1) um CASO BASE, que encerra a recursão, e
 * (2) um CASO RECURSIVO, que chama a si mesmo com um problema menor.
 */
public class Recursao {

    /**
     * BUSCA BINÁRIA recursiva por nome do cliente.
     * PRÉ-CONDIÇÃO: o array precisa estar ORDENADO por nome (bubbleSortPorNome).
     * A cada chamada descarta metade do array. Custo: O(log n). Devolve o índice ou -1.
     */
    public static int buscaBinaria(Atendimento[] vetor, String nome, int inicio, int fim) {
        if (inicio > fim) {
            return -1; // caso base 1: intervalo vazio, não encontrou
        }
        int meio = (inicio + fim) / 2;
        int comparacao = nome.compareToIgnoreCase(vetor[meio].getCliente().getNome());
        if (comparacao == 0) {
            return meio; // caso base 2: encontrou
        }
        if (comparacao < 0) {
            return buscaBinaria(vetor, nome, inicio, meio - 1); // procura na metade esquerda
        }
        return buscaBinaria(vetor, nome, meio + 1, fim); // procura na metade direita
    }

    /**
     * Conta recursivamente quantos atendimentos da lista têm o status informado.
     * Começa a chamada com indice = 0.
     */
    public static int contarPorStatus(Lista lista, int indice, StatusAtendimento status) {
        if (indice >= lista.tamanho()) {
            return 0; // caso base: passou do último elemento
        }
        int contaEste = (lista.obter(indice).getStatus() == status) ? 1 : 0;
        return contaEste + contarPorStatus(lista, indice + 1, status);
    }

    /**
     * Soma recursivamente o valor dos procedimentos dos atendimentos com o status informado.
     * Com status CONCLUIDO, obtém o faturamento da clínica. Começa a chamada com indice = 0.
     */
    public static double somarValorPorStatus(Lista lista, int indice, StatusAtendimento status) {
        if (indice >= lista.tamanho()) {
            return 0.0; // caso base
        }
        Atendimento atual = lista.obter(indice);
        double valorEste = (atual.getStatus() == status) ? atual.getProcedimento().getValor() : 0.0;
        return valorEste + somarValorPorStatus(lista, indice + 1, status);
    }
}
