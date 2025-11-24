package domain.usuarios;

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
        this.creationDateTime = LocalDateTime.now();
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = true;
    }

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

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public abstract String getTipo();
}
