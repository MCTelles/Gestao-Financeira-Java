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

    private Usuario parseUsuarioBloco(String bloco) {

        try {
            // Remover as chaves externas
            bloco = bloco.replace("{", "")
                        .replace("}", "")
                        .trim();

            // Separar por vírgula
            String[] linhas = bloco.split(",");

            String id = null, nome = null, email = null, senha = null, telefone = null, endereco = null, tipo = "individual";
            boolean ativo = false;

            for (String l : linhas) {

                String[] partes = l.split(":", 2);
                if (partes.length < 2) continue;

                String chave = partes[0].replace("\"", "").trim();
                String valor = partes[1].replace("\"", "").trim();

                switch (chave) {
                    case "id" -> id = valor;
                    case "nome" -> nome = valor;
                    case "email" -> email = valor;
                    case "senha" -> senha = valor;
                    case "telefone" -> telefone = valor;
                    case "endereco" -> endereco = valor;
                    case "ativo" -> ativo = Boolean.parseBoolean(valor);
                    case "tipo" -> tipo = valor.toLowerCase();
                }
            }

            // Criar conta padrão (simples para carregar persistência)
            Conta conta = new Conta(1000.00);

            if (tipo.equals("grupo")) {
                return new Grupo(nome, email, senha, telefone, endereco, ativo, conta);
            } else {
                return new UsuarioIndividual(nome, email, senha, telefone, endereco, ativo, conta);
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler bloco JSON: " + bloco);
            return null;
        }
    }

    // Carregar usuários do arquivo JSON
    private void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {

            StringBuilder bloco = new StringBuilder();
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                // Ignorar linhas que não fazem parte de objetos
                if (linha.equals("[") || linha.equals("]") || linha.equals(",")) 
                    continue;

                // Acumular conteúdo
                bloco.append(linha);

                // Quando fechar um objeto JSON, processar
                if (linha.endsWith("}")) {
                    Usuario usuario = parseUsuarioBloco(bloco.toString());
                    if (usuario != null) {
                        usuarios.add(usuario);
                    }
                    bloco = new StringBuilder(); // reset bloco
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
       

    // Método para salvar os usuários no arquivo
    public void salvarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {

            writer.println("[");  // início do array JSON

            for (int i = 0; i < usuarios.size(); i++) {

                Usuario u = usuarios.get(i);

                writer.println("  {");
                writer.println("    \"id\": \"" + u.getId() + "\",");
                writer.println("    \"nome\": \"" + u.getNome() + "\",");
                writer.println("    \"email\": \"" + u.getEmail() + "\",");
                writer.println("    \"senha\": \"" + u.getSenha() + "\",");
                writer.println("    \"telefone\": \"" + u.getTelefone() + "\",");
                writer.println("    \"endereco\": \"" + u.getEndereco() + "\",");
                writer.println("    \"ativo\": " + u.isAtivo() + ",");
                writer.println("    \"tipo\": \"" + (u instanceof Grupo ? "grupo" : "individual") + "\"");
                writer.println("  }" + (i < usuarios.size() - 1 ? "," : ""));
            }

            writer.println("]");  // fim do array JSON

        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
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
