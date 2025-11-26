package domain.relatorios;

import service.UsuarioService;
import domain.transacoes.Transacao;
import domain.usuarios.Usuario;

public class RankingDespesas implements IRelatorio {

    private final UsuarioService usuarioService;

    public RankingDespesas(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("=== Relatório de Ranking de Maiores Despesas ===");

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            // Vamos buscar as transações do usuário e calcular a maior despesa
            double maiorDespesa = 0.0;
            for (Transacao transacao : usuario.getTransacoes()) {
                // Verifica se a transação é uma despesa e se o valor é maior que o maior despesa registrado
                if (transacao.getTipo() == Transacao.TipoTransacao.DESPESA && transacao.getValor() > maiorDespesa) {
                    maiorDespesa = transacao.getValor();
                }
            }
            System.out.println(usuario.getNome() + " - Maior Despesa: " + maiorDespesa);
        }
    }

    private double calcularMaiorDespesa(Usuario usuario) {
        // Lógica para buscar as transações e calcular a maior despesa do usuário
        double maiorDespesa = 0.0;
        for (Transacao transacao : usuario.getTransacoes()) {
            if (transacao.getValor() > maiorDespesa) {
                maiorDespesa = transacao.getValor();
            }
        }
        return maiorDespesa;
    }

}
