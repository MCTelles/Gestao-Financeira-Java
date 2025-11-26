package domain.metas;
import java.time.LocalDate;

public class MetaIndividual extends Meta {
    private String usuarioId;  // O id do usuário associado à meta

    public MetaIndividual(String nome, double valorMeta, LocalDate prazo, String categoria, String usuarioId) {
        super(nome, valorMeta, prazo, categoria);
        this.usuarioId = usuarioId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }
}
