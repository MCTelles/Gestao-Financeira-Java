package domain.usuarios;

public class UsuarioAdmin extends UsuarioIndividual {

    public UsuarioAdmin(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        super(nome, email, senha, telefone, endereco, ativo, conta);
        this.perfil = TipoPerfil.ADMIN;
    }

    @Override
    public boolean temPermissao(Permissao p) {
        return true;
    }
}
