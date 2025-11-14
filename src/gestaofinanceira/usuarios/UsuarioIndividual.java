package usuarios;

import java.time.LocalDateTime;

public class UsuarioIndividual extends Usuario {

  public UsuarioIndividual(String id, String nome, String email, String senha, LocalDateTime creationDateTime,
      String telefone, String endereco, boolean ativo) {
    super(id, nome, email, senha, creationDateTime, telefone, endereco, ativo);
    
  }

}