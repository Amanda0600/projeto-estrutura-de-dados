package estruturas;

import modelos.Atendimento;

/**
 * FILA (Queue) circular implementada com array.
 *
 * Regra da fila: FIFO (First In, First Out) — quem chega primeiro é atendido primeiro.
 *
 * Por que CIRCULAR?
 * Numa fila simples em array, cada desenfileirar deixaria um espaço vazio no começo
 * que nunca mais seria usado. Na versão circular, quando os índices chegam ao fim do
 * array voltam ao zero (operador %), reaproveitando as posições liberadas.
 *
 * Variáveis de controle:
 *  - inicio:  índice do primeiro elemento (o próximo a sair);
 *  - tamanho: quantidade de elementos guardados.
 * A posição de inserção é calculada: (inicio + tamanho) % capacidade.
 */
public class Fila {
    private final Atendimento[] dados;
    private int inicio;
    private int tamanho;

    public Fila(int capacidade) {
        this.dados = new Atendimento[capacidade];
        this.inicio = 0;
        this.tamanho = 0;
    }

    /** Enfileirar: insere no fim. Devolve false se a fila estiver cheia. */
    public boolean enfileirar(Atendimento atendimento) {
        if (estaCheia()) {
            return false;
        }
        int posicaoFim = (inicio + tamanho) % dados.length;
        dados[posicaoFim] = atendimento;
        tamanho++;
        return true;
    }

    /** Desenfileirar: remove e devolve o primeiro; null se a fila estiver vazia. */
    public Atendimento desenfileirar() {
        if (estaVazia()) {
            return null;
        }
        Atendimento removido = dados[inicio];
        dados[inicio] = null;
        inicio = (inicio + 1) % dados.length; // avança de forma circular
        tamanho--;
        return removido;
    }

    /** Consulta o primeiro da fila sem removê-lo; null se estiver vazia. */
    public Atendimento consultarPrimeiro() {
        if (estaVazia()) {
            return null;
        }
        return dados[inicio];
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

    /**
     * Cópia dos elementos na ordem da fila (do primeiro ao último).
     * Usada para listar, buscar e ordenar com as rotinas de array.
     */
    public Atendimento[] paraArray() {
        Atendimento[] copia = new Atendimento[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = dados[(inicio + i) % dados.length];
        }
        return copia;
    }

    /** Esvazia a fila. Necessário para recarregá-la depois de uma ordenação. */
    public void limpar() {
        for (int i = 0; i < dados.length; i++) {
            dados[i] = null;
        }
        inicio = 0;
        tamanho = 0;
    }

    /**
     * Operação EXTRA (fora do conceito puro de fila): remove quem está em uma posição
     * qualquer (0 = primeiro). Necessária porque um cliente pode desistir no meio da fila.
     * Solução simples: copia para array, reconstrói a fila sem o elemento cancelado.
     */
    public Atendimento removerEm(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            return null;
        }
        Atendimento[] copia = paraArray();
        Atendimento removido = copia[posicao];
        limpar();
        for (int i = 0; i < copia.length; i++) {
            if (i != posicao) {
                enfileirar(copia[i]);
            }
        }
        return removido;
    }
}
