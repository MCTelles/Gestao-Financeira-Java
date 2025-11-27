package domain.carteira;

import domain.usuarios.Usuario;

public class ContaCorrente extends ContaFinanceira {

    // criação normal
    public ContaCorrente(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    // reconstrução do arquivo
    public ContaCorrente(String id, String nome, double saldoInicial, Usuario dono) {
        super(id, nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Conta Corrente";
    }
}
