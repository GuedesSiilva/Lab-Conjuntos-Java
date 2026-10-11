import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;
        do {
            System.out.print("""
                ======================================= 
                    Laboratório de Conjuntos - JAVA
                =======================================  
                    [1] - Teoria dos Conjuntos (Menu e Operações)
                    [2] - Classificador de Conjuntos Numéricoseoria dos conjuntos 
                    [3] - Teoria dos conjuntos 
                    [0] - Sair do Programa
                """);
            System.out.print("Escolha uma opção: ");

            String entrada = scan.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    MenuTeoriaConjuntos menuParte1 = new MenuTeoriaConjuntos();
                    menuParte1.iniciar();
                    break;

                case 2:
                    MenuConjuntosNumericos menuParte2 = new MenuConjuntosNumericos();
                    menuParte2.iniciar();
                    break;

                case 0:
                    System.out.println("Programa encerrado!");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scan.close();
    }
}