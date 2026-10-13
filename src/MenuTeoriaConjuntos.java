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
            System.out.print("""
            === MENU DE OPERAÇÕES ===
                [1] - Mostrar Conjuntos (A, B e U)
                [2] - União (A ∪ B)
                [3] - Intersecção (A ∩ B)
                [4] - Diferença (A - B e B - A)
                [5] - Diferença Simétrica (A Δ B)
                [6] - Verificações (Pertinência e Inclusão)
                [7] - Cardinalidade e Complemento
                [8] - Comparar com Validação Nativa (java.util.Set)
                [0] - Voltar ao Menu Principal
            """);
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
                System.out.println("Complemento de A (A^c = U - A): " + conjuntoA.complemento(universo));
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
        Set<Integer> setU = new HashSet<>(universo.getElementos());

        System.out.println("==============================================================");
        System.out.println("      VALIDAÇÃO CRUZADA: NOSSA CLASSE vs. JAVA.UTIL.SET       ");
        System.out.println("==============================================================");

        // 1. União
        Set<Integer> uniaoNativa = new HashSet<>(setA);
        uniaoNativa.addAll(setB);
        Set<Integer> nossaUniaoSet = new HashSet<>(conjuntoA.uniao(conjuntoB).getElementos());
        System.out.println("1. União (A ∪ B):");
        System.out.println("   - Implementação Própria : " + conjuntoA.uniao(conjuntoB));
        System.out.println("   - java.util.HashSet     : " + uniaoNativa);
        System.out.println("   - Validação             : " + (nossaUniaoSet.equals(uniaoNativa) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 2. Intersecção
        Set<Integer> intersecaoNativa = new HashSet<>(setA);
        intersecaoNativa.retainAll(setB);
        Set<Integer> nossaIntersecaoSet = new HashSet<>(conjuntoA.intersecao(conjuntoB).getElementos());
        System.out.println("\n2. Intersecção (A ∩ B):");
        System.out.println("   - Implementação Própria : " + conjuntoA.intersecao(conjuntoB));
        System.out.println("   - java.util.HashSet     : " + intersecaoNativa);
        System.out.println("   - Validação             : " + (nossaIntersecaoSet.equals(intersecaoNativa) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 3. Diferença A - B
        Set<Integer> difABNativa = new HashSet<>(setA);
        difABNativa.removeAll(setB);
        Set<Integer> nossaDifABSet = new HashSet<>(conjuntoA.diferenca(conjuntoB).getElementos());
        System.out.println("\n3. Diferença (A - B):");
        System.out.println("   - Implementação Própria : " + conjuntoA.diferenca(conjuntoB));
        System.out.println("   - java.util.HashSet     : " + difABNativa);
        System.out.println("   - Validação             : " + (nossaDifABSet.equals(difABNativa) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 4. Diferença B - A
        Set<Integer> difBANativa = new HashSet<>(setB);
        difBANativa.removeAll(setA);
        Set<Integer> nossaDifBASet = new HashSet<>(conjuntoB.diferenca(conjuntoA).getElementos());
        System.out.println("\n4. Diferença (B - A):");
        System.out.println("   - Implementação Própria : " + conjuntoB.diferenca(conjuntoA));
        System.out.println("   - java.util.HashSet     : " + difBANativa);
        System.out.println("   - Validação             : " + (nossaDifBASet.equals(difBANativa) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 5. Diferença Simétrica
        Set<Integer> difSimNativa = new HashSet<>(difABNativa);
        difSimNativa.addAll(difBANativa);
        Set<Integer> nossaDifSimSet = new HashSet<>(conjuntoA.diferencaSimetrica(conjuntoB).getElementos());
        System.out.println("\n5. Diferença Simétrica (A Δ B):");
        System.out.println("   - Implementação Própria : " + conjuntoA.diferencaSimetrica(conjuntoB));
        System.out.println("   - java.util.HashSet     : " + difSimNativa);
        System.out.println("   - Validação             : " + (nossaDifSimSet.equals(difSimNativa) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 6. Subconjunto A ⊆ B
        boolean subNativo = setB.containsAll(setA);
        boolean nossoSub = conjuntoA.ehSubconjunto(conjuntoB);
        System.out.println("\n6. Subconjunto (A ⊆ B):");
        System.out.println("   - Implementação Própria : " + nossoSub);
        System.out.println("   - java.util.HashSet     : " + subNativo);
        System.out.println("   - Validação             : " + (nossoSub == subNativo ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 7. Cardinalidade
        int cardNativaA = setA.size();
        int nossaCardA = conjuntoA.cardinalidade();
        System.out.println("\n7. Cardinalidade (|A|):");
        System.out.println("   - Implementação Própria : " + nossaCardA);
        System.out.println("   - java.util.HashSet     : " + cardNativaA);
        System.out.println("   - Validação             : " + (nossaCardA == cardNativaA ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));

        // 8. Complemento (U - A)
        Set<Integer> compNativo = new HashSet<>(setU);
        compNativo.removeAll(setA);
        Set<Integer> nossoCompSet = new HashSet<>(conjuntoA.complemento(universo).getElementos());
        System.out.println("\n8. Complemento de A em U (A^c = U - A):");
        System.out.println("   - Implementação Própria : " + conjuntoA.complemento(universo));
        System.out.println("   - java.util.HashSet     : " + compNativo);
        System.out.println("   - Validação             : " + (nossoCompSet.equals(compNativo) ? "CORRETO [OK]" : "DIVERGENTE [ERRO]"));
        System.out.println("==============================================================\n");
    }
}
