package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import snackflow.model.ItemPedido;
import snackflow.model.Pedido;

/**
 * Segunda tela: mostra os itens do pedido, o total e permite remover produtos.
 * Recebe o Pedido criado na tela de cardápio e a própria tela de cardápio,
 * para poder voltar a ela sem perder os dados.
 */
public class PedidoView extends JFrame {

    // Largura fixa das colunas Quantidade, Preço unitário, Subtotal e Ação
    private static final int[] LARGURAS_COLUNAS = {0, 100, 140, 140, 120};

    private Pedido pedido;
    private CardapioView telaCardapio;

    private JPanel painelItens;
    private JLabel lblTotal;
    private JLabel lblQuantidade;

    public PedidoView(Pedido pedido, CardapioView telaCardapio) {
        this.pedido = pedido;
        this.telaCardapio = telaCardapio;

        setTitle("SnackFlow - Meu Pedido");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(820, 640));
        setSize(telaCardapio.getSize());
        setLocation(telaCardapio.getLocation());

        // Fechar a janela tem o mesmo efeito de "Voltar ao cardápio"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarAoCardapio();
            }
        });

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Estilo.FUNDO);
        raiz.add(Estilo.criarCabecalho(null), BorderLayout.NORTH);
        raiz.add(criarConteudo(), BorderLayout.CENTER);
        raiz.add(criarRodape(), BorderLayout.SOUTH);
        setContentPane(raiz);

        atualizarTela();
    }

    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 20));
        conteudo.setBackground(Estilo.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(26, 28, 20, 28));

        JPanel titulos = new JPanel();
        titulos.setOpaque(false);
        titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));
        titulos.add(Estilo.criarRotulo("Meu Pedido", Estilo.FONTE_TITULO, Estilo.VINHO));
        titulos.add(Estilo.criarRotulo("Confira os produtos escolhidos antes de continuar.",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO));

        painelItens = new JPanel();
        painelItens.setOpaque(false);
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));

        // Mantém as linhas no topo, sem esticá-las verticalmente
        JPanel alinhamentoTopo = new JPanel(new BorderLayout());
        alinhamentoTopo.setBackground(Estilo.FUNDO);
        alinhamentoTopo.add(painelItens, BorderLayout.NORTH);

        JScrollPane rolagem = new JScrollPane(alinhamentoTopo);
        rolagem.setBorder(null);
        rolagem.getViewport().setBackground(Estilo.FUNDO);
        rolagem.getVerticalScrollBar().setUnitIncrement(16);

        conteudo.add(titulos, BorderLayout.NORTH);
        conteudo.add(rolagem, BorderLayout.CENTER);
        return conteudo;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(Estilo.VINHO);
        rodape.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 0, 0, 0, Estilo.LARANJA),
                BorderFactory.createEmptyBorder(14, 28, 14, 28)));

        BotaoModerno botaoVoltar = BotaoModerno.secundario("Voltar ao cardápio");
        botaoVoltar.addActionListener(e -> voltarAoCardapio());

        JPanel totais = new JPanel();
        totais.setOpaque(false);
        totais.setLayout(new BoxLayout(totais, BoxLayout.Y_AXIS));
        lblQuantidade = Estilo.criarRotulo("", Estilo.FONTE_PEQUENA, Estilo.TEXTO_CLARO);
        lblTotal = Estilo.criarRotulo("", Estilo.FONTE_SUBTITULO.deriveFont(24f), Estilo.CREME);
        lblQuantidade.setAlignmentX(RIGHT_ALIGNMENT);
        lblTotal.setAlignmentX(RIGHT_ALIGNMENT);
        totais.add(lblQuantidade);
        totais.add(lblTotal);

        JPanel alinhamentoBotao = new JPanel(new GridBagLayout());
        alinhamentoBotao.setOpaque(false);
        alinhamentoBotao.add(botaoVoltar);

        rodape.add(alinhamentoBotao, BorderLayout.WEST);
        rodape.add(totais, BorderLayout.EAST);
        return rodape;
    }

    /** Reconstrói a lista de itens e o total a partir do objeto Pedido. */
    private void atualizarTela() {
        painelItens.removeAll();

        if (pedido.estaVazio()) {
            painelItens.add(criarMensagemPedidoVazio());
        } else {
            painelItens.add(criarLinhaCabecalho());
            for (ItemPedido item : pedido.getItens()) {
                painelItens.add(Box.createVerticalStrut(10));
                painelItens.add(criarLinhaItem(item));
            }
        }

        int quantidade = pedido.getQuantidadeTotal();
        lblQuantidade.setText((quantidade == 1 ? "1 item" : quantidade + " itens") + " no pedido");
        lblTotal.setText("Total: " + Estilo.formatarPreco(pedido.calcularTotal()));

        painelItens.revalidate();
        painelItens.repaint();
    }

    private JPanel criarMensagemPedidoVazio() {
        PainelArredondado cartao = new PainelArredondado(new GridBagLayout(), Estilo.CARD, Estilo.BORDA, 24);
        cartao.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        IconeChama chama = new IconeChama(44, 56, Estilo.BORDA, null);
        chama.setMaximumSize(chama.getPreferredSize());
        JLabel titulo = Estilo.criarRotulo("Seu pedido está vazio", Estilo.FONTE_SUBTITULO, Estilo.VINHO);
        JLabel dica = Estilo.criarRotulo("Volte ao cardápio e adicione seus lanches favoritos.",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO);
        chama.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        dica.setAlignmentX(CENTER_ALIGNMENT);
        textos.add(chama);
        textos.add(Box.createVerticalStrut(12));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(dica);

        cartao.add(textos);
        return cartao;
    }

    private JPanel criarLinhaCabecalho() {
        JPanel linha = new JPanel(new GridBagLayout());
        linha.setOpaque(false);
        linha.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        String[] titulos = {"Produto", "Quantidade", "Preço unitário", "Subtotal", ""};
        for (int i = 0; i < titulos.length; i++) {
            JLabel rotulo = Estilo.criarRotulo(titulos[i], Estilo.FONTE_PEQUENA, Estilo.TEXTO_SECUNDARIO);
            adicionarColuna(linha, rotulo, i);
        }
        return linha;
    }

    private JPanel criarLinhaItem(ItemPedido item) {
        PainelArredondado linha = new PainelArredondado(new GridBagLayout(), Estilo.CARD, Estilo.BORDA, 20);
        linha.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        // GridLayout limita a largura dos textos; descrições longas terminam com "..."
        JPanel produto = new JPanel(new GridLayout(2, 1));
        produto.setOpaque(false);
        produto.add(Estilo.criarRotulo(item.getProduto().getNome(), Estilo.FONTE_NEGRITO, Estilo.VINHO));
        produto.add(Estilo.criarRotulo(item.getProduto().getDescricao(), Estilo.FONTE_PEQUENA,
                Estilo.TEXTO_SECUNDARIO));

        JLabel quantidade = Estilo.criarRotulo(item.getQuantidade() + "x", Estilo.FONTE_NEGRITO, Estilo.TEXTO);
        JLabel precoUnitario = Estilo.criarRotulo(Estilo.formatarPreco(item.getProduto().getPreco()),
                Estilo.FONTE_NORMAL, Estilo.TEXTO);
        JLabel subtotal = Estilo.criarRotulo(Estilo.formatarPreco(item.calcularSubtotal()),
                Estilo.FONTE_NEGRITO, Estilo.VERMELHO);

        BotaoModerno botaoRemover = BotaoModerno.perigo("Remover");
        botaoRemover.addActionListener(e -> removerItem(item));
        JPanel areaBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        areaBotao.setOpaque(false);
        areaBotao.add(botaoRemover);

        adicionarColuna(linha, produto, 0);
        adicionarColuna(linha, quantidade, 1);
        adicionarColuna(linha, precoUnitario, 2);
        adicionarColuna(linha, subtotal, 3);
        adicionarColuna(linha, areaBotao, 4);
        return linha;
    }

    /** Posiciona um componente na coluna indicada, mantendo as colunas alinhadas entre as linhas. */
    private void adicionarColuna(JPanel linha, JComponent componente, int coluna) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = coluna;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, coluna == 0 ? 0 : 10, 0, 0);

        if (coluna == 0) {
            // A coluna "Produto" ocupa todo o espaço que sobrar
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            componente.setMinimumSize(new Dimension(0, componente.getPreferredSize().height));
            componente.setPreferredSize(new Dimension(0, componente.getPreferredSize().height));
        } else {
            Dimension tamanho = new Dimension(LARGURAS_COLUNAS[coluna], componente.getPreferredSize().height);
            componente.setPreferredSize(tamanho);
            componente.setMinimumSize(tamanho);
        }
        if (componente instanceof JLabel && coluna > 0 && coluna < 4) {
            ((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
        }
        linha.add(componente, gbc);
    }

    private void removerItem(ItemPedido item) {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja remover " + item.getProduto().getNome() + " do pedido?",
                "Remover produto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            pedido.removerItem(item);
            atualizarTela();
        }
    }

    /** Fecha esta tela e reexibe o cardápio, que continua com o mesmo objeto Pedido. */
    private void voltarAoCardapio() {
        telaCardapio.atualizarResumo();
        telaCardapio.setLocation(getLocation());
        telaCardapio.setSize(getSize());
        telaCardapio.setVisible(true);
        dispose();
    }
}
