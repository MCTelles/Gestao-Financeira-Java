package domain.carteira;

import java.util.UUID;
import exception.SaldoInsuficienteException;
import domain.usuarios.Usuario;

public abstract class ContaFinanceira {

    protected final String id;
    protected String nome;
    protected double saldo;
    protected Usuario dono;

    public ContaFinanceira(String nome, double saldoInicial, Usuario dono) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.saldo = saldoInicial;
        this.dono = dono;
    }

    public void depositar(double valor) {
        validarValorPositivo(valor);
        saldo += valor;
        aposMovimentacao("DEPÓSITO", valor);
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        validarValorPositivo(valor);
        if (saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta: " + nome);
        }
        saldo -= valor;
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
        // Hook method
    }

    protected void validarValorPositivo(double valor) {
        if (valor <= 0)
            throw new IllegalArgumentException("O valor deve ser positivo.");
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public Usuario getDono() {
        return dono;
    }

    public abstract String getTipoConta();
}
