package domain.relatorios;

import domain.transacoes.Transacao;
import service.UsuarioService;
import domain.usuarios.Usuario;

public class SaldoEvolucao implements IRelatorio {

    private final UsuarioService usuarioService;

    public SaldoEvolucao(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("=== Relatório de Evolução de Saldo ===");

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            double saldoInicial = calcularSaldoInicial(usuario);
            double saldoFinal = saldoInicial;

            for (Transacao transacao : usuario.getTransacoes()) {
                saldoFinal += transacao.getTipo() == Transacao.TipoTransacao.RECEITA ? transacao.getValor() : -transacao.getValor();
            }

            System.out.println(usuario.getNome() + " - Saldo Inicial: " + saldoInicial + " | Saldo Final: " + saldoFinal);
        }
    }

    private double calcularSaldoInicial(Usuario usuario) {
        return usuario.getConta().getSaldoInicial();  // Acessando o saldo inicial da conta
    }
}
