package service;
import domain.metas.Meta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import domain.metas.*;


public class MetaService {
    private List<Meta> metas;

    public MetaService() {
        this.metas = new ArrayList<>();
    }

    public MetaIndividual criarMetaIndividual(String nome, double valorMeta, LocalDate prazo, String categoria, String usuarioId) {
        MetaIndividual meta = new MetaIndividual(nome, valorMeta, prazo, categoria, usuarioId);
        metas.add(meta);
        return meta;
    }

    public MetaGrupo criarMetaGrupo(String nome, double valorMeta, LocalDate prazo, String categoria, String grupoId) {
        MetaGrupo meta = new MetaGrupo(nome, valorMeta, prazo, categoria, grupoId);
        metas.add(meta);
        return meta;
    }

    public List<Meta> listarMetas() {
        return metas;
    }

    public void atualizarMeta(Meta meta, double valor) {
        meta.atualizarValor(valor);
    }

    public void verificarMetas() {
        for (Meta meta : metas) {
            if (meta.isAtingida()) {
                System.out.println("Meta atingida: " + meta.getNome());
            }
        }
    }
}
