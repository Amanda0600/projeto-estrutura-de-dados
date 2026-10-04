package modelos;

/**
 * ENUM 2: tipos de procedimento oferecidos pela clínica.
 *
 * Diferente do primeiro Enum, este carrega DADOS (nome, duração e valor).
 * Assim, o preço e a duração ficam centralizados aqui: se o valor da massagem mudar,
 * altera-se em um único lugar.
 */
public enum TipoProcedimento {
    LIMPEZA_DE_PELE("Limpeza de pele", 60, 120.00),
    MASSAGEM("Massagem relaxante", 50, 150.00),
    DEPILACAO("Depilação a laser", 30, 90.00),
    DRENAGEM_LINFATICA("Drenagem linfática", 45, 130.00),
    PEELING("Peeling facial", 40, 200.00);

    private final String nome;
    private final int duracaoMinutos;
    private final double valor;

    TipoProcedimento(String nome, int duracaoMinutos, double valor) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("%s (%d min, R$ %.2f)", nome, duracaoMinutos, valor);
    }
}
