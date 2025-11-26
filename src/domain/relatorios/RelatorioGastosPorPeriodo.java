package domain.relatorios;

import java.time.LocalDate;
import service.UsuarioService;
import domain.usuarios.Usuario;
import domain.transacoes.Transacao;


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
        System.out.println("=== Relatório de Gastos por Período ===");
        double totalGastos = 0.0;

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            double gastosPeriodo = 0.0;
            for (Transacao transacao : usuario.getTransacoes()) {
                // Verifica se a transação está dentro do período
                if (transacao.getTipo() == Transacao.TipoTransacao.DESPESA &&
                        (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim))) {
                    gastosPeriodo += transacao.getValor();
                }
            }
            totalGastos += gastosPeriodo;
            System.out.println(usuario.getNome() + ": " + gastosPeriodo);
        }

        System.out.println("Total de Gastos: " + totalGastos);
    }

    // Método para calcular os gastos no período
    private double calcularGastosPorPeriodo(Usuario usuario, LocalDate inicio, LocalDate fim) {
        double total = 0.0;
        for (Transacao transacao : usuario.getTransacoes()) {
            if (transacao.getData().isAfter(inicio) && transacao.getData().isBefore(fim)) {
                total += transacao.getValor();
            }
        }
        return total;
    }
}
