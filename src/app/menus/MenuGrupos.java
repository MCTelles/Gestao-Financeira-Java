package app.menus;

import app.IoConsole;
import domain.usuarios.*;
import service.UsuarioService;

public class MenuGrupos {

    private final UsuarioService usuarioService;
    private final Grupo grupo;

    public MenuGrupos(UsuarioService usuarioService, Grupo grupo) {
        this.usuarioService = usuarioService;
        this.grupo = grupo;
    }

    public void exibir() {

        while (true) {
            System.out.println("\n=== GERENCIAR GRUPO: " + grupo.getNome() + " ===");
            System.out.println("1. Adicionar membro");
            System.out.println("2. Listar membros");
            System.out.println("0. Voltar");

            int op = IoConsole.lerInt("Escolha");

            switch (op) {
                case 1 -> adicionarMembro();
                case 2 -> listarMembros();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void adicionarMembro() {

        String idMembro = IoConsole.lerTexto("ID do usuário a adicionar:");
        Usuario membro = usuarioService.buscarPorId(idMembro);

        if (membro == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        grupo.adicionarMembro(membro);
        System.out.println("Membro adicionado com sucesso!");
    }

    private void listarMembros() {

        System.out.println("\n--- MEMBROS DO GRUPO ---");

        if (grupo.getMembros().isEmpty()) {
            System.out.println("Nenhum membro no grupo.");
            return;
        }

        for (Usuario u : grupo.getMembros()) {
            System.out.println(
                    u.getId() + " | " + u.getNome() + " | " + u.getTipo() + " | Email: " + u.getEmail()
            );
        }
    }
}
