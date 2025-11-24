package service;

import java.util.List;

import domain.carteira.ContaFactory;
import domain.carteira.ContaFinanceira;
import domain.carteira.ContaFactory.TipoConta;
import domain.usuarios.Usuario;
import persistence.ContaRepository;
import persistence.ContaRepositoryArquivo;

public class    ContaService {

    private final ContaRepository repository = new ContaRepositoryArquivo();
    private final List<ContaFinanceira> contas;

    public ContaService() {
        this.contas = repository.carregar();
    }

    public ContaFinanceira criarConta(TipoConta tipo, String nome, double saldo, double extra, Usuario dono) {

        ContaFinanceira conta = ContaFactory.criarConta(tipo, nome, saldo, dono, extra);

        contas.add(conta);
        repository.salvar(contas);
        return conta;
    }

    public List<ContaFinanceira> listarContas() {
        return contas;
    }

    public ContaFinanceira buscarPorId(String id) {
        return contas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void atualizarPersistencia() {
        repository.salvar(contas);
    }

    public void apagarTudo() {
        repository.apagarTudo();
        contas.clear();
    }
}
