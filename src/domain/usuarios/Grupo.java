package domain.usuarios;

import java.time.LocalDateTime;

public class Grupo extends Usuario {
  public Grupo(String id, String nome, String email, String senha, LocalDateTime creationDateTime,
      String telefone, String endereco, boolean ativo) {
    super(id, nome, email, senha, creationDateTime, telefone, endereco, ativo);
    
  }
}
