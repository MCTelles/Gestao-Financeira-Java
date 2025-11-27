package domain.transacoes;

import domain.carteira.ContaFinanceira;
import domain.usuarios.Usuario;
import java.time.LocalDate;
import java.util.UUID;

public class Transacao {

    private final String id;
    private final double valor;
    private final String descricao;
    private final String categoria;
    private final LocalDate data;
    private final TipoTransacao tipo;

    private final Usuario pagador;
    private final Usuario beneficiario;

    private final boolean recorrente;
    private final int parcelas;

    private final ContaFinanceira contaOrigem;
    private final ContaFinanceira contaDestino;

    public enum TipoTransacao {
        RECEITA,
        DESPESA,
        TRANSFERENCIA
    }

    public Transacao(
            double valor,
            String descricao,
            String categoria,
            LocalDate data,
            TipoTransacao tipo,
            Usuario pagador,
            Usuario beneficiario,
            boolean recorrente,
            int parcelas,
            ContaFinanceira contaOrigem,
            ContaFinanceira contaDestino
    ) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.tipo = tipo;
        this.pagador = pagador;
        this.beneficiario = beneficiario;
        this.recorrente = recorrente;
        this.parcelas = parcelas;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    // RECEITA ou DESPESA simples
    public Transacao(double valor, String categoria, LocalDate data, TipoTransacao tipo) {
        this(valor, categoria, categoria, data, tipo, null, null, false, 1, null, null);
    }

    // Transferência
    public static Transacao transferencia(double valor, String descricao, LocalDate data,
                                          Usuario pagador, Usuario beneficiario,
                                          ContaFinanceira origem, ContaFinanceira destino) {

        return new Transacao(
                valor,
                descricao,
                "Transferência",
                data,
                TipoTransacao.TRANSFERENCIA,
                pagador,
                beneficiario,
                false,
                1,
                origem,
                destino
        );
    }

    public String getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
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

    public Usuario getPagador() {
        return pagador;
    }

    public Usuario getBeneficiario() {
        return beneficiario;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public int getParcelas() {
        return parcelas;
    }

    public ContaFinanceira getContaOrigem() {
        return contaOrigem;
    }

    public ContaFinanceira getContaDestino() {
        return contaDestino;
    }
}
