package app;

import app.menus.MenuContas;
import app.IoConsole;
import service.ContaService;
import service.UsuarioService;
import app.menus.MenuUsuarios;

public class MenuPrincipal {

    // Instância única de ContaService para toda a aplicação
    private final ContaService contaService = new ContaService();
    private final UsuarioService usuarioService = new UsuarioService();


    public void exibir() {
        while (true) {
            System.out.println("\n=== SISTEMA FINANCEIRO ===");
            System.out.println("1. Contas");
            System.out.println("2. Usuários");
            System.out.println("0. Sair");

            int opcao = IoConsole.lerInt("Escolha");

            switch (opcao) {
                case 1 -> new MenuContas(contaService, usuarioService).exibir();
                case 2 -> new MenuUsuarios(usuarioService).exibir();

                case 0 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
