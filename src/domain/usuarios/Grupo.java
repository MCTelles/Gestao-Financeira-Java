package domain.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Grupo extends Usuario {

    private final List<Usuario> membros = new ArrayList<>();

    public Grupo(String nome, String email, String senha, String telefone, String endereco) {
        super(nome, email, senha, telefone, endereco);
    }

    public void adicionarMembro(Usuario usuario) {
        membros.add(usuario);
    }

    public List<Usuario> getMembros() {
        return membros;
    }

    @Override
    public String getTipo() {
        return "GRUPO";
    }
}
