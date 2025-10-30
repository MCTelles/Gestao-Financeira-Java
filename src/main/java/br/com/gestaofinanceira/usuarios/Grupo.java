package usuarios;

import java.util.ArrayList;
import java.util.List;

public class Grupo extends Usuario {

    private List<UsuarioIndividual> membros = new ArrayList<>();

    public Grupo(String nome, String email, String senha, String telefone, String endereco) {
        super(nome, email, senha, telefone, endereco);
    }

    public void adicionarMembro(UsuarioIndividual membro) {
        membros.add(membro);
    }

    public void removerMembro(UsuarioIndividual membro) {
        membros.remove(membro);
    }

    public List<UsuarioIndividual> getMembros() {
        return membros;
    }

    @Override
    public void exibirPermissoes() {
        System.out.println("Permissões: gerenciar contas e lançamentos do grupo.");
    }
}
