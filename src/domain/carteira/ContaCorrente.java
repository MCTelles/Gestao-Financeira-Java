package domain.carteira;

import domain.usuarios.Usuario;

public class ContaCorrente extends ContaFinanceira {

    public ContaCorrente(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Conta Corrente"; // Aqui está a implementação
    }
}