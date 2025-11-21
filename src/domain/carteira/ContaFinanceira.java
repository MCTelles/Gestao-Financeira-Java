package domain.carteira;
import exception.SaldoInsuficienteException;

public abstract class ContaFinanceira {

    protected String id;
    protected String nome;
    protected double saldo;

    public ContaFinanceira(String id, String nome, double saldoInicial) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldoInicial;
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

    protected void aposMovimentacao(String tipo, double valor) {}

    protected void validarValorPositivo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo.");
        }
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
    
    public abstract String getTipoConta();
}
