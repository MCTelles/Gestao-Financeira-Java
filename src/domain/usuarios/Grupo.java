package domain.usuarios;

import domain.transacoes.Transacao;
import java.util.ArrayList;
import java.util.List;

public class Grupo extends Usuario {

    private final List<Usuario> membros = new ArrayList<>();

    public Grupo(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        super(nome, email, senha, telefone, endereco, ativo, conta);
    }

    // Adiciona um membro ao grupo
    public void adicionarMembro(Usuario usuario) {
        membros.add(usuario);
    }

    // Retorna os membros do grupo
    public List<Usuario> getMembros() {
        return membros;
    }

    // Retorna a lista de transações de todos os membros do grupo
    public List<Transacao> getTransacoes() {
        List<Transacao> todasTransacoes = new ArrayList<>();
        // Itera sobre os membros e adiciona as transações de cada um
        for (Usuario membro : membros) {
            todasTransacoes.addAll(membro.getTransacoes());
        }
        return todasTransacoes;
    }

    @Override
    public String getTipo() {
        return "GRUPO";
    }
}
