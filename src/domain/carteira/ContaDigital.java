package domain.carteira;

import domain.usuarios.Usuario;
import exception.SaldoInsuficienteException;
import java.time.LocalDate;

public class ContaDigital extends ContaFinanceira {

    private double limiteDiarioTransferencia;
    private double totalTransferidoHoje = 0;
    private LocalDate ultimoDiaTransferencia = LocalDate.now();

    // criação normal
    public ContaDigital(String nome, double saldoInicial, Usuario dono, double limiteDiario) {
        super(nome, saldoInicial, dono);
        this.limiteDiarioTransferencia = limiteDiario;
    }

    // reconstrução do arquivo
    public ContaDigital(String id, String nome, double saldoInicial, Usuario dono, double limiteDiario) {
        super(id, nome, saldoInicial, dono);
        this.limiteDiarioTransferencia = limiteDiario;
    }

    @Override
    public String getTipoConta() {
        return "Conta Digital";
    }

    @Override
    public void transferir(ContaFinanceira destino, double valor) throws SaldoInsuficienteException {

        // troca de dia → reseta limite
        if (!LocalDate.now().equals(ultimoDiaTransferencia)) {
            totalTransferidoHoje = 0;
            ultimoDiaTransferencia = LocalDate.now();
        }

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
}
