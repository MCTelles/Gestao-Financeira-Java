package persistence;

import domain.carteira.ContaFactory;
import domain.carteira.ContaFactory.TipoConta;
import domain.carteira.ContaFinanceira;
import domain.usuarios.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import service.UsuarioService;

public class ContaRepositoryArquivo implements ContaRepository {

    private static final String ARQUIVO = "contas.json";

    private final UsuarioService usuarioService;

    public ContaRepositoryArquivo(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void salvar(List<ContaFinanceira> contas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {

            writer.println("[");
            for (int i = 0; i < contas.size(); i++) {
                ContaFinanceira c = contas.get(i);

                writer.println("  {");
                writer.println("    \"id\": \"" + c.getId() + "\",");
                writer.println("    \"nome\": \"" + c.getNome() + "\",");
                writer.println("    \"saldo\": " + c.getSaldo() + ",");
                writer.println("    \"tipo\": \"" + c.getTipoConta() + "\",");
                writer.println("    \"donoId\": \"" + (c.getDono() != null ? c.getDono().getId() : "null") + "\"");
                writer.println("  }" + (i < contas.size() - 1 ? "," : ""));
            }
            writer.println("]");

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @Override
    public List<ContaFinanceira> carregar() {
        List<ContaFinanceira> contas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {

            String linha;
            String id = "", nome = "", tipo = "", donoId = "";
            double saldo = 0;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("\"id\"")) {
                    id = extrairValor(linha);
                } else if (linha.startsWith("\"nome\"")) {
                    nome = extrairValor(linha);
                } else if (linha.startsWith("\"saldo\"")) {
                    saldo = Double.parseDouble(linha.replace("\"saldo\":", "").replace(",", "").trim());
                } else if (linha.startsWith("\"tipo\"")) {
                    tipo = extrairValor(linha);
                } else if (linha.startsWith("\"donoId\"")) {
                    donoId = extrairValor(linha);
                } else if (linha.equals("},") || linha.equals("}")) {

                    Usuario dono = "null".equalsIgnoreCase(donoId)
                            ? null
                            : usuarioService.buscarPorId(donoId);

                    ContaFinanceira conta = reconstruirConta(id, tipo, nome, saldo, dono);

                    if (conta != null) {
                        contas.add(conta);
                    }

                    id = nome = tipo = donoId = "";
                    saldo = 0;
                }
            }

        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }

        return contas;
    }

    private String extrairValor(String linha) {
        return linha.split(":")[1]
                .replace("\"", "")
                .replace(",", "")
                .trim();
    }

    private ContaFinanceira reconstruirConta(String id, String tipoConta, String nome, double saldo, Usuario dono) {

        TipoConta tipo = switch (tipoConta) {
            case "Conta Corrente" -> TipoConta.CONTA_CORRENTE;
            case "Conta Digital" -> TipoConta.CONTA_DIGITAL;
            case "Cartão de Crédito" -> TipoConta.CARTAO_CREDITO;
            case "Poupança Virtual" -> TipoConta.POUPANCA_VIRTUAL;
            case "Carteira de Investimento" -> TipoConta.CARTEIRA_INVESTIMENTO;
            default -> null;
        };

        if (tipo == null) return null;

        double extra = 0; // se quiser, depois lê do JSON

        return ContaFactory.criarContaComId(id, tipo, nome, saldo, dono, extra);
    }

    @Override
    public void apagarTudo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            writer.println("[]");
        } catch (IOException e) {
            System.out.println("Erro ao apagar contas: " + e.getMessage());
        }
    }
}
