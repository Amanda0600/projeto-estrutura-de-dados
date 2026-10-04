package modelos;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa UM atendimento: quem é o cliente, qual procedimento quer,
 * em que situação está (Enum) e a que horas chegou.
 *
 * É este objeto que circula pela Fila, pelas Pilhas e pela Lista.
 */
public class Atendimento {
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    // Contador compartilhado por todos os atendimentos: gera um número de senha sequencial.
    private static int proximoId = 1;

    private final int id;
    private final Cliente cliente;
    private final TipoProcedimento procedimento;
    private StatusAtendimento status;
    private final LocalTime horarioChegada;

    public Atendimento(Cliente cliente, TipoProcedimento procedimento) {
        this.id = proximoId++;
        this.cliente = cliente;
        this.procedimento = procedimento;
        this.status = StatusAtendimento.AGUARDANDO; // todo atendimento nasce aguardando
        this.horarioChegada = LocalTime.now();
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoProcedimento getProcedimento() {
        return procedimento;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    // O status é o único dado que muda durante a vida do atendimento.
    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }

    public LocalTime getHorarioChegada() {
        return horarioChegada;
    }

    @Override
    public String toString() {
        return String.format("Senha #%03d | %s | %s | chegada %s | %s",
                id,
                cliente.getNome(),
                procedimento.getNome(),
                horarioChegada.format(FORMATO_HORA),
                status.getDescricao());
    }
}
