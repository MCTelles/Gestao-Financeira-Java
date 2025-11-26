package app.menus;

import app.IoConsole;
import domain.usuarios.*;
import service.UsuarioService;

public class MenuUsuarios {

    private final UsuarioService usuarioService;

    public MenuUsuarios(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void exibir() {

        while (true) {
            System.out.println("\n=== MENU DE USUÁRIOS ===");
            System.out.println("1. Criar Usuário Individual");
            System.out.println("2. Criar Grupo");
            System.out.println("3. Listar Todos os Usuários");
            System.out.println("4. Gerenciar Grupo");
            System.out.println("0. Voltar");

            int op = IoConsole.lerInt("Escolha");

            switch (op) {
                case 1 -> criarUsuarioIndividual();
                case 2 -> criarGrupo();
                case 3 -> listarUsuarios();
                case 4 -> gerenciarGrupo();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void criarUsuarioIndividual() {

        String nome = IoConsole.lerTexto("Nome:");
        String email = IoConsole.lerTexto("Email:");
        String senha = IoConsole.lerTexto("Senha:");
        String telefone = IoConsole.lerTexto("Telefone:");
        String endereco = IoConsole.lerTexto("Endereço:");

        boolean ativo = IoConsole.lerTexto("Usuário ativo? (sim/nao):").equalsIgnoreCase("sim");
        Usuario u = usuarioService.criarUsuarioIndividual(nome, email, senha, telefone, endereco, ativo);

        System.out.println("Usuário criado com ID: " + u.getId());
    }

    private void criarGrupo() {

        String nome = IoConsole.lerTexto("Nome do grupo:");
        String email = IoConsole.lerTexto("Email do grupo:");
        String senha = IoConsole.lerTexto("Senha do grupo:");
        String telefone = IoConsole.lerTexto("Telefone do grupo:");
        String endereco = IoConsole.lerTexto("Endereço do grupo:");
        Conta conta = new Conta(1000.00);

        boolean ativo = IoConsole.lerTexto("Usuário ativo? (sim/nao):").equalsIgnoreCase("sim");

        Grupo grupo = usuarioService.criarGrupo(nome, email, senha, telefone, endereco, ativo, conta);

        System.out.println("Grupo criado com ID: " + grupo.getId());
    }

    private void listarUsuarios() {

        System.out.println("\n--- USUÁRIOS CADASTRADOS ---");

        for (Usuario u : usuarioService.listarUsuarios()) {
            System.out.println(
                    u.getId() + " | " + u.getNome() + " | " + u.getTipo() + " | Email: " + u.getEmail()
            );
        }
    }

    private void gerenciarGrupo() {

        String id = IoConsole.lerTexto("ID do grupo:");
        Usuario usuario = usuarioService.buscarPorId(id);

        if (!(usuario instanceof Grupo grupo)) {
            System.out.println("Grupo não encontrado!");
            return;
        }

        new MenuGrupos(usuarioService, grupo).exibir();
    }
}
