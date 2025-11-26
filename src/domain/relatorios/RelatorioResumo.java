package domain.relatorios;

import domain.usuarios.Usuario;
import service.UsuarioService;
import domain.transacoes.Transacao;

public class RelatorioResumo implements IRelatorio {

    private final UsuarioService usuarioService;

    public RelatorioResumo(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("=== Relatório Resumo ===");

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            double totalGastos = 0.0;
            double totalEconomias = 0.0;

            // Calculando o total de gastos e economias diretamente no método gerar()
            for (Transacao transacao : usuario.getTransacoes()) {
                if (transacao.getTipo() == Transacao.TipoTransacao.DESPESA) {
                    totalGastos += transacao.getValor();
                } else if (transacao.getTipo() == Transacao.TipoTransacao.RECEITA) {
                    totalEconomias += transacao.getValor();
                }
            }

            // Exibe o resultado
            System.out.println(usuario.getNome() + " - Total de Gastos: " + totalGastos + " | Total de Economias: " + totalEconomias);
        }
    }
}
