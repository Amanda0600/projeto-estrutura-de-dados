package util;

import estruturas.Fila;
import estruturas.Lista;
import estruturas.Pilha;
import modelos.Atendimento;
import modelos.Cliente;
import modelos.StatusAtendimento;
import modelos.TipoProcedimento;

/**
 * Regras de negócio da clínica. É aqui que Array, Enum e Pilha trabalham juntos:
 *  - ENUM:   status do atendimento e tipo de procedimento;
 *  - ARRAY:  fila, pilhas e lista são arrays por dentro; busca e ordenação usam arrays;
 *  - PILHA:  historico (concluídos) e cancelados (permite desfazer).
 *
 * Cada método público corresponde a uma opção do menu e faz UMA coisa.
 */
public class ClinicaServico {
    private static final int CAPACIDADE_FILA = 10;
    private static final int CAPACIDADE_PILHA = 20;
    private static final int CAPACIDADE_REGISTRO = 100;

    private final Fila filaEspera = new Fila(CAPACIDADE_FILA);
    private final Pilha historico = new Pilha(CAPACIDADE_PILHA);   // atendimentos concluídos
    private final Pilha cancelados = new Pilha(CAPACIDADE_PILHA);  // base do "desfazer cancelamento"
    private final Lista registro = new Lista(CAPACIDADE_REGISTRO); // todos os atendimentos já criados
    private Atendimento atual = null;                              // quem está sendo atendido agora

    // ---------- 1. cadastrar ----------
    public void cadastrarCliente() {
        if (filaEspera.estaCheia()) {
            System.out.println("Fila cheia (" + CAPACIDADE_FILA + " pessoas). Chame o próximo cliente antes de cadastrar outro.");
            return;
        }
        if (registro.estaCheia()) {
            System.out.println("O registro de atendimentos do dia está cheio.");
            return;
        }
        String nome = Entrada.lerTexto("Nome do cliente: ");
        String telefone = Entrada.lerTelefone("Telefone: ");
        TipoProcedimento procedimento = Entrada.lerProcedimento();

        Atendimento novo = new Atendimento(new Cliente(nome, telefone), procedimento);
        filaEspera.enfileirar(novo);
        registro.adicionar(novo);
        System.out.println("Cadastrado! " + novo);
    }

    // ---------- 2. chamar próximo ----------
    public void chamarProximo() {
        if (atual != null) {
            System.out.println("Já existe um atendimento em andamento. Conclua-o antes de chamar o próximo:");
            System.out.println("  " + atual);
            return;
        }
        Atendimento proximo = filaEspera.desenfileirar();
        if (proximo == null) {
            System.out.println("A fila de espera está vazia.");
            return;
        }
        proximo.setStatus(StatusAtendimento.EM_ATENDIMENTO);
        atual = proximo;
        System.out.println("Chamando: " + proximo);
    }

    // ---------- 3. concluir ----------
    public void concluirAtendimento() {
        if (atual == null) {
            System.out.println("Não há atendimento em andamento.");
            return;
        }
        if (historico.estaCheia()) {
            System.out.println("O histórico está cheio (" + CAPACIDADE_PILHA + " atendimentos); não é possível concluir mais.");
            return;
        }
        atual.setStatus(StatusAtendimento.CONCLUIDO);
        historico.empilhar(atual);
        System.out.println("Atendimento concluído: " + atual);
        atual = null;
    }

    // ---------- 4. listar ----------
    public void listarFila() {
        Atendimento[] fila = filaEspera.paraArray();
        if (fila.length == 0) {
            System.out.println("A fila de espera está vazia.");
            return;
        }
        System.out.println("Fila de espera (" + fila.length + "/" + CAPACIDADE_FILA + "):");
        OperacoesArray.percorrer(fila);
    }

    // ---------- 5. busca linear ----------
    public void buscarLinear() {
        if (filaEspera.estaVazia()) {
            System.out.println("A fila de espera está vazia.");
            return;
        }
        String nome = Entrada.lerTexto("Nome a buscar: ");
        Atendimento[] fila = filaEspera.paraArray();
        int posicao = OperacoesArray.buscaLinearPorNome(fila, nome);
        mostrarResultadoBusca(fila, posicao, nome);
    }

    // ---------- 6. busca binária ----------
    public void buscarBinaria() {
        if (filaEspera.estaVazia()) {
            System.out.println("A fila de espera está vazia.");
            return;
        }
        String nome = Entrada.lerTexto("Nome a buscar: ");
        // A busca binária exige array ordenado. Ordenamos uma CÓPIA para não alterar a ordem de chegada.
        Atendimento[] copia = OperacoesArray.copiar(filaEspera.paraArray());
        OperacoesArray.bubbleSortPorNome(copia);
        int posicao = Recursao.buscaBinaria(copia, nome, 0, copia.length - 1);
        mostrarResultadoBusca(copia, posicao, nome);
    }

    private void mostrarResultadoBusca(Atendimento[] vetor, int posicao, String nome) {
        if (posicao == -1) {
            System.out.println("Nenhum cliente chamado \"" + nome + "\" na fila.");
        } else {
            System.out.println("Encontrado: " + vetor[posicao]);
        }
    }

    // ---------- 7. ordenar ----------
    public void ordenarFila() {
        if (filaEspera.tamanho() < 2) {
            System.out.println("É preciso ter ao menos 2 pessoas na fila para ordenar.");
            return;
        }
        System.out.println("Ordenar por: 1) Nome do cliente  2) Tipo de procedimento");
        int criterio = Entrada.lerInteiro("Critério: ", 1, 2);

        Atendimento[] vetor = filaEspera.paraArray();
        if (criterio == 1) {
            OperacoesArray.bubbleSortPorNome(vetor);
        } else {
            OperacoesArray.selectionSortPorProcedimento(vetor);
        }
        // Recarrega a fila com a nova ordem (atenção: a ordem de chegada original é substituída).
        filaEspera.limpar();
        for (int i = 0; i < vetor.length; i++) {
            filaEspera.enfileirar(vetor[i]);
        }
        System.out.println("Fila reordenada:");
        OperacoesArray.percorrer(vetor);
    }

    // ---------- 8. cancelar ----------
    public void cancelarAtendimento() {
        if (filaEspera.estaVazia()) {
            System.out.println("A fila de espera está vazia; não há o que cancelar.");
            return;
        }
        if (cancelados.estaCheia()) {
            System.out.println("A pilha de cancelamentos está cheia; não é possível cancelar mais.");
            return;
        }
        listarFila();
        int numero = Entrada.lerInteiro("Número do cliente a cancelar (0 para voltar): ", 0, filaEspera.tamanho());
        if (numero == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
        Atendimento removido = filaEspera.removerEm(numero - 1);
        removido.setStatus(StatusAtendimento.CANCELADO);
        cancelados.empilhar(removido); // guarda para um possível "desfazer"
        System.out.println("Atendimento cancelado: " + removido);
    }

    // ---------- 9. desfazer cancelamento ----------
    public void desfazerCancelamento() {
        if (cancelados.estaVazia()) {
            System.out.println("Não há cancelamentos para desfazer.");
            return;
        }
        if (filaEspera.estaCheia()) {
            System.out.println("Fila cheia: não é possível reinserir o cliente agora.");
            return;
        }
        Atendimento restaurado = cancelados.desempilhar(); // o último cancelado é o primeiro a voltar (LIFO)
        restaurado.setStatus(StatusAtendimento.AGUARDANDO);
        filaEspera.enfileirar(restaurado); // volta para o FIM da fila
        System.out.println("Cancelamento desfeito (cliente voltou ao fim da fila): " + restaurado);
    }

    // ---------- 10. histórico ----------
    public void mostrarHistorico() {
        Atendimento[] concluidos = historico.paraArray();
        if (concluidos.length == 0) {
            System.out.println("Nenhum atendimento concluído ainda.");
            return;
        }
        System.out.println("Histórico (do mais recente para o mais antigo):");
        OperacoesArray.percorrer(concluidos);
    }

    // ---------- 11. último atendimento (peek) ----------
    public void mostrarUltimoAtendimento() {
        Atendimento ultimo = historico.consultarTopo();
        if (ultimo == null) {
            System.out.println("Nenhum atendimento concluído ainda.");
        } else {
            System.out.println("Último atendimento concluído: " + ultimo);
        }
    }

    // ---------- 12. relatório ----------
    public void mostrarRelatorio() {
        if (registro.estaVazia()) {
            System.out.println("Ainda não há atendimentos registrados.");
            return;
        }
        System.out.println("Total de atendimentos registrados: " + registro.tamanho());
        StatusAtendimento[] todosStatus = StatusAtendimento.values();
        for (int i = 0; i < todosStatus.length; i++) {
            int quantidade = Recursao.contarPorStatus(registro, 0, todosStatus[i]);
            System.out.println("  " + todosStatus[i].getDescricao() + ": " + quantidade);
        }
        double faturamento = Recursao.somarValorPorStatus(registro, 0, StatusAtendimento.CONCLUIDO);
        System.out.printf("Faturamento dos atendimentos concluídos: R$ %.2f%n", faturamento);
    }
}
