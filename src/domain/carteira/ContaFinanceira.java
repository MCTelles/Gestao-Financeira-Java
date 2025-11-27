package domain.carteira;

import domain.transacoes.Transacao;
import domain.usuarios.Usuario;
import exception.SaldoInsuficienteException;
import java.time.LocalDate;
import java.util.UUID;

public abstract class ContaFinanceira {

    protected String id;
    protected String nome;
    protected double saldo;
    protected Usuario dono;

    // Construtor para criação normal (gera ID novo)
    public ContaFinanceira(String nome, double saldoInicial, Usuario dono) {
        this(UUID.randomUUID().toString(), nome, saldoInicial, dono);
    }

    // Construtor para reconstrução a partir do arquivo (usa ID já existente)
    public ContaFinanceira(String id, String nome, double saldoInicial, Usuario dono) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldoInicial;
        this.dono = dono;
    }

    public void depositar(double valor) {
        validarValorPositivo(valor);
        this.saldo += valor;

        if (dono != null) {
            Transacao t = new Transacao(
                    valor,
                    "Depósito",
                    LocalDate.now(),
                    Transacao.TipoTransacao.RECEITA
            );
            dono.adicionarTransacao(t);
        }

        aposMovimentacao("DEPÓSITO", valor);
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        validarValorPositivo(valor);

        if (valor > this.saldo)
            throw new SaldoInsuficienteException("Saldo insuficiente!");

        this.saldo -= valor;

        if (dono != null) {
            Transacao t = new Transacao(
                    valor,
                    "Saque",
                    LocalDate.now(),
                    Transacao.TipoTransacao.DESPESA
            );
            dono.adicionarTransacao(t);
        }

        aposMovimentacao("SAQUE", valor);
    }

    public void transferir(ContaFinanceira destino, double valor) throws SaldoInsuficienteException {
        validarValorPositivo(valor);
        this.sacar(valor);
        destino.depositar(valor);
        aposMovimentacao("TRANSFERÊNCIA PARA " + destino.getNome(), valor);
        destino.aposMovimentacao("TRANSFERÊNCIA RECEBIDA DE " + this.getNome(), valor);
    }

    protected void aposMovimentacao(String tipo, double valor) {
        // hook: especializado nas subclasses
    }

    protected void validarValorPositivo(double valor) {
        if (valor <= 0)
            throw new IllegalArgumentException("O valor deve ser positivo.");
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public Usuario getDono() { return dono; }

    public abstract String getTipoConta();
}
