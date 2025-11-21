package usuarios;
import java.time.*;


public abstract class Usuario {
  protected String id;                // Identificador único (UUID, por exemplo)
  protected String nome;              // Nome do usuário, grupo ou empresa
  protected String email;             // Email de login ou contato
  protected String senha;             // Criptografada, comum a todos os perfis
  protected LocalDateTime creationDateTime;        // Data de criação no sistema
  protected String telefone;          // Telefone ou celular de contato
  protected String endereco;          // Endereço (pode ser físico ou base da operação)
  protected boolean ativo;

  public Usuario(String id, String nome, String email, String senha, LocalDateTime creationDateTime, String telefone,
      String endereco, boolean ativo) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.creationDateTime = creationDateTime;
    this.telefone = telefone;
    this.endereco = endereco;
    this.ativo = ativo;
  }
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
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

  
}
