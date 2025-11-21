package app.menus;

import app.IoConsole;
import domain.carteira.ContaDigital;
import domain.carteira.ContaFinanceira;
import exception.SaldoInsuficienteException;

public class MenuContas {

    public void exibir() {

        ContaFinanceira conta = null;

        while (true) {
            System.out.println("\n=== MENU DE CONTAS ===");
            System.out.println("1. Criar Conta Digital");
            System.out.println("2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Ver saldo");
            System.out.println("0. Voltar");

            int opcao = IoConsole.lerInt("Escolha");

            switch (opcao) {

                case 1 -> {
                    String id = IoConsole.lerTexto("ID da conta");
                    String nome = IoConsole.lerTexto("Nome da conta");
                    double saldoInicial = IoConsole.lerDouble("Saldo inicial");
                    double limite = IoConsole.lerDouble("Limite diário de transferência");

                    conta = new ContaDigital(id, nome, saldoInicial, limite);
                    System.out.println("Conta criada com sucesso!");
                }

                case 2 -> {
                    if (conta == null) {
                        System.out.println("Crie uma conta primeiro!");
                        break;
                    }
                    double valor = IoConsole.lerDouble("Valor para depositar");
                    conta.depositar(valor);
                    System.out.println("Depósito realizado.");
                }

                case 3 -> {
                    if (conta == null) {
                        System.out.println("Crie uma conta primeiro!");
                        break;
                    }
                    double valor = IoConsole.lerDouble("Valor para sacar");

                    try {
                        conta.sacar(valor);
                        System.out.println("Saque realizado.");
                    } catch (SaldoInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }

                case 4 -> {
                    if (conta == null) {
                        System.out.println("Crie uma conta primeiro!");
                        break;
                    }
                    System.out.println("Saldo atual: " + conta.getSaldo());
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
