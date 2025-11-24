package domain.carteira;

import exception.SaldoInsuficienteException;
import domain.usuarios.Usuario;


public class ContaDigital extends ContaFinanceira {

    private double limiteDiarioTransferencia;
    private double totalTransferidoHoje;

    public ContaDigital(String nome, double saldoInicial, double limiteDiarioTransferencia, Usuario dono) {
        super(nome, saldoInicial, dono);
        this.limiteDiarioTransferencia = limiteDiarioTransferencia;
        this.totalTransferidoHoje = 0;
    }

    @Override
    public void transferir(ContaFinanceira destino, double valor) throws SaldoInsuficienteException {
        if (valor + totalTransferidoHoje > limiteDiarioTransferencia) {
            throw new IllegalArgumentException("Limite diário de transferência excedido.");
        }

        super.transferir(destino, valor);
        totalTransferidoHoje += valor;
        aposMovimentacao("PIX/TRANSFERÊNCIA DIGITAL", valor);
    }

    @Override
    protected void aposMovimentacao(String tipo, double valor) {
        System.out.println("[Conta Digital] " + tipo + ": R$ " + valor);
    }

    @Override
    public String getTipoConta() {
        return "Conta Digital";
    }
}
