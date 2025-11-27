package service;

import domain.usuarios.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private static final String ARQUIVO_USUARIOS = "usuarios.json";

    public UsuarioService() {
        carregarUsuarios();
    }

    // ===== CARREGAR USUÁRIOS (JSON MULTILINHA) =====
    private void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {

            StringBuilder bloco = new StringBuilder();
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.equals("[") || linha.equals("]") || linha.equals(",")) 
                    continue;

                bloco.append(linha);

                if (linha.endsWith("}")) {
                    Usuario usuario = parseUsuarioBloco(bloco.toString());
                    if (usuario != null) usuarios.add(usuario);
                    bloco = new StringBuilder();
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public Grupo criarGrupo(String nome, String email, String senha, String telefone, String endereco, boolean ativo) {

        Conta conta = new Conta(1000.0);

        Grupo grupo = new Grupo(nome, email, senha, telefone, endereco, ativo, conta);

        usuarios.add(grupo);
        salvarUsuarios();

        return grupo;
    }

    // ===== SALVAR USUÁRIOS =====
    public void salvarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {

            writer.println("[");
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
                writer.println("    \"tipo\": \"" + u.getPerfil().name() + "\"");
                writer.println("  }" + (i < usuarios.size() - 1 ? "," : ""));
            }

            writer.println("]");

        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }


    // ===== PARSE DO BLOCO JSON =====
    private Usuario parseUsuarioBloco(String bloco) {
        try {
            bloco = bloco.replace("{", "").replace("}", "").trim();

            String[] linhas = bloco.split(",");

            String id = null, nome = null, email = null, senha = null, telefone = null, endereco = null;
            String tipoPerfil = "MEMBRO";
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
                    case "tipo" -> tipoPerfil = valor.toUpperCase();
                }
            }

            Conta conta = new Conta(1000.0);

            return switch (tipoPerfil) {
                case "ADMIN" -> new UsuarioAdmin(nome, email, senha, telefone, endereco, ativo, conta);
                case "MODERADOR" -> new UsuarioModerador(nome, email, senha, telefone, endereco, ativo, conta);
                case "MEMBRO" -> new UsuarioMembro(nome, email, senha, telefone, endereco, ativo, conta);
                case "GRUPO" -> new Grupo(nome, email, senha, telefone, endereco, ativo, conta);
                default -> new UsuarioMembro(nome, email, senha, telefone, endereco, ativo, conta);
            };

        } catch (Exception e) {
            System.out.println("Erro ao ler bloco: " + bloco);
            return null;
        }
    }


    // ===== MÉTODOS DE SERVIÇO =====
    public Usuario criarUsuario(String perfil, String nome, String email, String senha, String telefone, String endereco, boolean ativo) {

        Conta conta = new Conta(1000.0);

        Usuario u = switch (perfil.toUpperCase()) {
            case "ADMIN" -> new UsuarioAdmin(nome, email, senha, telefone, endereco, ativo, conta);
            case "MODERADOR" -> new UsuarioModerador(nome, email, senha, telefone, endereco, ativo, conta);
            case "MEMBRO" -> new UsuarioMembro(nome, email, senha, telefone, endereco, ativo, conta);
            default -> new UsuarioMembro(nome, email, senha, telefone, endereco, ativo, conta);
        };

        usuarios.add(u);
        salvarUsuarios();
        return u;
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorId(String id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}
