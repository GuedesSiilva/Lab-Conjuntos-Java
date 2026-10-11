import java.util.Scanner;

public class MenuConjuntosNumericos {
    private final Scanner scanner;

    public MenuConjuntosNumericos() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;
        do {
            System.out.print("""
            === PARTE 2: CLASSIFICADOR DE CONJUNTOS NUMÉRICOS ===
                [1] - Classificar um número (Ex: -3, 3/4, 0.(3), 1.2(5), v9, v2)
                [2] - Bônus: Gerar primos até N (Crivo de Eratóstenes)
                [3] - Bônus: Listar divisores de um número
                [0] - Voltar ao Menu Principal
            """);
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o número para análise (ex: -3, 6/3, 0.(3), 1.2(5), v9, v2): ");
                    String numStr = scanner.nextLine().trim();
                    System.out.println("\n--- RESULTADO DA ANÁLISE ---");
                    System.out.println(ClassificadorNumerico.classificar(numStr));
                    break;

                case 2:
                    System.out.print("Digite o valor limite N (ex: 30): ");
                    try {
                        int n = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println("Primos até " + n + ": " + ClassificadorNumerico.crivoEratostenes(n));
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case 3:
                    System.out.print("Digite um número para ver os divisores (ex: 36): ");
                    try {
                        int n = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println("Divisores de " + n + ": " + ClassificadorNumerico.listarDivisores(n));
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case 0:
                    System.out.println("A voltar...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}