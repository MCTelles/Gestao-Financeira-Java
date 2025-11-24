package domain.carteira;

import domain.usuarios.Usuario;

public final class ContaFactory {

    private ContaFactory() {}

    public enum TipoConta {
        CONTA_CORRENTE,
        CONTA_DIGITAL,
        CARTAO_CREDITO,
        POUPANCA_VIRTUAL,
        CARTEIRA_INVESTIMENTO
    }

    /**
     * Factory principal para criar qualquer tipo de conta financeira.
     */
    public static ContaFinanceira criarConta(
            TipoConta tipo,
            String nome,
            double saldoInicial,
            Usuario dono,
            double parametroExtra // usado apenas quando necessário
    ) {
        return switch (tipo) {

            case CONTA_CORRENTE ->
                    new ContaCorrente(nome, saldoInicial, dono);

            case CONTA_DIGITAL ->
                    new ContaDigital(nome, saldoInicial, parametroExtra, dono);
            // parametroExtra = limite diário

            case CARTAO_CREDITO ->
                    new CartaoDeCredito(nome, parametroExtra, dono);
            // parametroExtra = limite do cartão

            case POUPANCA_VIRTUAL ->
                    new PoupancaVirtual(nome, saldoInicial, dono);

            case CARTEIRA_INVESTIMENTO ->
                    new CarteiraDeInvestimento(nome, saldoInicial, dono);
        };
    }

    // Métodos auxiliares opcionais (facilitam chamadas específicas)

    public static ContaFinanceira criarContaCorrente(String nome, double saldo, Usuario dono) {
        return criarConta(TipoConta.CONTA_CORRENTE, nome, saldo, dono, 0);
    }

    public static ContaFinanceira criarContaDigital(String nome, double saldo, double limite, Usuario dono) {
        return criarConta(TipoConta.CONTA_DIGITAL, nome, saldo, dono, limite);
    }

    public static ContaFinanceira criarCartaoCredito(String nome, double limiteCredito, Usuario dono) {
        return criarConta(TipoConta.CARTAO_CREDITO, nome, 0, dono, limiteCredito);
    }

    public static ContaFinanceira criarCofrinho(String nome, double saldo, Usuario dono) {
        return criarConta(TipoConta.POUPANCA_VIRTUAL, nome, saldo, dono, 0);
    }

    public static ContaFinanceira criarInvestimento(String nome, double saldo, Usuario dono) {
        return criarConta(TipoConta.CARTEIRA_INVESTIMENTO, nome, saldo, dono, 0);
    }
}
