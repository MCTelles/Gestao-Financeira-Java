package domain.carteira;

import domain.usuarios.Usuario;

public class PoupancaVirtual extends ContaFinanceira {

    // criação normal
    public PoupancaVirtual(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    // reconstrução do arquivo
    public PoupancaVirtual(String id, String nome, double saldoInicial, Usuario dono) {
        super(id, nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Poupança Virtual";
    }
}
