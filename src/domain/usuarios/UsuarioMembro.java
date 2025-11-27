package domain.usuarios;

public class UsuarioMembro extends UsuarioIndividual {

    public UsuarioMembro(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        super(nome, email, senha, telefone, endereco, ativo, conta);
        this.perfil = TipoPerfil.MEMBRO;
    }

    @Override
    public boolean temPermissao(Permissao p) {
        return switch (p) {
            case FAZER_DEPOSITO, FAZER_SAQUE -> true;
            default -> false;
        };
    }
}
