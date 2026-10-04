package estruturas;

import modelos.Atendimento;

/**
 * PILHA (Stack) implementada do zero, usando um array interno.
 *
 * Regra da pilha: LIFO (Last In, First Out) — o último a entrar é o primeiro a sair,
 * como uma pilha de pratos: só se mexe no prato de cima.
 *
 * Decisão de projeto: implementação PRÓPRIA (e não java.util.Stack) para mostrar
 * como a estrutura funciona por dentro. O array guarda os elementos e a variável
 * "topo" aponta para o índice do último elemento inserido (-1 quando está vazia).
 *
 * Usos neste sistema:
 *  - histórico de atendimentos concluídos (peek mostra o último atendimento);
 *  - pilha de cancelamentos, que permite DESFAZER o último cancelamento.
 */
public class Pilha {
    private final Atendimento[] dados;
    private int topo; // índice do elemento do topo; -1 = pilha vazia

    public Pilha(int capacidade) {
        this.dados = new Atendimento[capacidade]; // CRIAÇÃO do array com tamanho fixo
        this.topo = -1;
    }

    /** Empilhar (push). Devolve false se a pilha estiver cheia, em vez de lançar exceção. */
    public boolean empilhar(Atendimento atendimento) {
        if (estaCheia()) {
            return false;
        }
        topo++;
        dados[topo] = atendimento;
        return true;
    }

    /** Desempilhar (pop). Remove e devolve o topo; devolve null se estiver vazia. */
    public Atendimento desempilhar() {
        if (estaVazia()) {
            return null;
        }
        Atendimento removido = dados[topo];
        dados[topo] = null; // solta a referência para o objeto poder ser coletado
        topo--;
        return removido;
    }

    /** Consultar o topo (peek). Devolve o topo SEM removê-lo; null se estiver vazia. */
    public Atendimento consultarTopo() {
        if (estaVazia()) {
            return null;
        }
        return dados[topo];
    }

    public boolean estaVazia() {
        return topo == -1;
    }

    public boolean estaCheia() {
        return topo == dados.length - 1;
    }

    public int tamanho() {
        return topo + 1;
    }

    /**
     * Cópia dos elementos do topo para a base (ordem em que seriam desempilhados).
     * Serve para exibir a pilha sem destruí-la.
     */
    public Atendimento[] paraArray() {
        Atendimento[] copia = new Atendimento[tamanho()];
        for (int i = 0; i < copia.length; i++) {
            copia[i] = dados[topo - i];
        }
        return copia;
    }
}
