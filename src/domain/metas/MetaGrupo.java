package domain.metas;

import java.time.LocalDate;

public class MetaGrupo extends Meta {
    private String grupoId;  // O id do grupo associado à meta

    public MetaGrupo(String nome, double valorMeta, LocalDate prazo, String categoria, String grupoId) {
        super(nome, valorMeta, prazo, categoria);
        this.grupoId = grupoId;
    }

    public String getGrupoId() {
        return grupoId;
    }
}
