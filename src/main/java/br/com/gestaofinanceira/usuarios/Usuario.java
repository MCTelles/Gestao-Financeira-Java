package usuarios;

import java.time.LocalDateTime;
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

    public Usuario(String nome, String email, String senha, String telefone, String endereco) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
        this.creationDateTime = LocalDateTime.now();
        this.ativo = true;
    }

    public abstract void exibirPermissoes();

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format("Usuário: %s | Email: %s | Ativo: %s | Criado em: %s",
                nome, email, ativo ? "Sim" : "Não", creationDateTime);
    }
}
