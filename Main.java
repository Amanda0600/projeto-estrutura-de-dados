import util.ClinicaServico;
import util.Entrada;

/**
 * Ponto de entrada do programa: contém SOMENTE o menu e o despacho das opções.
 * Toda a lógica está em ClinicaServico (regras), estruturas/ (Pilha, Fila, Lista) e util/.
 */
public class Main {

    public static void main(String[] args) {
        ClinicaServico clinica = new ClinicaServico();
        int opcao;
        do {
            exibirMenu();
            opcao = Entrada.lerInteiro("Escolha uma opção: ", 0, 12);
            System.out.println();
            executarOpcao(opcao, clinica);
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("===== CLÍNICA DE ESTÉTICA — FILA DE ATENDIMENTO =====");
        System.out.println(" 1) Cadastrar cliente na fila");
        System.out.println(" 2) Chamar próximo cliente");
        System.out.println(" 3) Concluir atendimento atual");
        System.out.println(" 4) Listar fila de espera");
        System.out.println(" 5) Buscar cliente por nome (busca linear)");
        System.out.println(" 6) Buscar cliente por nome (busca binária)");
        System.out.println(" 7) Ordenar fila (por nome ou procedimento)");
        System.out.println(" 8) Cancelar atendimento");
        System.out.println(" 9) Desfazer último cancelamento");
        System.out.println("10) Ver histórico de atendimentos (pilha)");
        System.out.println("11) Ver último atendimento concluído (topo da pilha)");
        System.out.println("12) Relatório (contagem e faturamento)");
        System.out.println(" 0) Sair");
        System.out.println("=====================================================");
    }

    private static void executarOpcao(int opcao, ClinicaServico clinica) {
        switch (opcao) {
            case 1 -> clinica.cadastrarCliente();
            case 2 -> clinica.chamarProximo();
            case 3 -> clinica.concluirAtendimento();
            case 4 -> clinica.listarFila();
            case 5 -> clinica.buscarLinear();
            case 6 -> clinica.buscarBinaria();
            case 7 -> clinica.ordenarFila();
            case 8 -> clinica.cancelarAtendimento();
            case 9 -> clinica.desfazerCancelamento();
            case 10 -> clinica.mostrarHistorico();
            case 11 -> clinica.mostrarUltimoAtendimento();
            case 12 -> clinica.mostrarRelatorio();
            case 0 -> System.out.println("Encerrando o sistema. Até logo!");
            default -> System.out.println("Opção inexistente."); // defesa extra; Entrada já valida o intervalo
        }
    }
}
