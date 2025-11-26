package exception;

public class CategoriaNaoEncontradaException extends Exception {

    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    public CategoriaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
