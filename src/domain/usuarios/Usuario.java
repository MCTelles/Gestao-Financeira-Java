package domain.usuarios;

import domain.transacoes.Transacao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Usuario {

    protected final String id;
    protected String nome;
    protected String email;
    protected String senha;
    protected LocalDateTime creationDateTime;
    protected String telefone;
    protected String endereco;
    protected boolean ativo;

    protected Conta conta;

    // Atributo para armazenar as transações do usuário
    protected List<Transacao> transacoes;

    public Usuario(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.creationDateTime = LocalDateTime.now();
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = true;
        this.transacoes = new ArrayList<>();
        this.conta = conta;
    }

    // Métodos para acessar as informações do usuário
    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDateTime getCreationDateTime() { return creationDateTime; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Conta getConta() {
        return conta;
    }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    // Método para acessar as transações do usuário
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    // Método para adicionar uma transação ao usuário
    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public abstract String getTipo();
}
