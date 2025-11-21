package app;

import app.menus.MenuContas;

public class MenuPrincipal {

    public void exibir() {
        while (true) {
            System.out.println("\n=== SISTEMA FINANCEIRO ===");
            System.out.println("1. Testar contas");
            System.out.println("0. Sair");

            int opcao = IoConsole.lerInt("Escolha uma opção");

            switch (opcao) {
                case 1 -> new MenuContas().exibir();
                case 0 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}