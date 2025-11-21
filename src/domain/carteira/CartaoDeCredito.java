package domain.carteira;

public class CartaoDeCredito extends ContaFinanceira {

    public CartaoDeCredito(String id, String nome, double saldoInicial) {
        super(id, nome, saldoInicial);
    }

    @Override
    public String getTipoConta() {
        return "";
    }
}
