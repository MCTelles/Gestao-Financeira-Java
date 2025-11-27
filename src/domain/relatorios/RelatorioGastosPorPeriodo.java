package domain.relatorios;

import domain.transacoes.Transacao;
import domain.usuarios.Usuario;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import service.UsuarioService;

public class RelatorioGastosPorPeriodo implements IRelatorio {

    private final UsuarioService usuarioService;
    private final LocalDate inicio;
    private final LocalDate fim;

    public RelatorioGastosPorPeriodo(UsuarioService usuarioService, LocalDate inicio, LocalDate fim) {
        this.usuarioService = usuarioService;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public void gerar() {
        System.out.println("\n=== RELATÓRIO DE GASTOS POR PERÍODO ===");
        System.out.println("Período: " + inicio + " até " + fim);

        double totalDespesas = 0.0;
        double totalReceitas = 0.0;

        Map<String, Double> categorias = new HashMap<>();

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            for (Transacao t : usuario.getTransacoes()) {

                LocalDate data = t.getData();
                if (data.isBefore(inicio) || data.isAfter(fim))
                    continue;

                switch (t.getTipo()) {
                    case DESPESA -> {
                        totalDespesas += t.getValor();
                        categorias.put(
                                t.getCategoria(),
                                categorias.getOrDefault(t.getCategoria(), 0.0) + t.getValor()
                        );
                    }
                    case RECEITA -> totalReceitas += t.getValor();
                }
            }
        }

        System.out.printf("Total de Receitas: R$ %.2f\n", totalReceitas);
        System.out.printf("Total de Despesas: R$ %.2f\n", totalDespesas);
        System.out.printf("Saldo do Período: R$ %.2f\n", (totalReceitas - totalDespesas));

        if (!categorias.isEmpty()) {
            String maiorCategoria = categorias.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey();

            System.out.println("Categoria mais cara: " + maiorCategoria);
        } else {
            System.out.println("Nenhuma despesa no período.");
        }
    }
}
