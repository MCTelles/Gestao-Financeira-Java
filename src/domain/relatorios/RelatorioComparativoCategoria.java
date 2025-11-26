package domain.relatorios;
import java.time.LocalDate;
import java.util.List;
import domain.usuarios.Usuario;
import service.*;
import domain.transacoes.Transacao;


public class RelatorioComparativoCategoria implements IRelatorio {

    private final UsuarioService usuarioService;

    public RelatorioComparativoCategoria(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("=== Relatório Comparativo por Categoria ===");

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            // Inicializa os totais por categoria
            double totalAlimentacao = 0.0;
            double totalMoradia = 0.0;

            for (Transacao transacao : usuario.getTransacoes()) {
                // Verifica o tipo da transação e soma o valor para a categoria correspondente
                if (transacao.getTipo() == Transacao.TipoTransacao.DESPESA) {
                    if (transacao.getCategoria().equals("Alimentação")) {
                        totalAlimentacao += transacao.getValor();
                    } else if (transacao.getCategoria().equals("Moradia")) {
                        totalMoradia += transacao.getValor();
                    }
                }
            }

            System.out.println(usuario.getNome() + " - Categoria: Alimentação - Total: " + totalAlimentacao);
            System.out.println(usuario.getNome() + " - Categoria: Moradia - Total: " + totalMoradia);
        }
    }

    // Método para calcular o total de despesas por categoria
    private double calcularTotalPorCategoria(Usuario usuario, String categoria) {
        double total = 0.0;
        for (Transacao transacao : usuario.getTransacoes()) {
            if (transacao.getCategoria().equals(categoria)) {
                total += transacao.getValor();
            }
        }
        return total;
    }

}
