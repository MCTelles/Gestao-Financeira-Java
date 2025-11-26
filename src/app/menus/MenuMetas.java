package app.menus;
import service.MetaService;
import service.UsuarioService;
import app.IoConsole;
import domain.metas.Meta;
import domain.usuarios.*;

import java.time.LocalDate;

public class MenuMetas {
    private final MetaService metaService;
    private final UsuarioService usuarioService;

    public MenuMetas(MetaService metaService, UsuarioService usuarioService) {
        this.metaService = metaService;
        this.usuarioService = usuarioService;
    }

    public void exibir() {
        while (true) {
            System.out.println("\n=== MENU DE METAS ===");
            System.out.println("1. Criar Meta Individual");
            System.out.println("2. Criar Meta de Grupo");
            System.out.println("3. Listar Metas");
            System.out.println("4. Atualizar Meta");
            System.out.println("0. Voltar");

            int op = IoConsole.lerInt("Escolha");

            switch (op) {
                case 1 -> criarMetaIndividual();
                case 2 -> criarMetaGrupo();
                case 3 -> listarMetas();
                case 4 -> atualizarMeta();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void criarMetaIndividual() {
        String nome = IoConsole.lerTexto("Nome da Meta:");
        double valorMeta = IoConsole.lerDouble("Valor da Meta:");
        LocalDate prazo = IoConsole.lerData("Prazo para atingir a meta:");
        String categoria = IoConsole.lerTexto("Categoria:");
        String usuarioId = IoConsole.lerTexto("ID do Usuário:");

        // Verificar se o usuário existe
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Erro: Usuário com ID " + usuarioId + " não encontrado.");
            return;  // Retorna sem criar a meta
        }

        // Se o usuário existe, criar a meta
        metaService.criarMetaIndividual(nome, valorMeta, prazo, categoria, usuarioId);
        System.out.println("Meta Individual criada com sucesso.");
    }


    private void criarMetaGrupo() {
        String nome = IoConsole.lerTexto("Nome da Meta:");
        double valorMeta = IoConsole.lerDouble("Valor da Meta:");
        LocalDate prazo = IoConsole.lerData("Prazo para atingir a meta:");
        String categoria = IoConsole.lerTexto("Categoria:");
        String grupoId = IoConsole.lerTexto("ID do Grupo:");

        metaService.criarMetaGrupo(nome, valorMeta, prazo, categoria, grupoId);
        System.out.println("Meta de Grupo criada com sucesso.");
    }

    private void listarMetas() {
        System.out.println("\n--- METAS ---");
        for (Meta meta : metaService.listarMetas()) {
            System.out.println(meta);
        }
    }

    private void atualizarMeta() {
        String nomeMeta = IoConsole.lerTexto("Nome da Meta para Atualizar:");
        double valor = IoConsole.lerDouble("Valor a adicionar à meta:");

        for (Meta meta : metaService.listarMetas()) {
            if (meta.getNome().equalsIgnoreCase(nomeMeta)) {
                metaService.atualizarMeta(meta, valor);
                System.out.println("Meta atualizada com sucesso.");
                return;
            }
        }

        System.out.println("Meta não encontrada.");
    }
}
