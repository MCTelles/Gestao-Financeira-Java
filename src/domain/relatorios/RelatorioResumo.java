package domain.relatorios;

import service.UsuarioService;
import domain.transacoes.Transacao;
import domain.usuarios.Usuario;

import java.util.*;

public class RelatorioResumo implements IRelatorio {

    private final UsuarioService usuarioService;

    public RelatorioResumo(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void gerar() {
        System.out.println("\n=== RELATÓRIO RESUMO ===");

        double totalReceitas = 0.0;
        double totalDespesas = 0.0;

        List<Transacao> despesas = new ArrayList<>();
        Map<String, Double> categorias = new HashMap<>();

        for (Usuario usuario : usuarioService.listarUsuarios()) {
            for (Transacao t : usuario.getTransacoes()) {

                if (t.getTipo() == Transacao.TipoTransacao.RECEITA) {
                    totalReceitas += t.getValor();
                } else {
                    totalDespesas += t.getValor();
                    despesas.add(t);
                    categorias.put(
                            t.getCategoria(),
                            categorias.getOrDefault(t.getCategoria(), 0.0) + t.getValor()
                    );
                }
            }
        }

        System.out.printf("Total de Receitas: R$ %.2f\n", totalReceitas);
        System.out.printf("Total de Despesas: R$ %.2f\n", totalDespesas);
        System.out.printf("Saldo Geral: R$ %.2f\n", (totalReceitas - totalDespesas));

        despesas.sort(Comparator.comparingDouble(Transacao::getValor).reversed());

        System.out.println("\nMaiores despesas:");
        for (int i = 0; i < Math.min(3, despesas.size()); i++) {
            System.out.println("- " + despesas.get(i).getDescricao()
                    + " — R$ " + despesas.get(i).getValor());
        }

        if (!categorias.isEmpty()) {
            String cat = categorias.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get().getKey();

            System.out.println("\nCategoria dominante: " + cat);
        }
    }
}
