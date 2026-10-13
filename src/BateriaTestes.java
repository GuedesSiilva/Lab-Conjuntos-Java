import java.util.Arrays;
import java.util.List;

/**
 * Bateria de Testes Automatizados para validação de todas as exigências do Laboratório:
 * - Cenários da Parte 1 (Geral, Vazio, Iguais, Disjuntos, Inclusão Total, Repetição)
 * - Casos de Teste da Parte 2 (Inteiro negativo, Zero, Frações, Dízimas, Raízes)
 * - Casos de Teste da Parte 3 (Similaridade de Jaccard e Recomendação)
 */
public class BateriaTestes {

    public static void main(String[] args) {
        executarTodosOsTestes();
    }

    public static void executarTodosOsTestes() {
        System.out.println("================================================================================");
        System.out.println("                BATERIA DE TESTES AUTOMATIZADOS DO PROJETO                      ");
        System.out.println("================================================================================\n");

        executarTestesParte1();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        executarTestesParte2();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        executarTestesParte3();
        System.out.println("\n================================================================================");
        System.out.println("             TODOS OS TESTES FORAM EXECUTADOS COM SUCESSO!                     ");
        System.out.println("================================================================================\n");
    }

    private static void executarTestesParte1() {
        System.out.println(">>> [PARTE 1: TESTES OBRIGATÓRIOS DE TEORIA DOS CONJUNTOS] <<<");

        // Cenário 1: Caso Geral com interseção parcial
        Conjuntos<Integer> a1 = new Conjuntos<>();
        Conjuntos<Integer> b1 = new Conjuntos<>();
        for (int x : new int[]{1, 2, 3, 4}) a1.adicionar(x);
        for (int x : new int[]{3, 4, 5, 6}) b1.adicionar(x);
        System.out.println("\n1. Caso Geral: A = {1, 2, 3, 4}, B = {3, 4, 5, 6}");
        System.out.println("   • União (A ∪ B)               : " + a1.uniao(b1));
        System.out.println("   • Intersecção (A ∩ B)         : " + a1.intersecao(b1));
        System.out.println("   • Diferença (A - B)           : " + a1.diferenca(b1));
        System.out.println("   • Diferença (B - A)           : " + b1.diferenca(a1));
        System.out.println("   • Diferença Simétrica (A Δ B) : " + a1.diferencaSimetrica(b1));

        // Cenário 2: Conjunto Vazio
        Conjuntos<Integer> aVazio = new Conjuntos<>();
        Conjuntos<Integer> bNaoVazio = new Conjuntos<>();
        bNaoVazio.adicionar(10);
        bNaoVazio.adicionar(20);
        System.out.println("\n2. Conjunto Vazio: A = ∅, B = {10, 20}");
        System.out.println("   • União (A ∪ B)       : " + aVazio.uniao(bNaoVazio));
        System.out.println("   • Intersecção (A ∩ B) : " + aVazio.intersecao(bNaoVazio));
        System.out.println("   • A ⊆ B               : " + aVazio.ehSubconjunto(bNaoVazio) + " (O conjunto vazio é subconjunto de qualquer conjunto)");

        // Cenário 3: Conjuntos Iguais
        Conjuntos<Integer> aIgual = new Conjuntos<>();
        Conjuntos<Integer> bIgual = new Conjuntos<>();
        for (int x : new int[]{1, 2, 3}) {
            aIgual.adicionar(x);
            bIgual.adicionar(x);
        }
        System.out.println("\n3. Conjuntos Iguais: A = {1, 2, 3}, B = {1, 2, 3}");
        System.out.println("   • A - B               : " + aIgual.diferenca(bIgual));
        System.out.println("   • Diferença Simétrica : " + aIgual.diferencaSimetrica(bIgual));
        System.out.println("   • A ⊆ B               : " + aIgual.ehSubconjunto(bIgual));
        System.out.println("   • A ⊂ B (Próprio)     : " + aIgual.ehSubconjuntoProprio(bIgual) + " (Falso pois são iguais)");

        // Cenário 4: Conjuntos Disjuntos
        Conjuntos<Integer> aDisj = new Conjuntos<>();
        Conjuntos<Integer> bDisj = new Conjuntos<>();
        for (int x : new int[]{1, 2}) aDisj.adicionar(x);
        for (int x : new int[]{7, 8}) bDisj.adicionar(x);
        System.out.println("\n4. Conjuntos Disjuntos: A = {1, 2}, B = {7, 8}");
        System.out.println("   • Intersecção (A ∩ B) : " + aDisj.intersecao(bDisj) + " (Vazio)");
        System.out.println("   • A - B               : " + aDisj.diferenca(bDisj) + " (Todo o A)");

        // Cenário 5: Inclusão Total
        Conjuntos<Integer> aSub = new Conjuntos<>();
        Conjuntos<Integer> bSuper = new Conjuntos<>();
        for (int x : new int[]{2, 3}) aSub.adicionar(x);
        for (int x : new int[]{1, 2, 3, 4}) bSuper.adicionar(x);
        System.out.println("\n5. Inclusão Total: A = {2, 3}, B = {1, 2, 3, 4}");
        System.out.println("   • A ⊆ B (Subconjunto)         : " + aSub.ehSubconjunto(bSuper));
        System.out.println("   • A ⊂ B (Subconjunto Próprio) : " + aSub.ehSubconjuntoProprio(bSuper));
        System.out.println("   • A - B                       : " + aSub.diferenca(bSuper));

        // Cenário 6: Entradas com Repetição
        Conjuntos<Integer> aRepetidos = new Conjuntos<>();
        for (int x : new int[]{1, 1, 1, 2, 2, 3}) aRepetidos.adicionar(x);
        System.out.println("\n6. Entradas com Repetição: Inserido [1, 1, 1, 2, 2, 3]");
        System.out.println("   • Conjunto Armazenado         : " + aRepetidos);
        System.out.println("   • Cardinalidade Final         : " + aRepetidos.cardinalidade() + " (Duplicatas descartadas)");
    }

    private static void executarTestesParte2() {
        System.out.println(">>> [PARTE 2: BATERIA DE ENTRADAS OBRIGATÓRIAS (CONJUNTOS NUMÉRICOS)] <<<");

        String[] entradasObrigatorias = {
            "-3",        // Inteiro negativo
            "0",         // Zero
            "3/4",       // Fração positiva
            "-5/2",      // Fração negativa
            "6/3",       // Fração aparente
            "0.(3)",     // Dízima periódica simples
            "1.2(5)",    // Dízima periódica composta
            "0.(9)",     // Caso especial de dízima
            "v9",        // Raiz exata
            "v2"         // Raiz irracional
        };

        for (String entrada : entradasObrigatorias) {
            System.out.println("\n--------------------------------------------------");
            ClassificadorNumerico.ResultadoClassificacao r = ClassificadorNumerico.classificar(entrada);
            System.out.print(r);
        }
    }

    private static void executarTestesParte3() {
        System.out.println(">>> [PARTE 3: MINI-PROJETO - ÍNDICE DE JACCARD & RECOMENDAÇÃO] <<<");
        SistemaRecomendacao sistema = new SistemaRecomendacao();

        SistemaRecomendacao.Usuario ana = sistema.buscarUsuario("Ana");
        SistemaRecomendacao.Usuario caio = sistema.buscarUsuario("Caio");
        SistemaRecomendacao.Usuario bia = sistema.buscarUsuario("Bia");

        double jaccardAnaCaio = SistemaRecomendacao.calcularJaccard(ana.getInteresses(), caio.getInteresses());
        double jaccardAnaBia = SistemaRecomendacao.calcularJaccard(ana.getInteresses(), bia.getInteresses());

        System.out.println("\n• Perfil Ana  : " + ana.getInteresses());
        System.out.println("• Perfil Caio : " + caio.getInteresses());
        System.out.println("• Perfil Bia  : " + bia.getInteresses());

        System.out.printf("\nSimilaridade Jaccard J(Ana, Caio) = %.2f%%\n", jaccardAnaCaio * 100);
        System.out.printf("Similaridade Jaccard J(Ana, Bia)  = %.2f%%\n", jaccardAnaBia * 100);

        SistemaRecomendacao.Usuario compativel = sistema.encontrarMaisCompativel(caio);
        Conjuntos<String> recomendados = SistemaRecomendacao.obterItensRecomendados(caio, compativel);

        System.out.println("\nRecomendação gerada para Caio:");
        System.out.println(" -> Perfil com maior afinidade : " + compativel.getNome());
        System.out.println(" -> Itens recomendados (B - A) : " + recomendados);
    }
}
