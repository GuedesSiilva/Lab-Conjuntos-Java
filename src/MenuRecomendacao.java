import java.util.Collection;
import java.util.Scanner;

public class MenuRecomendacao {
    private final Scanner scanner;
    private final SistemaRecomendacao sistema;

    public MenuRecomendacao() {
        this.scanner = new Scanner(System.in);
        this.sistema = new SistemaRecomendacao();
    }

    public void iniciar() {
        int opcao = -1;
        do {
            System.out.print("""
            ===========================================================
             PARTE 3: SISTEMA DE RECOMENDAÇÃO (ÍNDICE DE JACCARD)
            ===========================================================
                [1] - Listar Usuários e Interesses
                [2] - Comparar Afinidade entre 2 Usuários (Jaccard)
                [3] - Gerar Recomendações Automáticas para um Usuário
                [4] - Cadastrar Novo Usuário e Interesses
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
                    listarUsuarios();
                    break;

                case 2:
                    compararUsuarios();
                    break;

                case 3:
                    recomendarParaUsuario();
                    break;

                case 4:
                    cadastrarNovoUsuario();
                    break;

                case 0:
                    System.out.println("Retornando ao Menu Principal...");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha uma opção do menu.");
            }
        } while (opcao != 0);
    }

    private void listarUsuarios() {
        System.out.println("--- LISTA DE USUÁRIOS E CONJUNTOS DE INTERESSES ---");
        Collection<SistemaRecomendacao.Usuario> lista = sistema.getTodosUsuarios();
        for (SistemaRecomendacao.Usuario u : lista) {
            System.out.println("• " + u.getNome() + ": " + u.getInteresses());
        }
    }

    private void compararUsuarios() {
        listarUsuarios();
        System.out.print("\nDigite o nome do primeiro usuário: ");
        String nome1 = scanner.nextLine().trim();
        SistemaRecomendacao.Usuario u1 = sistema.buscarUsuario(nome1);

        System.out.print("Digite o nome do segundo usuário: ");
        String nome2 = scanner.nextLine().trim();
        SistemaRecomendacao.Usuario u2 = sistema.buscarUsuario(nome2);

        if (u1 == null || u2 == null) {
            System.out.println("Erro: Um ou ambos os usuários não foram encontrados!");
            return;
        }

        Conjuntos<String> a = u1.getInteresses();
        Conjuntos<String> b = u2.getInteresses();

        Conjuntos<String> intersecao = a.intersecao(b);
        Conjuntos<String> uniao = a.uniao(b);
        double jaccard = SistemaRecomendacao.calcularJaccard(a, b);

        System.out.println("\n--- ANÁLISE DE AFINIDADE (JACCARD) ---");
        System.out.println("Conjunto A (" + u1.getNome() + "): " + a);
        System.out.println("Conjunto B (" + u2.getNome() + "): " + b);
        System.out.println("Interseção A ∩ B (Em comum): " + intersecao + " (Total: " + intersecao.cardinalidade() + ")");
        System.out.println("União A ∪ B (Total único):  " + uniao + " (Total: " + uniao.cardinalidade() + ")");
        System.out.printf("Fórmula: J(A, B) = |A ∩ B| / |A ∪ B| = %d / %d\n", intersecao.cardinalidade(), uniao.cardinalidade());
        System.out.printf("Índice de Similaridade de Jaccard: %.4f (%.2f%% de afinidade)\n", jaccard, jaccard * 100);
    }

    private void recomendarParaUsuario() {
        listarUsuarios();
        System.out.print("\nDigite o nome do usuário que receberá recomendações: ");
        String nome = scanner.nextLine().trim();
        SistemaRecomendacao.Usuario alvo = sistema.buscarUsuario(nome);

        if (alvo == null) {
            System.out.println("Erro: Usuário não encontrado!");
            return;
        }

        SistemaRecomendacao.Usuario maisCompativel = sistema.encontrarMaisCompativel(alvo);
        if (maisCompativel == null) {
            System.out.println("Não encontramos nenhum outro usuário com interesses em comum para gerar recomendações.");
            return;
        }

        double score = SistemaRecomendacao.calcularJaccard(alvo.getInteresses(), maisCompativel.getInteresses());
        Conjuntos<String> recomendados = SistemaRecomendacao.obterItensRecomendados(alvo, maisCompativel);

        System.out.println("\n--- RESULTADO DA RECOMENDAÇÃO INTELIGENTE ---");
        System.out.println("Usuário Alvo: " + alvo.getNome() + " " + alvo.getInteresses());
        System.out.printf("Perfil mais afim encontrado: %s (%.2f%% de similaridade)\n", maisCompativel.getNome(), score * 100);
        System.out.println("Interesses do Perfil Afim: " + maisCompativel.getInteresses());
        System.out.println("Itens Recomendados (Diferença de Conjuntos B - A): " + recomendados);

        if (recomendados.cardinalidade() == 0) {
            System.out.println("O usuário alvo já possui todos os interesses que o perfil afim tem!");
        } else {
            System.out.println("Novos interesses sugeridos:");
            for (String item : recomendados.getElementos()) {
                System.out.println(" -> Sugestão: " + item);
            }
        }
    }

    private void cadastrarNovoUsuario() {
        System.out.print("Nome do novo usuário: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome inválido!");
            return;
        }

        SistemaRecomendacao.Usuario novo = new SistemaRecomendacao.Usuario(nome);
        System.out.println("Digite os interesses separados por vírgula (ex: Python, Java, Docker, Rust): ");
        String linha = scanner.nextLine().trim();

        if (!linha.isEmpty()) {
            String[] partes = linha.split(",");
            for (String p : partes) {
                if (!p.trim().isEmpty()) {
                    novo.adicionarInteresse(p);
                }
            }
        }

        sistema.cadastrarUsuario(novo);
        System.out.println("Usuário '" + nome + "' cadastrado com sucesso com os interesses: " + novo.getInteresses());
    }
}
