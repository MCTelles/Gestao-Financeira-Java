package domain.carteira;

import domain.usuarios.Usuario;

public class PoupancaVirtual extends ContaFinanceira {

    public PoupancaVirtual(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Cofrinho Virtual";
    }
}
