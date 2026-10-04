package modelos;

/**
 * ENUM 1: representa o conjunto FIXO de situações em que um atendimento pode estar.
 *
 * Por que usar Enum e não String ou int?
 * - O compilador garante que só existam esses quatro valores (nada de "aguardano" digitado errado).
 * - O código fica legível: StatusAtendimento.CONCLUIDO em vez de um número mágico como 3.
 */
public enum StatusAtendimento {
    AGUARDANDO("Aguardando na fila"),
    EM_ATENDIMENTO("Em atendimento"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String descricao;

    // Construtor de enum é sempre privado: os valores são criados uma única vez, na declaração acima.
    StatusAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
