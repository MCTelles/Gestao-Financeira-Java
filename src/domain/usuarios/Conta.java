package domain.usuarios;

public class Conta {

    private double saldoInicial;

    public Conta(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
