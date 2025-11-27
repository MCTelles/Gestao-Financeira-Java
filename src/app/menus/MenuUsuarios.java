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

        System.out.println("\n=== CRIAR USUÁRIO INDIVIDUAL ===");

        String nome = IoConsole.lerTexto("Nome:");
        String email = IoConsole.lerTexto("Email:");
        String senha = IoConsole.lerTexto("Senha:");
        String telefone = IoConsole.lerTexto("Telefone:");
        String endereco = IoConsole.lerTexto("Endereço:");

        System.out.println("Escolha o perfil:");
        System.out.println("1. Admin");
        System.out.println("2. Moderador");
        System.out.println("3. Membro");

        int escolha = IoConsole.lerInt("Perfil:");

        String perfil = switch (escolha) {
            case 1 -> "ADMIN";
            case 2 -> "MODERADOR";
            case 3 -> "MEMBRO";
            default -> "MEMBRO";
        };

        Usuario u = usuarioService.criarUsuario(
                perfil,
                nome,
                email,
                senha,
                telefone,
                endereco,
                true
        );

        System.out.println("Usuário criado com sucesso!");
        System.out.println("ID: " + u.getId());
        System.out.println("Perfil: " + u.getPerfil());
    }

    private void criarGrupo() {

        System.out.println("\n=== CRIAR GRUPO ===");

        String nome = IoConsole.lerTexto("Nome do grupo:");
        String email = IoConsole.lerTexto("Email do grupo:");
        String senha = IoConsole.lerTexto("Senha:");
        String telefone = IoConsole.lerTexto("Telefone:");
        String endereco = IoConsole.lerTexto("Endereço:");

        Grupo grupo = usuarioService.criarGrupo(
                nome,
                email,
                senha,
                telefone,
                endereco,
                true
        );

        System.out.println("Grupo criado com sucesso!");
        System.out.println("ID: " + grupo.getId());
    }


    private void listarUsuarios() {

        System.out.println("\n--- USUÁRIOS CADASTRADOS ---");

        for (Usuario u : usuarioService.listarUsuarios()) {

            String tipo = (u instanceof Grupo)
                    ? "GRUPO"
                    : u.getPerfil().name();

            System.out.println(
                u.getId() + " | " + u.getNome() + " | " + tipo + " | Email: " + u.getEmail()
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
