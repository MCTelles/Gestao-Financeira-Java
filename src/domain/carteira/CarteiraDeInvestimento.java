package domain.carteira;

import domain.usuarios.Usuario;

public class CarteiraDeInvestimento extends ContaFinanceira {

    public CarteiraDeInvestimento(String nome, double saldoInicial, Usuario dono) {
        super(nome, saldoInicial, dono);
    }

    @Override
    public String getTipoConta() {
        return "Carteira de Investimento";
    }
}
