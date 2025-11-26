package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class IoConsole {

    private static final Scanner scanner = new Scanner(System.in);

    public static String lerTexto(String msg) {
        System.out.print(msg + ": ");
        return scanner.nextLine();
    }

    public static LocalDate lerData(String mensagem) {
        System.out.print(mensagem + " (formato: YYYY-MM-DD): ");
        String dataString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dataString, formatter); // Converte a string para LocalDate
    }

    public static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    public static double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg + ": ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
    }
}