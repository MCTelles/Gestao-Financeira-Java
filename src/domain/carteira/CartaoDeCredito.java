package domain.carteira;

import domain.usuarios.Usuario;
import exception.SaldoInsuficienteException;

public class CartaoDeCredito extends ContaFinanceira {

    private double limiteCredito;
    private double faturaAtual;

    public CartaoDeCredito(String nome, double limiteCredito, Usuario dono) {
        super(nome, 0, dono); // saldo = 0, pois cartão não usa saldo
        this.limiteCredito = limiteCredito;
        this.faturaAtual = 0;
    }

    @Override
    public void depositar(double valor) {
        throw new UnsupportedOperationException("Cartão de crédito não recebe depósito.");
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > limiteCredito) {
            throw new SaldoInsuficienteException("Saldo insuficiente no cartão de crédito.");
        }
        limiteCredito -= valor;
        aposMovimentacao("SAQUE", valor);
    }

    public void comprar(double valor) {
        if (valor > limiteCredito - faturaAtual) {
            throw new IllegalArgumentException("Limite insuficiente.");
        }
        faturaAtual += valor;
    }

    public double getFaturaAtual() {
        return faturaAtual;
    }

    @Override
    public String getTipoConta() {
        return "Cartão de Crédito";
    }
}
