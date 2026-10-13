import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;
        do {
            System.out.print("""
                ============================================================ 
                             LABORATÓRIO DE CONJUNTOS - JAVA
                ============================================================  
                    [1] - Teoria dos Conjuntos (Menu e Operações)
                    [2] - Classificador de Conjuntos Numéricos
                    [3] - Mini-projeto: Sistema de Recomendação (Jaccard)
                    [4] - Executar Bateria de Testes Automatizada
                    [0] - Sair do Programa
                ============================================================
                """);
            System.out.print("Escolha uma opção: ");

            String entrada = scan.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();

            switch (opcao) {
                case 1:
                    MenuTeoriaConjuntos menuParte1 = new MenuTeoriaConjuntos();
                    menuParte1.iniciar();
                    break;

                case 2:
                    MenuConjuntosNumericos menuParte2 = new MenuConjuntosNumericos();
                    menuParte2.iniciar();
                    break;

                case 3:
                    MenuRecomendacao menuParte3 = new MenuRecomendacao();
                    menuParte3.iniciar();
                    break;

                case 4:
                    BateriaTestes.executarTodosOsTestes();
                    break;

                case 0:
                    System.out.println("Programa encerrado com sucesso. Bons estudos!");
                    break;

                default:
                    System.out.println("Opção inválida. Digite um número de 0 a 4.");
            }
        } while (opcao != 0);

        scan.close();
    }
}
