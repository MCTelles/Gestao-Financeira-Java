package domain.relatorios;

public class GerenciadorRelatorios {

    public void gerarRelatorio(IRelatorio relatorio) {
        if (relatorio != null) {
            relatorio.gerar();  // Chama o método de geração do relatório
        } else {
            System.out.println("Relatório inválido ou nulo.");
        }
    }

    // Exemplo de como você pode gerar diferentes relatórios com base em entradas
    public void gerarRelatorio(int tipoRelatorio, IRelatorio relatorio) {
        switch (tipoRelatorio) {
            case 1:
                // Gera o relatório do tipo 1
                System.out.println("Gerando relatório de tipo 1...");
                relatorio.gerar();
                break;
            case 2:
                // Gera o relatório do tipo 2
                System.out.println("Gerando relatório de tipo 2...");
                relatorio.gerar();
                break;
            default:
                System.out.println("Tipo de relatório não reconhecido.");
        }
    }
}
