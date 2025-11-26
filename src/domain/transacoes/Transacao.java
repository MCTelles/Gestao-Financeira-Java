package domain.transacoes;

import java.time.LocalDate;

public class Transacao {

    private final double valor;
    private final String categoria;
    private final LocalDate data;
    private final TipoTransacao tipo;

    public enum TipoTransacao {
        RECEITA, DESPESA
    }

    // Construtor da transação
    public Transacao(double valor, String categoria, LocalDate data, TipoTransacao tipo) {
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }
}
