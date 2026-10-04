package util;

import java.util.Scanner;
import modelos.TipoProcedimento;

/**
 * Leitura segura do teclado. Concentra aqui toda a validação de entrada para que
 * o resto do programa nunca precise tratar texto inválido nem exceções do Scanner.
 */
public class Entrada {
    private static final Scanner SCANNER = new Scanner(System.in);

    /** Lê uma linha. Se a entrada acabar (Ctrl+D ou fim de arquivo), encerra o programa com educação. */
    public static String lerLinha(String mensagem) {
        System.out.print(mensagem);
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nEntrada encerrada. Até logo!");
            System.exit(0);
        }
        return SCANNER.nextLine().trim();
    }

    /** Lê um texto não vazio, repetindo a pergunta até receber algo válido. */
    public static String lerTexto(String mensagem) {
        String texto = lerLinha(mensagem);
        while (texto.isEmpty()) {
            System.out.println("  >> O campo não pode ficar vazio.");
            texto = lerLinha(mensagem);
        }
        return texto;
    }

    /** Lê um telefone com pelo menos 8 dígitos (aceita parênteses, espaços e hífen). */
    public static String lerTelefone(String mensagem) {
        while (true) {
            String telefone = lerTexto(mensagem);
            String apenasDigitos = telefone.replaceAll("[^0-9]", "");
            if (apenasDigitos.length() >= 8 && apenasDigitos.length() <= 13) {
                return telefone;
            }
            System.out.println("  >> Telefone inválido. Digite de 8 a 13 dígitos.");
        }
    }

    /** Lê um inteiro dentro de [minimo, maximo]; repete a pergunta se for texto ou estiver fora da faixa. */
    public static int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            String texto = lerLinha(mensagem);
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("  >> Opção inexistente. Digite um número de " + minimo + " a " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("  >> Entrada inválida. Digite apenas números.");
            }
        }
    }

    /** Mostra os procedimentos (percorrendo o Enum com values()) e devolve o escolhido. */
    public static TipoProcedimento lerProcedimento() {
        TipoProcedimento[] opcoes = TipoProcedimento.values();
        System.out.println("Procedimentos disponíveis:");
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println("  " + (i + 1) + ") " + opcoes[i]);
        }
        int escolha = lerInteiro("Escolha o procedimento: ", 1, opcoes.length);
        return opcoes[escolha - 1];
    }
}
