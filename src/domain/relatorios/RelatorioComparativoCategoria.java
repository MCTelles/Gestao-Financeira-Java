package domain.relatorios;

import domain.transacoes.Transacao;
import domain.usuarios.Usuario;
import java.util.*;
import java.util.stream.Collectors;
import service.UsuarioService;

public class RelatorioComparativoCategoria implements IRelatorio {

    private final UsuarioService usuarioService;

    public RelatorioComparativoCategoria(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("\n=== RELATÓRIO COMPARATIVO POR CATEGORIA ===");

        Map<String, Double> categorias = new HashMap<>();

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            for (Transacao t : usuario.getTransacoes()) {
                if (t.getTipo() != Transacao.TipoTransacao.DESPESA)
                    continue;

                categorias.put(
                        t.getCategoria(),
                        categorias.getOrDefault(t.getCategoria(), 0.0) + t.getValor()
                );
            }
        }

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma despesa registrada.");
            return;
        }

        double total = categorias.values().stream().mapToDouble(Double::doubleValue).sum();

        List<Map.Entry<String, Double>> ranking = categorias.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toList());

        int posicao = 1;
        for (var entry : ranking) {
            double valor = entry.getValue();
            double percentual = (valor / total) * 100;

            System.out.printf("%dº %s — R$ %.2f (%.2f%%)\n",
                    posicao++, entry.getKey(), valor, percentual
            );
        }
    }
}
