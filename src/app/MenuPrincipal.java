package app;

import app.menus.MenuContas;
import app.menus.MenuMetas;
import app.menus.MenuRelatorios;
import app.menus.MenuUsuarios;
import domain.relatorios.GerenciadorRelatorios;
import service.ContaService;
import service.MetaService;
import service.UsuarioService;

public class MenuPrincipal {

    private final UsuarioService usuarioService;
    private final ContaService contaService;
    private final MetaService metaService;
    private final GerenciadorRelatorios gerenciadorRelatorios;

    public MenuPrincipal() {

        // Criamos APENAS um UsuarioService
        this.usuarioService = new UsuarioService();

        // E passamos ele para ContaService
        this.contaService = new ContaService(usuarioService);

        // Metas pode usar o mesmo usuário
        this.metaService = new MetaService();

        // Relatórios também
        this.gerenciadorRelatorios = new GerenciadorRelatorios();
    }

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
        menuRelatorios.exibir();
    }
}
