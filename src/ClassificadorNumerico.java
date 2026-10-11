import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassificadorNumerico {

    // Convencionado no projeto: 0 PERTENCE aos Naturais (0 ∈ ℕ)
    public static class ResultadoClassificacao {
        public String entrada;
        public String tipoIdentificado;
        public List<String> pertence = new ArrayList<>();
        public List<String> naoPertence = new ArrayList<>();
        public String fracGeratriz = null;
        public String observacao = "";

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Entrada: ").append(entrada).append("\n");
            sb.append("Tipo: ").append(tipoIdentificado).append("\n");
            if (fracGeratriz != null) {
                sb.append("Fração geratriz: ").append(fracGeratriz).append("\n");
            }
            sb.append("Pertence a: ").append(String.join(", ", pertence)).append("\n");
            sb.append("Não pertence a: ").append(String.join(", ", naoPertence)).append("\n");
            if (!observacao.isEmpty()) {
                sb.append("Observação: ").append(observacao).append("\n");
            }
            return sb.toString();
        }
    }

    public static ResultadoClassificacao classificar(String entrada) {
        ResultadoClassificacao res = new ResultadoClassificacao();
        res.entrada = entrada.trim();
        String s = res.entrada.replace(",", ".").toLowerCase();

        // 1. Tratamento de Dízimas Periódicas: ex "0.(3)", "1.2(5)", "0.(9)"
        if (s.contains("(") && s.contains(")")) {
            return processarDizima(res, s);
        }

        // 2. Tratamento de Raízes: ex "v9", "√9", "v2", "sqrt(9)"
        if (s.startsWith("v") || s.startsWith("√") || s.startsWith("sqrt")) {
            return processarRaiz(res, s);
        }

        // 3. Tratamento de Frações: ex "3/4", "-5/2", "6/3"
        if (s.contains("/")) {
            return processarFracao(res, s);
        }

        // 4. Decimais Exatos: ex "0.75"
        if (s.contains(".")) {
            res.tipoIdentificado = "Decimal exato";
            res.pertence.addAll(Arrays.asList("Q", "R"));
            res.naoPertence.addAll(Arrays.asList("N", "Z", "I"));
            res.observacao = "Possui representação decimal finita.";
            return res;
        }

        // 5. Números Inteiros: ex "-3", "0", "7"
        try {
            long val = Long.parseLong(s);
            res.tipoIdentificado = "Número inteiro";

            if (val >= 0) {
                res.pertence.addAll(Arrays.asList("N", "Z", "Q", "R"));
                res.naoPertence.add("I");
                res.observacao = (val == 0) ? "Definido que 0 pertence aos Naturais (N)." : "Inteiro positivo.";
            } else {
                res.pertence.addAll(Arrays.asList("Z", "Q", "R"));
                res.naoPertence.addAll(Arrays.asList("N", "I"));
                res.observacao = "Inteiro negativo (não é natural).";
            }
            return res;
        } catch (NumberFormatException e) {
            res.tipoIdentificado = "Formato não reconhecido";
            res.observacao = "Verifique a formatação da entrada.";
            return res;
        }
    }

    // Cálculo da Fração Geratriz de Dízimas Periódicas Simples e Compostas
    private static ResultadoClassificacao processarDizima(ResultadoClassificacao res, String s) {
        res.tipoIdentificado = "Dízima periódica";
        res.pertence.addAll(Arrays.asList("Q", "R"));
        res.naoPertence.addAll(Arrays.asList("N", "Z", "I"));

        try {
            int idxDot = s.indexOf(".");
            int idxOpen = s.indexOf("(");
            int idxClose = s.indexOf(")");

            String parteInteira = (idxDot != -1) ? s.substring(0, idxDot) : "0";
            if (parteInteira.equals("-") || parteInteira.isEmpty()) parteInteira = "0";

            String antiPeriodo = (idxDot != -1) ? s.substring(idxDot + 1, idxOpen) : "";
            String periodo = s.substring(idxOpen + 1, idxClose);

            // Fórmula: (Parte Não-Periódica Completa - Parte Não-Periódica Sem Período) / 9s e 0s
            String naoPeriodicaStr = parteInteira + antiPeriodo;
            String tudoStr = parteInteira + antiPeriodo + periodo;

            long naoPeriodica = Long.parseLong(naoPeriodicaStr);
            long tudo = Long.parseLong(tudoStr);

            long num = tudo - naoPeriodica;

            StringBuilder denStr = new StringBuilder();
            for (int i = 0; i < periodo.length(); i++) denStr.append("9");
            for (int i = 0; i < antiPeriodo.length(); i++) denStr.append("0");

            long den = Long.parseLong(denStr.toString());

            // Simplificação pelo MDC
            long mdc = mdc(num, den);
            num /= mdc;
            den /= mdc;

            res.fracGeratriz = num + "/" + den;

            // Caso curioso 0.(9) -> 9/9 = 1 (inteiro)
            if (num == den) {
                res.pertence.clear();
                res.naoPertence.clear();
                res.pertence.addAll(Arrays.asList("N", "Z", "Q", "R"));
                res.naoPertence.add("I");
                res.observacao = "Caso curioso: a geratriz resulta no inteiro 1.";
            } else {
                res.observacao = antiPeriodo.isEmpty() ? "Dízima periódica simples." : "Dízima periódica composta.";
            }

        } catch (Exception e) {
            res.fracGeratriz = "Não foi possível calcular";
        }
        return res;
    }

    private static ResultadoClassificacao processarFracao(ResultadoClassificacao res, String s) {
        String[] partes = s.split("/");
        long num = Long.parseLong(partes[0].trim());
        long den = Long.parseLong(partes[1].trim());

        if (num % den == 0) {
            long resultado = num / den;
            res.tipoIdentificado = "Fração aparente (equivale a " + resultado + ")";
            if (resultado >= 0) {
                res.pertence.addAll(Arrays.asList("N", "Z", "Q", "R"));
                res.naoPertence.add("I");
            } else {
                res.pertence.addAll(Arrays.asList("Z", "Q", "R"));
                res.naoPertence.addAll(Arrays.asList("N", "I"));
            }
        } else {
            res.tipoIdentificado = "Fração irredutível / Rational";
            res.pertence.addAll(Arrays.asList("Q", "R"));
            res.naoPertence.addAll(Arrays.asList("N", "Z", "I"));
            res.observacao = "Fração equivalente a " + ((double) num / den);
        }
        return res;
    }

    private static ResultadoClassificacao processarRaiz(ResultadoClassificacao res, String s) {
        String numStr = s.replaceAll("[^0-9.-]", "");
        double val = Double.parseDouble(numStr);

        double raiz = Math.sqrt(val);
        if (raiz == (long) raiz) {
            long raizInt = (long) raiz;
            res.tipoIdentificado = "Raiz exata (vale " + raizInt + ")";
            res.pertence.addAll(Arrays.asList("N", "Z", "Q", "R"));
            res.naoPertence.add("I");
        } else {
            res.tipoIdentificado = "Raiz não exata (Irracional)";
            res.pertence.addAll(Arrays.asList("I", "R"));
            res.naoPertence.addAll(Arrays.asList("N", "Z", "Q"));
            res.observacao = "Número irracional com infinitas casas não periódicas.";
        }
        return res;
    }

    // Algoritmo de Euclides para o MDC
    private static long mdc(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    // BÔNUS 1: Crivo de Eratóstenes (Primos até N)
    public static List<Integer> crivoEratostenes(int n) {
        boolean[] primo = new boolean[n + 1];
        Arrays.fill(primo, true);
        List<Integer> primos = new ArrayList<>();

        for (int p = 2; p * p <= n; p++) {
            if (primo[p]) {
                for (int i = p * p; i <= n; i += p)
                    primo[i] = false;
            }
        }
        for (int i = 2; i <= n; i++) {
            if (primo[i]) primos.add(i);
        }
        return primos;
    }

    // BÔNUS 2: Listagem de Divisores de um número
    public static List<Integer> listarDivisores(int n) {
        List<Integer> divisores = new ArrayList<>();
        int absN = Math.abs(n);
        for (int i = 1; i <= absN; i++) {
            if (absN % i == 0) {
                divisores.add(i);
            }
        }
        return divisores;
    }
}