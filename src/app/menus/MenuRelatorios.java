package app.menus;

import app.IoConsole;
import domain.relatorios.*;
import java.time.LocalDate;
import service.UsuarioService;

public class MenuRelatorios {

    private final GerenciadorRelatorios gerenciadorRelatorios;
    private final UsuarioService usuarioService;

    public MenuRelatorios(UsuarioService usuarioService, GerenciadorRelatorios gerenciadorRelatorios) {
        this.usuarioService = usuarioService;
        this.gerenciadorRelatorios = gerenciadorRelatorios;
    }

    public void exibir() {

        while (true) {
            System.out.println("\n=== MENU DE RELATÓRIOS ===");
            System.out.println("1. Gastos por Período");
            System.out.println("2. Comparativo por Categoria");
            System.out.println("3. Ranking de Maiores Despesas");
            System.out.println("4. Evolução de Saldo");
            System.out.println("5. Relatório Resumo");
            System.out.println("0. Voltar");

            int opcao = IoConsole.lerInt("Escolha");

            switch (opcao) {
                case 1 -> gerarRelatorioGastosPorPeriodo();
                case 2 -> gerarComparativoCategoria();
                case 3 -> gerarRankingDespesas();
                case 4 -> gerarEvolucaoSaldo();
                case 5 -> gerarResumo();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void gerarRelatorioGastosPorPeriodo() {

        System.out.println("\n=== GASTOS POR PERÍODO ===");

        LocalDate inicio = lerData("Data inicial (AAAA-MM-DD): ");
        LocalDate fim = lerData("Data final (AAAA-MM-DD): ");

        IRelatorio rel = new RelatorioGastosPorPeriodo(usuarioService, inicio, fim);
        gerenciadorRelatorios.gerarRelatorio(rel);
    }

    private void gerarComparativoCategoria() {
        IRelatorio rel = new RelatorioComparativoCategoria(usuarioService);
        gerenciadorRelatorios.gerarRelatorio(rel);
    }

    private void gerarRankingDespesas() {

        System.out.println("\n=== RANKING DE DESPESAS ===");

        LocalDate inicio = lerData("Data inicial (AAAA-MM-DD): ");
        LocalDate fim = lerData("Data final (AAAA-MM-DD): ");

        IRelatorio rel = new RankingDespesas(usuarioService, inicio, fim);
        gerenciadorRelatorios.gerarRelatorio(rel);
    }

    private void gerarEvolucaoSaldo() {
        IRelatorio rel = new SaldoEvolucao(usuarioService);
        gerenciadorRelatorios.gerarRelatorio(rel);
    }

    private void gerarResumo() {
        IRelatorio rel = new RelatorioResumo(usuarioService);
        gerenciadorRelatorios.gerarRelatorio(rel);
    }

    private LocalDate lerData(String msg) {
        try {
            String texto = IoConsole.lerTexto(msg);
            return LocalDate.parse(texto);
        } catch (Exception e) {
            System.out.println("Data inválida. Tente novamente.");
            return lerData(msg);
        }
    }
}
