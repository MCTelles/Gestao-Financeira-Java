package persistence;

import java.util.List;
import domain.carteira.ContaFinanceira;

public interface ContaRepository {
    void salvar(List<ContaFinanceira> contas);
    List<ContaFinanceira> carregar();
    void apagarTudo();
}
