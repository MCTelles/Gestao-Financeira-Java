package app;

import app.menus.MenuContas;
import app.IoConsole;
import service.ContaService;
import service.UsuarioService;
import app.menus.MenuUsuarios;
import service.MetaService;
import app.menus.MenuMetas;
import app.menus.MenuRelatorios;
import domain.relatorios.GerenciadorRelatorios;

public class MenuPrincipal {

    // Instância única de ContaService para toda a aplicação
    private final ContaService contaService = new ContaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final MetaService metaService = new MetaService();
    private final GerenciadorRelatorios gerenciadorRelatorios = new GerenciadorRelatorios();

    public void exibir() {
        while (true) {
            System.out.println("\n=== SISTEMA FINANCEIRO ===");
            System.out.println("1. Contas");
            System.out.println("2. Usuários");
            System.out.println("3. Metas");
            System.out.println("4. Relátorios");
            System.out.println("0. Sair");

            int opcao = IoConsole.lerInt("Escolha");

            switch (opcao) {
                case 1 -> new MenuContas(contaService, usuarioService).exibir();
                case 2 -> new MenuUsuarios(usuarioService).exibir();
                case 3 -> gerenciarMetas();
                case 4 -> acessarRelatorios();

                case 0 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    private void gerenciarMetas() {
        MenuMetas menuMetas = new MenuMetas(metaService, usuarioService);
        menuMetas.exibir();
    }

    private void acessarRelatorios() {
        MenuRelatorios menuRelatorios = new MenuRelatorios(usuarioService, gerenciadorRelatorios);
        menuRelatorios.exibir();  // Exibe o menu de relatórios
    }
}
