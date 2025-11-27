package domain.relatorios;

import domain.transacoes.Transacao;
import domain.usuarios.Usuario;
import java.time.LocalDate;
import java.util.*;
import service.UsuarioService;

public class SaldoEvolucao implements IRelatorio {

    private final UsuarioService usuarioService;

    public SaldoEvolucao(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("\n=== RELATÓRIO DE EVOLUÇÃO DO SALDO ===");

        Map<LocalDate, Double> mapa = new TreeMap<>();

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            for (Transacao t : usuario.getTransacoes()) {
                LocalDate data = t.getData();
                double valor = t.getTipo() == Transacao.TipoTransacao.RECEITA
                        ? t.getValor()
                        : -t.getValor();

                mapa.put(data, mapa.getOrDefault(data, 0.0) + valor);
            }
        }

        double saldoAcumulado = 0.0;

        for (var entry : mapa.entrySet()) {
            saldoAcumulado += entry.getValue();
            int barras = (int) Math.max(0, saldoAcumulado / 10);

            System.out.printf("%s — Saldo: R$ %.2f | %s\n",
                    entry.getKey(),
                    saldoAcumulado,
                    "#".repeat(barras)
            );
        }
    }
}
