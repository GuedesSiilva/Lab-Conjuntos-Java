# 📐 Laboratório de Conjuntos em Java

Projeto prático desenvolvido para a disciplina de **Matemática Concreta / Teoria dos Conjuntos**, aplicando conceitos fundamentais de **Teoria dos Conjuntos** e **Conjuntos Numéricos** por meio de algoritmos desenvolvidos do zero (sem bibliotecas prontas de conjuntos nas operações principais), aliados a uma validação comparativa rigorosa com as coleções nativas da linguagem Java (`java.util.Set`).

---

## 👥 Integrantes
* Davi Guedes / 2161392613008
* Guilherme Carducci / 216139261

---

## 📌 Sumário
1. [Decisões Técnicas e Convenções](#-decisões-técnicas-e-convenções)
2. [Estrutura do Projeto](#-estrutura-do-projeto)
3. [Como Compilar e Executar](#-como-compilar-e-executar)
4. [Parte 1: Teoria dos Conjuntos (Básico)](#-parte-1-teoria-dos-conjuntos-básico)
5. [Parte 2: Conjuntos Numéricos (Intermediário)](#-parte-2-conjuntos-numéricos-intermediário)
6. [Parte 3: Mini-projeto - Sistema de Recomendação por Jaccard](#-parte-3-mini-projeto---sistema-de-recomendação-por-jaccard)
7. [Logs de Execução e Testes de Borda](#-logs-de-execução-e-testes-de-borda)

---

## ⚙️ Decisões Técnicas e Convenções

### 1. Definição do Conjunto dos Números Naturais ($\mathbb{N}$)
> **Convenção adotada:** Neste projeto, convencionou-se formalmente que **$0 \in \mathbb{N}$** (Zero pertence aos Naturais), alinhando-se aos axiomas de Peano modernos e ao padrão internacional ISO 80000-2. Portanto:
> $$\mathbb{N} = \{0, 1, 2, 3, 4, \dots\}$$
> Números negativos inteiros pertencem a $\mathbb{Z}$, mas **não** pertencem a $\mathbb{N}$.

### 2. Algoritmos "Do Zero" vs. Nativos
Conforme o desafio algorítmico do edital, a classe `Conjuntos<T>` não utiliza estruturas como `HashSet` ou métodos auxiliares (`addAll`, `retainAll`, `removeAll`) para calcular os resultados. Todas as operações utilizam:
* Laços (`for-each`)
* Condicionais (`if`)
* Tipagem genérica (`<T>`) para suportar inteiros, strings e quaisquer outros tipos.

A biblioteca nativa `java.util.HashSet` é utilizada **exclusivamente na opção de Validação Cruzada**, comprovando a exatidão matemática do algoritmo próprio.

---

## 📂 Estrutura do Projeto

```text
Lab-Conjuntos-java/
│
├── src/
│   ├── Main.java                     # Ponto de entrada do sistema (Menu Geral)
│   ├── Conjuntos.java                # Implementação pura de conjuntos (Generics)
│   ├── MenuTeoriaConjuntos.java      # Interface interativa e validação nativa (Parte 1)
│   ├── ClassificadorNumerico.java    # Classificador de N, Z, Q, I, R e Dízimas (Parte 2)
│   ├── MenuConjuntosNumericos.java   # Interface do classificador e bônus (Parte 2)
│   ├── SistemaRecomendacao.java      # Mini-projeto: Afinidade via Jaccard (Parte 3)
│   ├── MenuRecomendacao.java         # Interface do recomendador inteligente (Parte 3)
│   └── BateriaTestes.java            # Suíte automatizada de testes e casos de borda
│
└── README.md                         # Documentação completa e logs de execução
```

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
* Java Development Kit (JDK) versão 17 ou superior.

### Compilação via Terminal
No diretório raiz do projeto:
```bash
# Compilar todos os fontes para a pasta 'out'
javac -d out src/*.java
```

### Execução do Menu Principal
```bash
java -cp out Main
```

### Execução Direta da Bateria de Testes
```bash
java -cp out BateriaTestes
```

---

## 🔬 Parte 1: Teoria dos Conjuntos (Básico)

A classe `Conjuntos<T>` fornece os seguintes operadores implementados manualmente:

| Operação Matemática | Notação | Método Implementado | Equivalente Nativo (Validação) |
| :--- | :---: | :--- | :--- |
| **Pertinência** | $x \in A$ | `pertence(x)` | `set.contains(x)` |
| **Cardinalidade** | $\|A\|$ | `cardinalidade()` | `set.size()` |
| **União** | $A \cup B$ | `uniao(B)` | `setA.addAll(setB)` |
| **Intersecção** | $A \cap B$ | `intersecao(B)` | `setA.retainAll(setB)` |
| **Diferença** | $A - B$ | `diferenca(B)` | `setA.removeAll(setB)` |
| **Diferença Simétrica** | $A \ \Delta \ B$ | `diferencaSimetrica(B)` | $(A - B) \cup (B - A)$ |
| **Subconjunto** | $A \subseteq B$ | `ehSubconjunto(B)` | `setB.containsAll(setA)` |
| **Subconjunto Próprio** | $A \subset B$ | `ehSubconjuntoProprio(B)` | $A \subseteq B \land \|A\| < \|B\|$ |
| **Complemento** | $A^c = U - A$ | `complemento(U)` | `setU.removeAll(setA)` |

* **Eliminação de Duplicatas:** Realizada no método `adicionar(x)` antes de inserir cada elemento, assegurando a propriedade fundamental dos conjuntos.

---

## 🔢 Parte 2: Conjuntos Numéricos (Intermediário)

O módulo `ClassificadorNumerico` categoriza qualquer número fornecido em formato textual entre os conjuntos numéricos $\mathbb{N}, \mathbb{Z}, \mathbb{Q}, \mathbb{I}, \mathbb{R}$:

1. **Frações ($a/b$):**
   * Detecta se simplifica para inteiro (ex: $6/3 = 2 \in \mathbb{N}, \mathbb{Z}, \mathbb{Q}, \mathbb{R}$).
   * Trata frações irredutíveis e decimais exatos (ex: $3/4 = 0{,}75 \in \mathbb{Q}, \mathbb{R}$).
2. **Dízimas Periódicas:**
   * Distingue dízima simples de composta através de análise de subcadeias.
   * Calcula a **fração geratriz** simplificada pelo algoritmo de Euclides (MDC):
     $$\text{Geratriz} = \frac{\text{Parte Não Periódica com Período} - \text{Parte Não Periódica}}{9\dots90\dots0}$$
   * Trata convergência de dízimas com período 9 (ex: $0{,}(9) \to 1 \in \mathbb{N}$ e $1{,}(9) \to 2 \in \mathbb{N}$).
3. **Radicais:**
   * Distingue raízes exatas ($\sqrt{9} = 3 \in \mathbb{N}$) de raízes irracionais ($\sqrt{2} \in \mathbb{I}, \mathbb{R}$).
   * Trata raízes quadradas de negativos ($\sqrt{-4} \notin \mathbb{R}$, pertencentes a $\mathbb{C}$).
4. **Funcionalidades Bônus:**
   * **Crivo de Eratóstenes:** Geração de primos em $\mathbb{N}$ até um limite $N$.
   * **Listagem de Divisores:** Fatoração e listagem completa dos divisores de um inteiro.

---

## 🤝 Parte 3: Mini-projeto - Sistema de Recomendação por Jaccard

Escolhemos a **Opção 1: Sistema de Recomendação Simples (Índice de Jaccard)** devido à sua conexão direta com a teoria dos conjuntos.

### Fundamentação Teórica
O **Índice de Jaccard** quantifica o grau de sobreposição entre dois conjuntos finitos $A$ e $B$:
$$J(A, B) = \frac{|A \cap B|}{|A \cup B|}$$

* Se $J(A, B) = 1{,}0$ ($100\%$), os usuários possuem exatamente os mesmos interesses.
* Se $J(A, B) = 0{,}0$ ($0\%$), os conjuntos são **disjuntos** ($A \cap B = \emptyset$).

### Geração de Recomendações
Para um usuário alvo $A$, o sistema:
1. Calcula a similaridade de Jaccard com todos os demais perfis.
2. Identifica o usuário mais afim $B$ ($J(A, B)$ máximo).
3. Gera recomendações através da **diferença de conjuntos**:
$$\text{Recomendações} = B - A$$
Isto é: itens que o perfil semelhante consome, mas que o usuário alvo ainda não possui!

---

## 📊 Logs de Execução e Testes de Borda

Abaixo constam os registros reais gerados pela suíte automatizada `BateriaTestes`:

```text
================================================================================
                BATERIA DE TESTES AUTOMATIZADOS DO PROJETO                      
================================================================================

>>> [PARTE 1: TESTES OBRIGATÓRIOS DE TEORIA DOS CONJUNTOS] <<<

1. Caso Geral: A = {1, 2, 3, 4}, B = {3, 4, 5, 6}
   • União (A ∪ B)               : {1, 2, 3, 4, 5, 6}
   • Intersecção (A ∩ B)         : {3, 4}
   • Diferença (A - B)           : {1, 2}
   • Diferença (B - A)           : {5, 6}
   • Diferença Simétrica (A Δ B) : {1, 2, 5, 6}

2. Conjunto Vazio: A = ∅, B = {10, 20}
   • União (A ∪ B)       : {10, 20}
   • Intersecção (A ∩ B) : ∅
   • A ⊆ B               : true (O conjunto vazio é subconjunto de qualquer conjunto)

3. Conjuntos Iguais: A = {1, 2, 3}, B = {1, 2, 3}
   • A - B               : ∅
   • Diferença Simétrica : ∅
   • A ⊆ B               : true
   • A ⊂ B (Próprio)     : false (Falso pois são iguais)

4. Conjuntos Disjuntos: A = {1, 2}, B = {7, 8}
   • Intersecção (A ∩ B) : ∅ (Vazio)
   • A - B               : {1, 2} (Todo o A)

5. Inclusão Total: A = {2, 3}, B = {1, 2, 3, 4}
   • A ⊆ B (Subconjunto)         : true
   • A ⊂ B (Subconjunto Próprio) : true
   • A - B                       : ∅

6. Entradas com Repetição: Inserido [1, 1, 1, 2, 2, 3]
   • Conjunto Armazenado         : {1, 2, 3}
   • Cardinalidade Final         : 3 (Duplicatas descartadas)

--------------------------------------------------------------------------------

>>> [PARTE 2: BATERIA DE ENTRADAS OBRIGATÓRIAS (CONJUNTOS NUMÉRICOS)] <<<

Entrada: -3
Tipo: Número inteiro
Pertence a: Z, Q, R
Não pertence a: N, I
Observação: Inteiro negativo (não é natural).

Entrada: 0
Tipo: Número inteiro
Pertence a: N, Z, Q, R
Não pertence a: I
Observação: Convenção adotada no projeto: 0 pertence aos Naturais (0 ∈ ℕ).

Entrada: 3/4
Tipo: Fração / Racional não inteiro
Pertence a: Q, R
Não pertence a: N, Z, I
Observação: Fração com representação decimal 0.75

Entrada: -5/2
Tipo: Fração / Racional não inteiro
Pertence a: Q, R
Não pertence a: N, Z, I
Observação: Fração com representação decimal -2.5

Entrada: 6/3
Tipo: Fração aparente (equivale ao inteiro 2)
Pertence a: N, Z, Q, R
Não pertence a: I
Observação: Inteiro positivo.

Entrada: 0.(3)
Tipo: Dízima periódica
Fração geratriz: 1/3
Pertence a: Q, R
Não pertence a: N, Z, I
Observação: Dízima periódica simples. Fração geratriz simplificada calculada com sucesso.

Entrada: 1.2(5)
Tipo: Dízima periódica
Fração geratriz: 113/90
Pertence a: Q, R
Não pertence a: N, Z, I
Observação: Dízima periódica composta. Fração geratriz simplificada calculada com sucesso.

Entrada: 0.(9)
Tipo: Dízima periódica
Fração geratriz: 1/1
Pertence a: N, Z, Q, R
Não pertence a: I
Observação: Dízima periódica simples. Caso especial: a dízima com período 9 converge exatamente para o inteiro 1.

Entrada: v9
Tipo: Raiz exata (vale 3)
Pertence a: N, Z, Q, R
Não pertence a: I
Observação: Número natural e inteiro positivo.

Entrada: v2
Tipo: Raiz não exata (Irracional)
Pertence a: I, R
Não pertence a: N, Z, Q
Observação: Número irracional com infinitas casas decimais não periódicas (~1.414214...).

--------------------------------------------------------------------------------

>>> [PARTE 3: MINI-PROJETO - ÍNDICE DE JACCARD & RECOMENDAÇÃO] <<<

• Perfil Ana  : {python, java, inteligencia artificial, banco de dados}
• Perfil Caio : {python, inteligencia artificial, estatistica}
• Perfil Bia  : {java, banco de dados, cloud, devops}

Similaridade Jaccard J(Ana, Caio) = 40,00%
Similaridade Jaccard J(Ana, Bia)  = 33,33%

Recomendação gerada para Caio:
 -> Perfil com maior afinidade : Ana
 -> Itens recomendados (B - A) : {java, banco de dados}

================================================================================
             TODOS OS TESTES FORAM EXECUTADOS COM SUCESSO!                     
================================================================================
```
