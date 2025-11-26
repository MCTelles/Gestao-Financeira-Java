package domain.usuarios;

public class UsuarioIndividual extends Usuario {

    public UsuarioIndividual(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        super(nome, email, senha, telefone, endereco, ativo, conta);
    }

    @Override
    public String getTipo() {
        return "INDIVIDUAL";
    }
}
