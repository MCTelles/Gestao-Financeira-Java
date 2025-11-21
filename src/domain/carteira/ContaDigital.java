package domain.carteira;

import exception.SaldoInsuficienteException;

public class ContaDigital extends ContaFinanceira {

    private double limiteTransferenciaDiaria;
    private double totalTransferidoHoje;

    public ContaDigital(String id, String nome, double saldoInicial, double limiteTransferenciaDiaria) {
        super(id, nome, saldoInicial);
        this.limiteTransferenciaDiaria = limiteTransferenciaDiaria;
        this.totalTransferidoHoje = 0;
    }

    @Override
    public void transferir(ContaFinanceira destino, double valor) throws SaldoInsuficienteException {
        validarValorPositivo(valor);

        if (valor + totalTransferidoHoje > limiteTransferenciaDiaria) {
            throw new IllegalArgumentException(
                "Limite diário de transferência excedido para a conta: " + nome
            );
        }

        super.transferir(destino, valor);
        totalTransferidoHoje += valor;

        aposMovimentacao("TRANSFERÊNCIA DIGITAL PARA " + destino.getNome(), valor);
    }

    @Override
    protected void aposMovimentacao(String tipo, double valor) {
        System.out.println("[Conta Digital] Movimentação: " + tipo + " | Valor: " + valor);
    }

    @Override
    public String getTipoConta() {
        return "Conta Digital";
    }

    public double getLimiteTransferenciaDiaria() {
        return limiteTransferenciaDiaria;
    }

    public double getTotalTransferidoHoje() {
        return totalTransferidoHoje;
    }
}
