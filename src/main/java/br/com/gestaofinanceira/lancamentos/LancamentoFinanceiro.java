package lancamentos;

import java.time.LocalDate;
import java.util.UUID;
import br.com.gestaofinanceira.usuarios.Usuario;
import br.com.gestaofinanceira.carteira.ContaFinanceira;

public class LancamentoFinanceiro {

    public enum Tipo {
        RECEITA, DESPESA, TRANSFERENCIA
    }

    private final String id;
    private Tipo tipo;
    private String categoria;
    private String subcategoria;
    private double valor;
    private LocalDate data;
    private boolean recorrente;
    private Usuario pagador;
    private Usuario beneficiario;
    private ContaFinanceira contaOrigem;
    private ContaFinanceira contaDestino;
    private boolean estornado;

    public LancamentoFinanceiro(Tipo tipo, String categoria, String subcategoria,
                                double valor, LocalDate data, boolean recorrente,
                                Usuario pagador, Usuario beneficiario,
                                ContaFinanceira contaOrigem, ContaFinanceira contaDestino) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.valor = valor;
        this.data = data;
        this.recorrente = recorrente;
        this.pagador = pagador;
        this.beneficiario = beneficiario;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.estornado = false;
    }

    // ----------- Métodos principais -----------

    public void efetuarLancamento() throws Exception {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser maior que zero.");

        switch (tipo) {
            case RECEITA -> contaDestino.creditar(valor);
            case DESPESA -> contaOrigem.debitar(valor);
            case TRANSFERENCIA -> {
                contaOrigem.debitar(valor);
                contaDestino.creditar(valor);
            }
        }
    }

    public void estornar() throws Exception {
        if (estornado) throw new IllegalStateException("Lançamento já estornado!");
        switch (tipo) {
            case RECEITA -> contaDestino.debitar(valor);
            case DESPESA -> contaOrigem.creditar(valor);
            case TRANSFERENCIA -> {
                contaDestino.debitar(valor);
                contaOrigem.creditar(valor);
            }
        }
        estornado = true;
    }

    // Parcelamento simples
    public LancamentoFinanceiro[] parcelar(int parcelas) {
        if (parcelas <= 1) throw new IllegalArgumentException("Parcelas deve ser maior que 1.");
        double valorParcela = valor / parcelas;
        LancamentoFinanceiro[] lista = new LancamentoFinanceiro[parcelas];
        for (int i = 0; i < parcelas; i++) {
            lista[i] = new LancamentoFinanceiro(
                this.tipo,
                this.categoria,
                this.subcategoria + " - Parcela " + (i + 1),
                valorParcela,
                this.data.plusMonths(i),
                this.recorrente,
                this.pagador,
                this.beneficiario,
                this.contaOrigem,
                this.contaDestino
            );
        }
        return lista;
    }

    // ----------- Getters e Setters -----------

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public Usuario getPagador() {
        return pagador;
    }

    public Usuario getBeneficiario() {
        return beneficiario;
    }

    public boolean isEstornado() {
        return estornado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$%.2f (%s/%s)", 
            tipo, categoria, valor, pagador.getNome(), beneficiario.getNome());
    }
}
