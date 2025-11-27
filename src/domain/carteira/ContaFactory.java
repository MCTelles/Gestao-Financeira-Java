package domain.carteira;

import domain.usuarios.Usuario;

public class ContaFactory {

    public enum TipoConta {
        CONTA_CORRENTE,
        CONTA_DIGITAL,
        CARTAO_CREDITO,
        POUPANCA_VIRTUAL,
        CARTEIRA_INVESTIMENTO
    }

    // Criação normal (ID automático)
    public static ContaFinanceira criarConta(
            TipoConta tipo,
            String nome,
            double saldoInicial,
            Usuario dono,
            double extra
    ) {
        return switch (tipo) {
            case CONTA_CORRENTE ->
                    new ContaCorrente(nome, saldoInicial, dono);
            case CONTA_DIGITAL ->
                    new ContaDigital(nome, saldoInicial, dono, extra);
            case CARTAO_CREDITO ->
                    new CartaoCredito(nome, saldoInicial, dono, extra);
            case POUPANCA_VIRTUAL ->
                    new PoupancaVirtual(nome, saldoInicial, dono);
            case CARTEIRA_INVESTIMENTO ->
                    new CarteiraInvestimento(nome, saldoInicial, dono);
        };
    }

    // Criação restaurando a conta do JSON (com ID)
    public static ContaFinanceira criarContaComId(
            String id,
            TipoConta tipo,
            String nome,
            double saldo,
            Usuario dono,
            double extra
    ) {
        return switch (tipo) {
            case CONTA_CORRENTE ->
                    new ContaCorrente(id, nome, saldo, dono);
            case CONTA_DIGITAL ->
                    new ContaDigital(id, nome, saldo, dono, extra);
            case CARTAO_CREDITO ->
                    new CartaoCredito(id, nome, saldo, dono, extra);
            case POUPANCA_VIRTUAL ->
                    new PoupancaVirtual(id, nome, saldo, dono);
            case CARTEIRA_INVESTIMENTO ->
                    new CarteiraInvestimento(id, nome, saldo, dono);
        };
    }
}
