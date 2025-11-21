package domain.carteira;

public class PoupancaVirtual extends ContaFinanceira {

    public PoupancaVirtual(String id, String nome, double saldoInicial) {
        super(id, nome, saldoInicial);
    }

    @Override
    public String getTipoConta() {
        return "";
    }
}
