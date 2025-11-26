package service;

import domain.usuarios.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private static final String ARQUIVO_USUARIOS = "usuarios.json";

    public UsuarioService() {
        carregarUsuarios();  // Carrega os usuários ao iniciar o serviço
    }

    // Carregar usuários do arquivo JSON
    private void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Processar cada linha do arquivo como um usuário
                Usuario usuario = parseUsuario(linha);
                if (usuario != null) {
                    usuarios.add(usuario);  // Adiciona o usuário à lista
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // Método para salvar os usuários no arquivo
    public void salvarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            writer.println("[");  // Inicia o array JSON
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                writer.println("  {");
                writer.println("    \"id\": \"" + u.getId() + "\",");
                writer.println("    \"nome\": \"" + u.getNome() + "\",");
                writer.println("    \"email\": \"" + u.getEmail() + "\",");
                writer.println("    \"senha\": \"" + u.getSenha() + "\",");
                writer.println("    \"telefone\": \"" + u.getTelefone() + "\",");
                writer.println("    \"endereco\": \"" + u.getEndereco() + "\",");
                writer.println("    \"ativo\": " + u.isAtivo());
                writer.println("  }" + (i < usuarios.size() - 1 ? "," : ""));
            }
            writer.println("]");  // Fecha o array JSON
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    // Método auxiliar para converter linha JSON em objeto Usuario
    private Usuario parseUsuario(String linha) {
        // Verifica se a linha é vazia ou malformada
        if (linha == null || linha.isEmpty()) {
            System.out.println("Erro: Linha vazia ou inválida.");
            return null;
        }

        // Remover chaves "{" e "}"
        linha = linha.replace("{", "").replace("}", "").trim();

        // Dividir os dados por vírgulas
        String[] dados = linha.split(",");

        // Verificar se a linha tem o número correto de elementos
        if (dados.length < 7) {
            System.out.println("Erro: Linha com dados insuficientes.");
            return null;
        }

        try {
            // Manipular cada campo individualmente
            String id = dados[0].split(":")[1].replace("\"", "").trim();
            String nome = dados[1].split(":")[1].replace("\"", "").trim();
            String email = dados[2].split(":")[1].replace("\"", "").trim();
            String senha = dados[3].split(":")[1].replace("\"", "").trim();
            String telefone = dados[4].split(":")[1].replace("\"", "").trim();
            String endereco = dados[5].split(":")[1].replace("\"", "").trim();
            boolean ativo = Boolean.parseBoolean(dados[6].split(":")[1].replace("}", "").trim());
            Conta conta = new Conta(1000.00);

            // Retornar o usuário
            return new UsuarioIndividual(nome, email, senha, telefone, endereco, ativo, conta);

        } catch (Exception e) {
            System.out.println("Erro ao processar a linha: " + linha);
            e.printStackTrace();
            return null;
        }
    }

    // Método para criar usuário individual
    public Usuario criarUsuarioIndividual(String nome, String email, String senha, String telefone, String endereco, boolean ativo) {
        Conta conta = new Conta(1000.00);
        Usuario u = new UsuarioIndividual(nome, email, senha, telefone, endereco, ativo, conta);
        usuarios.add(u);
        salvarUsuarios();  // Salva os usuários no arquivo após criação
        return u;
    }

    // Método para criar grupo
    public Grupo criarGrupo(String nome, String email, String senha, String telefone, String endereco, boolean ativo, Conta conta) {
        Grupo g = new Grupo(nome, email, senha, telefone, endereco, ativo, conta);
        usuarios.add(g);
        salvarUsuarios();  // Salva os usuários no arquivo após criação
        return g;
    }

    // Método para listar usuários
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Método para buscar usuário por ID
    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Método para adicionar membro ao grupo
    public void adicionarMembroAoGrupo(Grupo grupo, Usuario membro) {
        grupo.adicionarMembro(membro);
    }
}
