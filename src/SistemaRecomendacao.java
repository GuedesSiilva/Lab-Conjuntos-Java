import java.util.*;

/**
 * Parte 3: Mini-projeto - Sistema de Recomendação Simples via Índice de Similaridade de Jaccard.
 *
 * Conceito Matemático:
 * O Índice de Jaccard mede a similaridade entre dois conjuntos A e B:
 * J(A, B) = |A ∩ B| / |A ∪ B|
 *
 * Onde:
 * - Se J(A, B) = 1 (100%), os conjuntos de interesses são idênticos.
 * - Se J(A, B) = 0 (0%), os conjuntos são disjuntos (nenhum interesse em comum).
 *
 * Aplicação em Recomendação:
 * Encontra o usuário mais semelhante a um usuário alvo e recomenda itens que estão em:
 * Recomendados = Interesses(MaisSemelhante) - Interesses(Alvo)
 */
public class SistemaRecomendacao {

    public static class Usuario {
        private final String nome;
        private final Conjuntos<String> interesses;

        public Usuario(String nome) {
            this.nome = nome;
            this.interesses = new Conjuntos<>();
        }

        public String getNome() {
            return nome;
        }

        public Conjuntos<String> getInteresses() {
            return interesses;
        }

        public void adicionarInteresse(String interesse) {
            this.interesses.adicionar(interesse.trim().toLowerCase());
        }

        @Override
        public String toString() {
            return nome + " -> Interesses: " + interesses;
        }
    }

    private final Map<String, Usuario> usuarios;

    public SistemaRecomendacao() {
        this.usuarios = new LinkedHashMap<>();
        carregarUsuariosPadrao();
    }

    /**
     * Carrega usuários padrão com dados de exemplo (ex: gostos culturais/tecnologia)
     */
    private void carregarUsuariosPadrao() {
        Usuario ana = new Usuario("Ana");
        ana.adicionarInteresse("Python");
        ana.adicionarInteresse("Java");
        ana.adicionarInteresse("Inteligencia Artificial");
        ana.adicionarInteresse("Banco de Dados");

        Usuario bia = new Usuario("Bia");
        bia.adicionarInteresse("Java");
        bia.adicionarInteresse("Banco de Dados");
        bia.adicionarInteresse("Cloud");
        bia.adicionarInteresse("DevOps");

        Usuario caio = new Usuario("Caio");
        caio.adicionarInteresse("Python");
        caio.adicionarInteresse("Inteligencia Artificial");
        caio.adicionarInteresse("Estatistica");

        Usuario lucas = new Usuario("Lucas");
        lucas.adicionarInteresse("Design");
        lucas.adicionarInteresse("Figma");
        lucas.adicionarInteresse("UX");

        cadastrarUsuario(ana);
        cadastrarUsuario(bia);
        cadastrarUsuario(caio);
        cadastrarUsuario(lucas);
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getNome().toLowerCase(), usuario);
    }

    public Usuario buscarUsuario(String nome) {
        return usuarios.get(nome.toLowerCase());
    }

    public Collection<Usuario> getTodosUsuarios() {
        return usuarios.values();
    }

    /**
     * Calcula o Índice de Similaridade de Jaccard entre dois conjuntos:
     * J(A, B) = |A ∩ B| / |A ∪ B|
     */
    public static double calcularJaccard(Conjuntos<String> conjuntoA, Conjuntos<String> conjuntoB) {
        Conjuntos<String> uniao = conjuntoA.uniao(conjuntoB);
        int cardUniao = uniao.cardinalidade();

        if (cardUniao == 0) {
            return 0.0; // Ambos conjuntos vazios
        }

        Conjuntos<String> intersecao = conjuntoA.intersecao(conjuntoB);
        int cardIntersecao = intersecao.cardinalidade();

        return (double) cardIntersecao / cardUniao;
    }

    /**
     * Gera recomendações para o usuário alvo com base nos interesses do usuário comparado:
     * Recomendações = Interesses(Outro) - Interesses(Alvo)
     */
    public static Conjuntos<String> obterItensRecomendados(Usuario alvo, Usuario comparado) {
        return comparado.getInteresses().diferenca(alvo.getInteresses());
    }

    /**
     * Localiza o usuário mais compatível (com maior índice de Jaccard > 0).
     */
    public Usuario encontrarMaisCompativel(Usuario alvo) {
        Usuario melhorMatch = null;
        double maiorScore = -1.0;

        for (Usuario outro : usuarios.values()) {
            if (outro.getNome().equalsIgnoreCase(alvo.getNome())) {
                continue;
            }

            double score = calcularJaccard(alvo.getInteresses(), outro.getInteresses());
            if (score > maiorScore && score > 0.0) {
                maiorScore = score;
                melhorMatch = outro;
            }
        }

        return melhorMatch;
    }
}
