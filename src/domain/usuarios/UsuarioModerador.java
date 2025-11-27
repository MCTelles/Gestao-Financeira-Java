package domain.usuarios;

public class UsuarioModerador extends UsuarioIndividual {

    public UsuarioModerador(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        super(nome, email, senha, telefone, endereco, ativo, conta);
        this.perfil = TipoPerfil.MODERADOR;
    }

    @Override
    public boolean temPermissao(Permissao p) {
        return switch (p) {
            case FAZER_DEPOSITO, FAZER_SAQUE, CRIAR_CONTA, VER_RELATORIOS -> true;
            default -> false;
        };
    }
}
