package domain.relatorios;

import service.UsuarioService;
import domain.transacoes.Transacao;
import domain.usuarios.Usuario;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RankingDespesas implements IRelatorio {

    private final UsuarioService usuarioService;
    private final LocalDate inicio;
    private final LocalDate fim;

    public RankingDespesas(UsuarioService usuarioService, LocalDate inicio, LocalDate fim) {
        this.usuarioService = usuarioService;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public void gerar() {
        System.out.println("\n=== RANKING DE MAIORES GASTOS ===");

        Map<Usuario, Double> totais = new HashMap<>();

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            double total = usuario.getTransacoes().stream()
                    .filter(t -> t.getTipo() == Transacao.TipoTransacao.DESPESA)
                    .filter(t -> !t.getData().toLocalDate().isBefore(inicio)
                            && !t.getData().toLocalDate().isAfter(fim))
                    .mapToDouble(Transacao::getValor)
                    .sum();

            totais.put(usuario, total);
        }

        List<Map.Entry<Usuario, Double>> ranking = totais.entrySet().stream()
                .sorted(Map.Entry.<Usuario, Double>comparingByValue().reversed())
                .collect(Collectors.toList());

        int posicao = 1;
        for (var entry : ranking) {
            System.out.printf("%dº %s — R$ %.2f\n",
                    posicao++, entry.getKey().getNome(), entry.getValue()
            );
        }
    }
}
