package domain.carteira;

public class ContaCorrente extends ContaFinanceira {

  public ContaCorrente(String id, String nome, double saldoInicial) {
    super(id, nome, saldoInicial);
    //TODO Auto-generated constructor stub
  }

  @Override
  public String getTipoConta() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTipoConta'");
  }

}