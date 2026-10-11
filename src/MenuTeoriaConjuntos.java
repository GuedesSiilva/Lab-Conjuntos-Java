import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MenuTeoriaConjuntos {
    private final Scanner scanner;
    private Conjuntos<Integer> conjuntoA;
    private Conjuntos<Integer> conjuntoB;
    private Conjuntos<Integer> universo;

    public MenuTeoriaConjuntos() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar () {
        System.out.println("=== Laboratório de Conjuntos ===");
        System.out.println("Parte 1: Teoria dos Conjuntos \n");

        this.conjuntoA = pedirConjunto("Conjunto A");
        this.conjuntoB = pedirConjunto("Conjunto B");
        this.universo = pedirConjunto("Conjunto Universo (U)");

        exibirMenu();
    }

    private void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- MENU DE OPERAÇÕES ---");
            System.out.println("1. Mostrar Conjuntos");
            System.out.println("2. União (A ∪ B)");
            System.out.println("3. Intersecção (A ∩ B)");
            System.out.println("4. Diferença (A - B e B - A)");
            System.out.println("5. Diferença Simétrica (A Δ B)");
            System.out.println("6. Verificações (Pertinência e Inclusão)");
            System.out.println("7. Cardinalidade e Complemento");
            System.out.println("8. Comparar com Validação Nativa (java.util.Set)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String entradaOpcao = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entradaOpcao);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();
            processarOpcao(opcao);

        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("A = " + conjuntoA);
                System.out.println("B = " + conjuntoB);
                System.out.println("U = " + universo);
                break;

            case 2:
                System.out.println("União (A ∪ B): " + conjuntoA.uniao(conjuntoB));
                break;

            case 3:
                System.out.println("Intersecção (A ∩ B): " + conjuntoA.intersecao(conjuntoB));
                break;

            case 4:
                System.out.println("Diferença (A - B): " + conjuntoA.diferenca(conjuntoB));
                System.out.println("Diferença (B - A): " + conjuntoB.diferenca(conjuntoA));
                break;

            case 5:
                System.out.println("Diferença Simétrica (A Δ B): " + conjuntoA.diferencaSimetrica(conjuntoB));
                break;

            case 6:
                System.out.print("Digite um elemento para verificar se pertence a A: ");
                String entradaElem = scanner.nextLine().trim();
                try {
                    int elem = Integer.parseInt(entradaElem);
                    System.out.println(elem + " ∈ A ? " + conjuntoA.pertence(elem));
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido inserido.");
                }

                System.out.println("A ⊆ B (A é subconjunto de B)? " + conjuntoA.ehSubconjunto(conjuntoB));
                System.out.println("A ⊂ B (A é subconjunto próprio de B)? " + conjuntoA.ehSubconjuntoProprio(conjuntoB));
                break;

            case 7:
                System.out.println("|A| (Cardinalidade de A): " + conjuntoA.cardinalidade());
                System.out.println("|B| (Cardinalidade de B): " + conjuntoB.cardinalidade());
                System.out.println("Complemento de A (A^c): " + conjuntoA.complemento(universo));
                break;

            case 8:
                validarComNativo();
                break;

            case 0:
                System.out.println("A encerrar a Parte 1...");
                break;

            default:
                System.out.println("Opção inválida! Escolha um número do menu.");
        }
    }

    private Conjuntos<Integer> pedirConjunto(String nomeConjunto) {
        Conjuntos<Integer> conjunto = new Conjuntos<>();
        System.out.print("Digite os elementos do " + nomeConjunto + " separados por espaço (ex: 1 2 3 4): ");
        String linha = scanner.nextLine();

        if (linha != null && !linha.trim().isEmpty()) {
            String[] partes = linha.trim().split("\\s+");
            for (String p : partes) {
                try {
                    int val = Integer.parseInt(p);
                    conjunto.adicionar(val);
                } catch (NumberFormatException e) {
                    System.out.println("Aviso: '" + p + "' não é um número válido e foi ignorado.");
                }
            }
        }
        return conjunto;
    }

    private void validarComNativo() {
        Set<Integer> setA = new HashSet<>(conjuntoA.getElementos());
        Set<Integer> setB = new HashSet<>(conjuntoB.getElementos());

        System.out.println("=== VALIDAÇÃO COM JAVA.UTIL.SET NATIVO ===");

        Set<Integer> uniaoNativa = new HashSet<>(setA);
        uniaoNativa.addAll(setB);
        System.out.println("União Nativa (HashSet): " + uniaoNativa);
        System.out.println("Nossa União:             " + conjuntoA.uniao(conjuntoB));

        Set<Integer> intersecaoNativa = new HashSet<>(setA);
        intersecaoNativa.retainAll(setB);
        System.out.println("Intersecção Nativa (HashSet): " + intersecaoNativa);
        System.out.println("Nossa Intersecção:             " + conjuntoA.intersecao(conjuntoB));
    }
}
