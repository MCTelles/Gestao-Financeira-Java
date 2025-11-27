package domain.carteira;

import domain.usuarios.Usuario;

public class CarteiraInvestimento extends ContaFinanceira {

    // criação normal
    public CarteiraInvestimento(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    // reconstrução do arquivo
    public CarteiraInvestimento(String id, String nome, double saldoInicial, Usuario dono) {
        super(id, nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Carteira de Investimento";
    }
}
