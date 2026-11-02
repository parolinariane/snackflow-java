# SnackFlow — Sistema de Gerenciamento de Pedidos para Lanchonetes

O **SnackFlow** é um aplicativo desktop em Java que simula a realização de pedidos em uma lanchonete. O usuário visualiza o cardápio, escolhe produtos e quantidades, confere o pedido e, futuramente, poderá finalizá-lo.

## Objetivo acadêmico

Projeto desenvolvido para a disciplina **Linguagem de Programação II (HTOLPR2)** do curso de **Análise e Desenvolvimento de Sistemas** do **Instituto Federal de São Paulo (IFSP)**.

O trabalho demonstra:

- Construção de 2 a 3 telas gráficas com Java Swing.
- Comunicação de dados entre telas por meio da passagem de objetos como parâmetros.
- Conceitos de programação orientada a objetos: classes, encapsulamento, construtores, herança (componentes visuais personalizados) e coleções (`ArrayList`).

## Tecnologias utilizadas

- Java 17
- Java Swing
- `ArrayList` para armazenamento em memória
- Git e GitHub

Não há banco de dados, frameworks ou APIs externas: os dados ficam em memória enquanto o aplicativo está aberto.

## Identidade visual

Estilo de hamburgueria artesanal, com tons quentes de "brasa":

| Cor              | Hex       | Uso                                        |
|------------------|-----------|--------------------------------------------|
| Laranja queimado | `#D9662B` | Botões principais, chamas, selo do pedido  |
| Vermelho         | `#B3241E` | Preços, subtotais, botão Remover           |
| Vinho            | `#4E1515` | Cabeçalho, rodapé, títulos                 |
| Creme            | `#F7C98A` | Logo, faixa de chamas, textos sobre vinho  |
| Preto            | `#1A0A0A` | Reserva para detalhes                      |

- Títulos na fonte **Cooper Black** (se não estiver instalada, o sistema usa Georgia ou uma fonte serifada padrão); textos em Segoe UI.
- A chama é desenhada em código (`IconeChama`, com `Path2D`), sem arquivos de imagem.
- Faixa decorativa de chamas abaixo do cabeçalho (`FaixaChamas`).

## Funcionalidades — primeira etapa

**Tela 1 — Cardápio**
- Cabeçalho com a identidade visual do SnackFlow.
- Cards com nome, descrição e preço (em reais) de cada produto.
- Seletor de quantidade e botão **Adicionar ao pedido** em cada card.
- Indicador da quantidade de itens e do valor total do pedido.
- Botão **Ver meu pedido**.

**Tela 2 — Meu Pedido**
- Lista dos produtos escolhidos com quantidade, preço unitário e subtotal.
- Valor total do pedido.
- Botão **Remover** em cada produto (com confirmação).
- Mensagem amigável quando o pedido está vazio.
- Botão **Voltar ao cardápio**, mantendo os itens já escolhidos.

**Validações**
- Quantidades menores ou iguais a zero, negativas, não numéricas ou acima do limite (20) são recusadas com uma mensagem.
- Itens ou produtos inválidos (nulos) não podem ser adicionados.
- Adicionar um produto que já está no pedido soma a quantidade, em vez de duplicar a linha.
- O total é recalculado sempre que um item é adicionado ou removido.

### Cardápio inicial

| Produto      | Preço     |
|--------------|-----------|
| Hambúrguer   | R$ 15,00  |
| Batata frita | R$ 12,00  |
| Refrigerante | R$ 5,00   |
| Milk-shake   | R$ 14,00  |

## Estrutura de pastas

```text
snackflow-java/
├── README.md
├── .gitignore
└── src/
    └── snackflow/
        ├── Main.java                 # Ponto de entrada: cria o Cardapio, o Pedido e abre a primeira tela
        ├── model/                    # Regras do sistema (sem nenhuma dependência de interface)
        │   ├── Produto.java          # Produto do cardápio (id, nome, descrição, preço)
        │   ├── ItemPedido.java       # Produto + quantidade, com calcularSubtotal()
        │   ├── Pedido.java           # Lista de itens, adicionarItem(), removerItem(), calcularTotal()
        │   └── Cardapio.java         # Lista de produtos cadastrados no código
        └── view/                     # Telas e componentes visuais
            ├── Estilo.java           # Cores, fontes, formatação em reais e cabeçalho padrão
            ├── IconeChama.java       # Desenho da chama (símbolo da marca)
            ├── FaixaChamas.java      # Faixa decorativa com chamas repetidas
            ├── PainelArredondado.java# Painel com cantos arredondados (cards)
            ├── BotaoModerno.java     # Botão arredondado com efeito de hover
            ├── CardProduto.java      # Card de um produto no cardápio
            ├── CardapioView.java     # Tela 1 — Cardápio
            └── PedidoView.java       # Tela 2 — Meu Pedido
```

### Comunicação entre as telas

1. `Main` cria **um único** objeto `Pedido` e o passa para `CardapioView` pelo construtor.
2. Cada `CardProduto` recebe o `Produto` e a `CardapioView`; ao clicar em *Adicionar*, chama `telaCardapio.adicionarAoPedido(produto, quantidade)`, que cria um `ItemPedido` e o entrega ao `Pedido`.
3. Ao clicar em *Ver meu pedido*, a `CardapioView` cria `new PedidoView(pedido, this)`, passando o **mesmo** objeto `Pedido` e a si mesma.
4. A `PedidoView` lê e altera esse objeto. Ao voltar, chama `telaCardapio.atualizarResumo()` e reexibe a tela anterior — como o objeto é o mesmo, nenhum item é perdido.

## Como executar

Requisito: **JDK 17 ou superior** instalado (`javac -version` deve mostrar 17+).

### Pelo terminal

Na pasta raiz do projeto:

```bash
javac -encoding UTF-8 -d out src/snackflow/Main.java src/snackflow/model/*.java src/snackflow/view/*.java
```

```bash
java -cp out snackflow.Main
```

### Por uma IDE (IntelliJ IDEA, Eclipse, NetBeans ou VS Code)

1. Abra a pasta `snackflow-java` como projeto.
2. Marque a pasta `src` como pasta de código-fonte (*Sources Root*), se a IDE não fizer isso automaticamente.
3. Execute a classe `snackflow.Main`.

## Funcionalidades planejadas — segunda etapa

- Terceira tela: **Finalização** (`FinalizacaoView`), recebendo o mesmo objeto `Pedido`.
- Identificação do cliente.
- Seleção da forma de pagamento: dinheiro, cartão ou PIX.
- Confirmação do pedido e mensagem de pedido finalizado.
- Melhorias finais na experiência do usuário.

A separação entre `model` e `view` permite adicionar essas funcionalidades sem reescrever o que já existe: basta criar a nova tela no pacote `view` (e, se necessário, classes como `Cliente` no pacote `model`) e abri-la a partir da `PedidoView`, repassando o `Pedido`.
