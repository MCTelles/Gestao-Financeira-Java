package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import domain.carteira.*;
import domain.carteira.ContaFactory.TipoConta;
import domain.usuarios.Usuario;
import service.UsuarioService;

public class ContaRepositoryArquivo implements ContaRepository {

    private static final String ARQUIVO = "contas.json";

    // Precisamos do UsuarioService para reconstruir os donos
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void salvar(List<ContaFinanceira> contas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {

            writer.println("[");

            for (int i = 0; i < contas.size(); i++) {
                ContaFinanceira c = contas.get(i);

                // Adiciona o id do dono ao salvar
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

                    // Aqui, buscamos o dono a partir do donoId
                    Usuario dono = usuarioService.buscarPorId(donoId);

                    // Reconstruímos a conta utilizando o tipo e dono
                    ContaFinanceira conta = reconstruirConta(tipo, nome, saldo, dono);

                    contas.add(conta);

                    // Resetando valores para a próxima conta
                    id = nome = tipo = donoId = "";
                    saldo = 0;
                }
            }

        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // arquivo ainda não existe
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

    private ContaFinanceira reconstruirConta(String tipoConta, String nome, double saldo, Usuario dono) {

        TipoConta tipo = switch (tipoConta) {

            case "Conta Corrente" -> TipoConta.CONTA_CORRENTE;
            case "Conta Digital" -> TipoConta.CONTA_DIGITAL;
            case "Cartão de Crédito" -> TipoConta.CARTAO_CREDITO;
            case "Poupança Virtual" -> TipoConta.POUPANCA_VIRTUAL;
            case "Carteira de Investimento" -> TipoConta.CARTEIRA_INVESTIMENTO;

            default -> null;
        };

        if (tipo == null) return null;

        double parametroExtra = 0;

        return ContaFactory.criarConta(tipo, nome, saldo, dono, parametroExtra);
    }

    public void apagarTudo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            writer.println("[]");
            System.out.println("Arquivo de contas apagado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao apagar contas: " + e.getMessage());
        }
    }
}
