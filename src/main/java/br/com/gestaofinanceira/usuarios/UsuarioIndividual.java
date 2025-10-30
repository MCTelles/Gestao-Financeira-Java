package usuarios;

public class UsuarioIndividual extends Usuario {

    private String cpf;

    public UsuarioIndividual(String nome, String email, String senha, String telefone, String endereco, String cpf) {
        super(nome, email, senha, telefone, endereco);
        this.cpf = cpf;
    }

    @Override
    public void exibirPermissoes() {
        System.out.println("Permissões: acessar suas contas e relatórios pessoais.");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
