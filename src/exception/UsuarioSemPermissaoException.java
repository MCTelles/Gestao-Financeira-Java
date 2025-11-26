package exception;

public class UsuarioSemPermissaoException extends Exception {
    public UsuarioSemPermissaoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioSemPermissaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
