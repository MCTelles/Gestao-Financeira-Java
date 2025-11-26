package app.menus;

import domain.relatorios.*;
import service.UsuarioService;
import app.IoConsole;

import java.time.LocalDate;

public class MenuRelatorios {

    private final GerenciadorRelatorios gerenciadorRelatorios;
    private final UsuarioService usuarioService;

    public MenuRelatorios(UsuarioService usuarioService, GerenciadorRelatorios gerenciadorRelatorios) {
        this.usuarioService = usuarioService;
        this.gerenciadorRelatorios = gerenciadorRelatorios;  // Inicializando o gerenciador de relatórios
    }

    public void exibir() {
        while (true) {
            System.out.println("\n=== MENU DE RELATÓRIOS ===");
            System.out.println("1. Relatório de Gastos por Período");
            System.out.println("2. Relatório de Comparativo por Categoria");
            System.out.println("3. Relatório de Ranking de Maiores Despesas");
            System.out.println("4. Relatório de Evolução de Saldo");
            System.out.println("5. Relatório Resumo");
            System.out.println("0. Voltar");

            int opcao = IoConsole.lerInt("Escolha");

            IRelatorio relatorio = null;
            switch (opcao) {
                case 1:
                    // Gera o relatório de Gastos por Período
                    relatorio = new RelatorioGastosPorPeriodo(usuarioService, LocalDate.now().minusMonths(1), LocalDate.now());
                    break;
                case 2:
                    // Gera o relatório de Comparativo por Categoria
                    relatorio = new RelatorioComparativoCategoria(usuarioService);
                    break;
                case 3:
                    // Gera o relatório de Ranking de Maiores Despesas
                    relatorio = new RankingDespesas(usuarioService);
                    break;
                case 4:
                    // Gera o relatório de Evolução de Saldo
                    relatorio = new SaldoEvolucao(usuarioService);
                    break;
                case 5:
                    // Gera o relatório Resumo
                    relatorio = new RelatorioResumo(usuarioService);
                    break;
                case 0:
                    return; // Voltar para o menu anterior
                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            // Se o relatório não for nulo, gera o relatório
            if (relatorio != null) {
                gerenciadorRelatorios.gerarRelatorio(relatorio);
            }
        }
    }
}
