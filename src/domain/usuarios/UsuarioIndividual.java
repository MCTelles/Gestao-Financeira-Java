package domain.usuarios;

public class UsuarioIndividual extends Usuario {

    public UsuarioIndividual(String nome, String email, String senha, String telefone, String endereco) {
        super(nome, email, senha, telefone, endereco);
    }

    @Override
    public String getTipo() {
        return "INDIVIDUAL";
    }
}
