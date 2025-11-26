package domain.metas;

import java.time.LocalDate;

public abstract class Meta {
    private String nome;
    private double valorMeta;
    private double valorAtual;
    private LocalDate prazo;
    private String categoria;
    private boolean atingida;

    // Construtor
    public Meta(String nome, double valorMeta, LocalDate prazo, String categoria) {
        this.nome = nome;
        this.valorMeta = valorMeta;
        this.valorAtual = 0;
        this.prazo = prazo;
        this.categoria = categoria;
        this.atingida = false;
    }

    // Métodos para manipulação da meta
    public void atualizarValor(double valor) {
        this.valorAtual += valor;
        if (this.valorAtual >= valorMeta) {
            this.atingida = true;
        }
    }

    public boolean isAtingida() {
        return atingida;
    }

    public String getNome() {
        return nome;
    }

    public double getValorMeta() {
        return valorMeta;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Meta: " + nome + " | Valor Meta: " + valorMeta + " | Valor Atual: " + valorAtual + " | Prazo: " + prazo + " | Categoria: " + categoria;
    }
}
