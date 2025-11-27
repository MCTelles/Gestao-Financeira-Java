package domain.carteira;

import domain.usuarios.Usuario;

public class CartaoCredito extends ContaFinanceira {

    private double limiteCredito;

    // criação normal
    public CartaoCredito(String nome, double saldoInicial, Usuario dono, double limiteCredito) {
        super(nome, saldoInicial, dono);
        this.limiteCredito = limiteCredito;
    }

    // reconstrução do arquivo
    public CartaoCredito(String id, String nome, double saldoInicial, Usuario dono, double limiteCredito) {
        super(id, nome, saldoInicial, dono);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public String getTipoConta() {
        return "Cartão de Crédito";
    }
}
