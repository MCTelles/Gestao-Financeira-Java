package domain.carteira;

public class CarteiraDeInvestimento extends ContaFinanceira {

    public CarteiraDeInvestimento(String id, String nome, double saldoInicial) {
        super(id, nome, saldoInicial);
    }

    @Override
    public String getTipoConta() {
        return "";
    }
}
