package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import snackflow.model.Cardapio;
import snackflow.model.Categoria;
import snackflow.model.ItemPedido;
import snackflow.model.Pedido;
import snackflow.model.Produto;

/**
 * Primeira tela: exibe o cardápio e permite adicionar produtos ao pedido.
 * O objeto Pedido é recebido no construtor e repassado para a PedidoView.
 */
public class CardapioView extends JFrame {

    private Cardapio cardapio;
    private Pedido pedido;

    private ArrayList<CardProduto> cards = new ArrayList<>();
    private ArrayList<BotaoModerno> botoesFiltro = new ArrayList<>();
    private JPanel grade;
    private JScrollPane rolagem;

    private BotaoModerno botaoCarrinho;
    private JLabel lblResumo;
    private JLabel lblMensagem;
    private Timer timerMensagem;

    public CardapioView(Cardapio cardapio, Pedido pedido) {
        this.cardapio = cardapio;
        this.pedido = pedido;

        setTitle("SnackFlow - Cardápio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1040, 720));
        setSize(1140, 780);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Estilo.FUNDO);
        raiz.add(criarCabecalho(), BorderLayout.NORTH);
        raiz.add(criarConteudo(), BorderLayout.CENTER);
        raiz.add(criarBarraPedido(), BorderLayout.SOUTH);
        setContentPane(raiz);

        atualizarResumo();
    }

    private JPanel criarCabecalho() {
        // O botão do topo também leva à tela do pedido
        botaoCarrinho = BotaoModerno.primario("");
        botaoCarrinho.addActionListener(e -> abrirTelaPedido());
        return Estilo.criarCabecalho(botaoCarrinho);
    }

    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 18));
        conteudo.setBackground(Estilo.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(26, 28, 12, 28));

        JPanel topo = new JPanel();
        topo.setOpaque(false);
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.add(Estilo.criarRotulo("Nosso Cardápio", Estilo.FONTE_TITULO, Estilo.VINHO));
        topo.add(Estilo.criarRotulo("Escolha seus favoritos e monte o seu pedido.",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO));
        topo.add(Box.createVerticalStrut(16));
        topo.add(criarFiltros());

        // Cria um card para cada produto; o filtro decide quais aparecem na grade
        for (Produto produto : cardapio.getProdutos()) {
            cards.add(new CardProduto(produto, this));
        }
        grade = new JPanel(new GridLayout(0, 4, 18, 18));
        grade.setOpaque(false);

        // Painel que rola só na vertical, mantendo os cards no topo
        PainelRolavel areaCards = new PainelRolavel(new BorderLayout());
        areaCards.setBackground(Estilo.FUNDO);
        areaCards.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 6));
        areaCards.add(grade, BorderLayout.NORTH);

        rolagem = new JScrollPane(areaCards);
        rolagem.setBorder(null);
        rolagem.getViewport().setBackground(Estilo.FUNDO);
        rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        exibirProdutos(cardapio.getProdutos());

        conteudo.add(topo, BorderLayout.NORTH);
        conteudo.add(rolagem, BorderLayout.CENTER);
        return conteudo;
    }

    /** Cria os botões "Todos", "Lanches", "Acompanhamentos"... a partir do enum Categoria. */
    private JPanel criarFiltros() {
        JPanel filtros = new JPanel();
        filtros.setOpaque(false);
        filtros.setLayout(new BoxLayout(filtros, BoxLayout.X_AXIS));
        filtros.setAlignmentX(LEFT_ALIGNMENT);

        BotaoModerno botaoTodos = BotaoModerno.filtro("Todos");
        botaoTodos.addActionListener(e -> filtrar(null, botaoTodos));
        botoesFiltro.add(botaoTodos);
        filtros.add(botaoTodos);

        for (Categoria categoria : Categoria.values()) {
            BotaoModerno botao = BotaoModerno.filtro(categoria.getDescricao());
            botao.addActionListener(e -> filtrar(categoria, botao));
            botoesFiltro.add(botao);
            filtros.add(Box.createHorizontalStrut(8));
            filtros.add(botao);
        }

        botaoTodos.setSelecionado(true);
        return filtros;
    }

    /** Mostra só os produtos da categoria escolhida (null = todos) e destaca o botão clicado. */
    private void filtrar(Categoria categoria, BotaoModerno botaoClicado) {
        if (categoria == null) {
            exibirProdutos(cardapio.getProdutos());
        } else {
            exibirProdutos(cardapio.buscarPorCategoria(categoria));
        }
        for (BotaoModerno botao : botoesFiltro) {
            botao.setSelecionado(botao == botaoClicado);
        }
    }

    private void exibirProdutos(List<Produto> produtos) {
        grade.removeAll();
        for (CardProduto card : cards) {
            if (produtos.contains(card.getProduto())) {
                grade.add(card);
            }
        }
        grade.revalidate();
        grade.repaint();
        // Volta a rolagem para o topo depois que a grade for redesenhada
        SwingUtilities.invokeLater(() -> rolagem.getVerticalScrollBar().setValue(0));
    }

    /** Barra vinho no rodapé, com o resumo do pedido e o botão "Ver meu pedido". */
    private JPanel criarBarraPedido() {
        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        lblResumo = Estilo.criarRotulo("", Estilo.FONTE_SUBTITULO, Estilo.CREME);
        lblMensagem = Estilo.criarRotulo(" ", Estilo.FONTE_PEQUENA, Estilo.TEXTO_CLARO);
        textos.add(lblResumo);
        textos.add(lblMensagem);

        JPanel esquerda = new JPanel(new BorderLayout(14, 0));
        esquerda.setOpaque(false);
        esquerda.add(centralizar(new IconeChama(26, 32, Estilo.LARANJA, Estilo.CREME)), BorderLayout.WEST);
        esquerda.add(textos, BorderLayout.CENTER);

        BotaoModerno botaoVerPedido = BotaoModerno.primario("Ver meu pedido");
        botaoVerPedido.addActionListener(e -> abrirTelaPedido());

        PainelArredondado barra = new PainelArredondado(new BorderLayout(16, 0), Estilo.VINHO, null, 30);
        barra.setBorder(BorderFactory.createEmptyBorder(14, 22, 14, 14));
        barra.add(esquerda, BorderLayout.CENTER);
        barra.add(centralizar(botaoVerPedido), BorderLayout.EAST);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(Estilo.FUNDO);
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 28, 20, 28));
        rodape.add(barra);
        return rodape;
    }

    /** Envolve o componente num painel que o mantém centralizado e no tamanho original. */
    private JPanel centralizar(JComponent componente) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setOpaque(false);
        painel.add(componente);
        return painel;
    }

    /**
     * Chamado pelos cards. Cria o ItemPedido e o entrega ao Pedido,
     * que faz as validações. Retorna true se o item foi adicionado.
     */
    public boolean adicionarAoPedido(Produto produto, int quantidade) {
        try {
            pedido.adicionarItem(new ItemPedido(produto, quantidade));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Não foi possível adicionar", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        atualizarResumo();
        mostrarMensagem(quantidade + "x " + produto.getNome() + " adicionado ao pedido.");
        return true;
    }

    /** Atualiza o contador e o total. Também é chamado ao voltar da tela do pedido. */
    public void atualizarResumo() {
        int quantidade = pedido.getQuantidadeTotal();
        String textoItens = quantidade == 1 ? "1 item" : quantidade + " itens";

        botaoCarrinho.setText("Meu pedido  •  " + quantidade);
        lblResumo.setText(textoItens + "  •  " + Estilo.formatarPreco(pedido.getValorTotal()));
    }

    private void mostrarMensagem(String mensagem) {
        lblMensagem.setText(mensagem);
        if (timerMensagem != null) {
            timerMensagem.stop();
        }
        timerMensagem = new Timer(3000, e -> lblMensagem.setText(" "));
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }

    /** Abre a segunda tela, passando o MESMO objeto Pedido e esta tela como parâmetros. */
    private void abrirTelaPedido() {
        PedidoView telaPedido = new PedidoView(pedido, this);
        telaPedido.setVisible(true);
        setVisible(false);
    }
}
