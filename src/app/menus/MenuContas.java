package app.menus;

import app.IoConsole;
import domain.carteira.ContaFinanceira;
import domain.carteira.ContaFactory.TipoConta;
import domain.usuarios.Usuario;
import service.ContaService;
import service.UsuarioService;
import exception.SaldoInsuficienteException;
import domain.usuarios.Grupo;

public class MenuContas {

    private final ContaService contaService;
    private final UsuarioService usuarioService;
    private ContaFinanceira contaSelecionada;

    public MenuContas(ContaService contaService, UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    public void exibir() {

        while (true) {
            System.out.println("\n=== MENU DE CONTAS ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Selecionar conta");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Mostrar informações");
            System.out.println("0. Voltar");

            int opcao = IoConsole.lerInt("Escolha");

            switch (opcao) {

                case 1 -> criarConta();
                case 2 -> selecionarConta();
                case 3 -> depositar();
                case 4 -> sacar();
                case 5 -> mostrarInfo();
                case 99 -> apagarTudo();
                case 0 -> { return; }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void criarConta() {

        System.out.println("\n=== TIPOS DE CONTA ===");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Digital");
        System.out.println("3. Cartão de Crédito");
        System.out.println("4. Poupança Virtual");  // Mantido como Poupança Virtual
        System.out.println("5. Carteira de Investimento");

        int tipoEscolhido = IoConsole.lerInt("Escolha o tipo:");

        TipoConta tipo = switch (tipoEscolhido) {
            case 1 -> TipoConta.CONTA_CORRENTE;
            case 2 -> TipoConta.CONTA_DIGITAL;
            case 3 -> TipoConta.CARTAO_CREDITO;
            case 4 -> TipoConta.POUPANCA_VIRTUAL;  // Mantido Poupança Virtual
            case 5 -> TipoConta.CARTEIRA_INVESTIMENTO;
            default -> {
                System.out.println("Tipo inválido!");
                yield null;
            }
        };

        if (tipo == null) return;

        String nome = IoConsole.lerTexto("Nome da conta:");
        double saldo = IoConsole.lerDouble("Saldo inicial:");

        double extra = 0;
        if (tipo == TipoConta.CONTA_DIGITAL) {
            extra = IoConsole.lerDouble("Limite diário de transferência:");
        } else if (tipo == TipoConta.CARTAO_CREDITO) {
            extra = IoConsole.lerDouble("Limite de crédito:");
        }

        String idDono = IoConsole.lerTexto("ID do proprietário da conta:");
        Usuario dono = usuarioService.buscarPorId(idDono);

        if (dono == null) {
            System.out.println("Usuário ou grupo não encontrado!");
            return;
        }

        ContaFinanceira conta = contaService.criarConta(tipo, nome, saldo, extra, dono);
        System.out.println("Conta criada: " + conta.getId());
    }

    private void apagarTudo() {
        contaService.apagarTudo();
    }

    private void selecionarConta() {
        var contas = contaService.listarContas();

        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        for (int i = 0; i < contas.size(); i++) {
            ContaFinanceira c = contas.get(i);

            String nomeDono = (c.getDono() != null) ? c.getDono().getNome() : "SEM DONO";

            System.out.println(i + " - " + c.getNome() + " | " + c.getTipoConta() + " | Dono: " + nomeDono);
        }

        int escolha = IoConsole.lerInt("Número da conta:");

        if (escolha < 0 || escolha >= contas.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        contaSelecionada = contas.get(escolha);
        System.out.println("Conta selecionada: " + contaSelecionada.getNome());
    }

    private void depositar() {
        if (contaSelecionada == null) { System.out.println("Selecione uma conta antes."); return; }

        double valor = IoConsole.lerDouble("Valor a depositar:");
        try {
            contaSelecionada.depositar(valor);
            contaService.atualizarPersistencia();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void sacar() {
        if (contaSelecionada == null) { System.out.println("Selecione uma conta antes."); return; }

        double valor = IoConsole.lerDouble("Valor a sacar:");
        try {
            contaSelecionada.sacar(valor);
            contaService.atualizarPersistencia();
        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void mostrarInfo() {
        if (contaSelecionada == null) {
            System.out.println("Selecione uma conta.");
            return;
        }

        System.out.println("\n=== INFORMAÇÕES ===");
        System.out.println("Nome: " + contaSelecionada.getNome());
        System.out.println("Saldo: " + contaSelecionada.getSaldo());
        System.out.println("Tipo: " + contaSelecionada.getTipoConta());
        System.out.println("Dono: " + contaSelecionada.getDono().getNome());

        var dono = contaSelecionada.getDono();
        if (dono instanceof Grupo grupo) {

            System.out.println("--- Grupo e Membros ---");
            grupo.getMembros().forEach(m ->
                    System.out.println("- " + m.getNome() + " (" + m.getEmail() + ")")
            );
        }
    }
}
