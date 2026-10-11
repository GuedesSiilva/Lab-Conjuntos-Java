import java.util.ArrayList;
import java.util.List;

public class Conjuntos<T>{
    private List<T> elementos;

    public Conjuntos() {
        this.elementos = new ArrayList<>();
    }

    public List<T> getElementos() {
        return elementos;
    }

    public boolean pertence(T elemento) {
        for (T item : elementos) {
            if (item.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    public boolean adicionar(T elemento) {
        if (!pertence(elemento)) {
            elementos.add(elemento);
            return true;
        }
        return false; // Elemento repetido descartado
    }
    public int cardinalidade() {
        int contador = 0;
        for (T item : elementos) {
            contador++;
        }
        return contador;
    }

    // 4. União (A ∪ B)
    public Conjuntos<T> uniao(Conjuntos<T> outro) {
        Conjuntos<T> resultado = new Conjuntos<>();

        for (T item : this.elementos) {
            resultado.adicionar(item);
        }
        for (T item : outro.getElementos()) {
            resultado.adicionar(item);
        }

        return resultado;
    }

    // 5. Intersecção (A ∩ B)
    public Conjuntos<T> intersecao(Conjuntos<T> outro) {
        Conjuntos<T> resultado = new Conjuntos<>();

        for (T item : this.elementos) {
            if (outro.pertence(item)) {
                resultado.adicionar(item);
            }
        }

        return resultado;
    }

    // 6. Diferença (A - B)
    public Conjuntos<T> diferenca(Conjuntos<T> outro) {
        Conjuntos<T> resultado = new Conjuntos<>();

        for (T item : this.elementos) {
            if (!outro.pertence(item)) {
                resultado.adicionar(item);
            }
        }

        return resultado;
    }

    // 7. Diferença Simétrica (A Δ B) = (A - B) ∪ (B - A)
    public Conjuntos<T> diferencaSimetrica(Conjuntos<T> outro) {
        Conjuntos<T> aMenosB = this.diferenca(outro);
        Conjuntos<T> bMenosA = outro.diferenca(this);

        return aMenosB.uniao(bMenosA);
    }

    // 8. Inclusão: Subconjunto (A ⊆ B)
    public boolean ehSubconjunto(Conjuntos<T> outro) {
        for (T item : this.elementos) {
            if (!outro.pertence(item)) {
                return false;
            }
        }
        return true;
    }

    // 9. Inclusão Própria (A ⊂ B) -> A ⊆ B e |A| < |B|
    public boolean ehSubconjuntoProprio(Conjuntos<T> outro) {
        return this.ehSubconjunto(outro) && this.cardinalidade() < outro.cardinalidade();
    }

    // 10. Complemento de A em relação ao Universo U (A^c = U - A)
    public Conjuntos<T> complemento(Conjuntos<T> universo) {
        return universo.diferenca(this);
    }

    // Exibição formatada do conjunto: {1, 2, 3}
    @Override
    public String toString() {
        if (elementos.isEmpty()) {
            return "∅"; // Representação de conjunto vazio
        }
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < elementos.size(); i++) {
            sb.append(elementos.get(i));
            if (i < elementos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
