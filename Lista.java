package estruturas;

import modelos.Atendimento;

/**
 * LISTA simples baseada em array, com capacidade fixa.
 *
 * Diferente da fila e da pilha, aqui se pode acessar e remover QUALQUER posição.
 * Neste sistema ela guarda o REGISTRO GERAL de todos os atendimentos já criados
 * (aguardando, concluídos ou cancelados) e alimenta os relatórios recursivos.
 *
 * Como o array não pode ter "buracos", ao remover um elemento os seguintes são
 * deslocados uma posição para a esquerda.
 */
public class Lista {
    private final Atendimento[] dados;
    private int tamanho;

    public Lista(int capacidade) {
        this.dados = new Atendimento[capacidade];
        this.tamanho = 0;
    }

    /** Adiciona no final. Devolve false se a lista estiver cheia. */
    public boolean adicionar(Atendimento atendimento) {
        if (estaCheia()) {
            return false;
        }
        dados[tamanho] = atendimento;
        tamanho++;
        return true;
    }

    /** Remove o elemento da posição indicada, deslocando os demais. Null se índice inválido. */
    public Atendimento remover(int indice) {
        if (indice < 0 || indice >= tamanho) {
            return null;
        }
        Atendimento removido = dados[indice];
        for (int i = indice; i < tamanho - 1; i++) {
            dados[i] = dados[i + 1];
        }
        dados[tamanho - 1] = null;
        tamanho--;
        return removido;
    }

    /** Devolve o elemento da posição indicada; null se o índice for inválido. */
    public Atendimento obter(int indice) {
        if (indice < 0 || indice >= tamanho) {
            return null;
        }
        return dados[indice];
    }

    /** Busca linear pelo nome do cliente (sem diferenciar maiúsculas). Devolve o índice ou -1. */
    public int buscarPorNomeCliente(String nome) {
        for (int i = 0; i < tamanho; i++) {
            if (dados[i].getCliente().getNome().equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return -1;
    }

    /** Percorre a lista imprimindo cada atendimento. */
    public void percorrer() {
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ") " + dados[i]);
        }
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public boolean estaCheia() {
        return tamanho == dados.length;
    }

    public int tamanho() {
        return tamanho;
    }
}
