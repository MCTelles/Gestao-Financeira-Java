package persistence;

import domain.carteira.ContaFinanceira;
import java.util.List;

public interface ContaRepository {

    List<ContaFinanceira> carregar();

    void salvar(java.util.List<ContaFinanceira> contas);

    void apagarTudo();
}
