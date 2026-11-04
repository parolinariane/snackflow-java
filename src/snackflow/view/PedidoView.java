package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

    // Largura fixa das colunas: ilustração, produto (ocupa o resto), quantidade, subtotal e ação
    private static final int[] LARGURAS_COLUNAS = {64, 0, 116, 100, 110};
    private static final int LARGURA_RESUMO = 300;

    private Pedido pedido;
    private CardapioView telaCardapio;

    private JPanel painelItens;
    private JLabel lblQuantidade;
    private JLabel lblTotal;
    private BotaoModerno botaoEsvaziar;

    public PedidoView(Pedido pedido, CardapioView telaCardapio) {
        this.pedido = pedido;
        this.telaCardapio = telaCardapio;

        setTitle("SnackFlow - Meu Pedido");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(telaCardapio.getMinimumSize());
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
        setContentPane(raiz);

        atualizarTela();
    }

    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout(24, 22));
        conteudo.setBackground(Estilo.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(26, 28, 24, 28));

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
        JPanel alinhamentoItens = new JPanel(new BorderLayout());
        alinhamentoItens.setBackground(Estilo.FUNDO);
        alinhamentoItens.add(painelItens, BorderLayout.NORTH);

        JScrollPane rolagem = new JScrollPane(alinhamentoItens);
        rolagem.setBorder(null);
        rolagem.getViewport().setBackground(Estilo.FUNDO);
        rolagem.getVerticalScrollBar().setUnitIncrement(16);

        JPanel alinhamentoResumo = new JPanel(new BorderLayout());
        alinhamentoResumo.setOpaque(false);
        alinhamentoResumo.add(criarResumo(), BorderLayout.NORTH);

        conteudo.add(titulos, BorderLayout.NORTH);
        conteudo.add(rolagem, BorderLayout.CENTER);
        conteudo.add(alinhamentoResumo, BorderLayout.EAST);
        return conteudo;
    }

    /** Card lateral com a quantidade de itens, o total e o botão de voltar. */
    private JPanel criarResumo() {
        PainelArredondado resumo = new PainelArredondado(null, Estilo.CARD, null, 26);
        resumo.setLayout(new BoxLayout(resumo, BoxLayout.Y_AXIS));
        resumo.setComSombra(true);
        resumo.setBorder(BorderFactory.createEmptyBorder(22, 22, 22 + PainelArredondado.TAMANHO_SOMBRA, 22));

        lblQuantidade = Estilo.criarRotulo("", Estilo.FONTE_NEGRITO, Estilo.TEXTO);
        lblTotal = Estilo.criarRotulo("", Estilo.FONTE_SUBTITULO.deriveFont(26f), Estilo.VERMELHO);

        JPanel divisor = new JPanel();
        divisor.setBackground(Estilo.BORDA);
        divisor.setPreferredSize(new Dimension(0, 1));
        divisor.setMinimumSize(new Dimension(0, 1));
        divisor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divisor.setAlignmentX(LEFT_ALIGNMENT);

        BotaoModerno botaoVoltar = BotaoModerno.contorno("Voltar ao cardápio");
        botaoVoltar.addActionListener(e -> voltarAoCardapio());
        botaoVoltar.setAlignmentX(LEFT_ALIGNMENT);
        botaoVoltar.setMaximumSize(new Dimension(Integer.MAX_VALUE, botaoVoltar.getPreferredSize().height));

        botaoEsvaziar = BotaoModerno.perigo("Esvaziar pedido");
        botaoEsvaziar.addActionListener(e -> esvaziarPedido());
        botaoEsvaziar.setAlignmentX(LEFT_ALIGNMENT);
        botaoEsvaziar.setMaximumSize(new Dimension(Integer.MAX_VALUE, botaoEsvaziar.getPreferredSize().height));

        resumo.add(Estilo.criarRotulo("Resumo do pedido", Estilo.FONTE_SUBTITULO, Estilo.VINHO));
        resumo.add(Box.createVerticalStrut(18));
        resumo.add(criarLinhaResumo(Estilo.criarRotulo("Itens", Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO),
                lblQuantidade));
        resumo.add(Box.createVerticalStrut(16));
        resumo.add(divisor);
        resumo.add(Box.createVerticalStrut(16));
        resumo.add(criarLinhaResumo(Estilo.criarRotulo("Total", Estilo.FONTE_SUBTITULO, Estilo.VINHO), lblTotal));
        resumo.add(Box.createVerticalStrut(24));
        resumo.add(botaoVoltar);
        resumo.add(Box.createVerticalStrut(10));
        resumo.add(botaoEsvaziar);

        resumo.setPreferredSize(new Dimension(LARGURA_RESUMO, resumo.getPreferredSize().height));
        return resumo;
    }

    private JPanel criarLinhaResumo(JLabel rotulo, JLabel valor) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);
        linha.add(rotulo, BorderLayout.WEST);
        linha.add(valor, BorderLayout.EAST);
        linha.setAlignmentX(LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, linha.getPreferredSize().height));
        return linha;
    }

    /** Reconstrói a lista de itens e o total a partir do objeto Pedido. */
    private void atualizarTela() {
        painelItens.removeAll();

        if (pedido.estaVazio()) {
            painelItens.add(criarMensagemPedidoVazio());
        } else {
            for (ItemPedido item : pedido.getItens()) {
                painelItens.add(criarLinhaItem(item));
                painelItens.add(Box.createVerticalStrut(10));
            }
        }

        lblQuantidade.setText(String.valueOf(pedido.getQuantidadeTotal()));
        lblTotal.setText(Estilo.formatarPreco(pedido.calcularTotal()));
        botaoEsvaziar.setEnabled(!pedido.estaVazio());

        painelItens.revalidate();
        painelItens.repaint();
    }

    private JPanel criarMensagemPedidoVazio() {
        PainelArredondado cartao = new PainelArredondado(new GridBagLayout(), Estilo.CARD, null, 26);
        cartao.setComSombra(true);
        cartao.setBorder(BorderFactory.createEmptyBorder(48, 20, 48 + PainelArredondado.TAMANHO_SOMBRA, 20));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        IconeChama chama = new IconeChama(48, 60, Estilo.BORDA, null);
        chama.setMaximumSize(chama.getPreferredSize());
        JLabel titulo = Estilo.criarRotulo("Seu pedido está vazio", Estilo.FONTE_SUBTITULO, Estilo.VINHO);
        JLabel dica = Estilo.criarRotulo("Volte ao cardápio e adicione seus lanches favoritos.",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO);
        chama.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        dica.setAlignmentX(CENTER_ALIGNMENT);
        textos.add(chama);
        textos.add(Box.createVerticalStrut(14));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(dica);

        cartao.add(textos);
        return cartao;
    }

    private JPanel criarLinhaItem(ItemPedido item) {
        PainelArredondado linha = new PainelArredondado(new GridBagLayout(), Estilo.CARD, null, 22);
        linha.setComSombra(true);
        linha.setBorder(BorderFactory.createEmptyBorder(12, 12, 12 + PainelArredondado.TAMANHO_SOMBRA, 18));

        // Nome do produto, preço unitário e observação
        JPanel produto = new JPanel(new GridLayout(3, 1));
        produto.setOpaque(false);
        produto.add(Estilo.criarRotulo(item.getProduto().getNome(), Estilo.FONTE_SUBTITULO.deriveFont(17f),
                Estilo.VINHO));
        produto.add(Estilo.criarRotulo(Estilo.formatarPreco(item.getProduto().getPreco()) + " cada",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO));
        produto.add(criarLinkObservacao(item));

        // A quantidade pode ser alterada aqui mesmo; o total é recalculado pelo Pedido
        SeletorQuantidade seletor = new SeletorQuantidade(1, ItemPedido.QUANTIDADE_MAXIMA);
        seletor.setQuantidade(item.getQuantidade());
        seletor.setAcaoAoAlterar(() -> alterarQuantidade(item, seletor.getQuantidade()));
        JPanel quantidade = criarColunaComLegenda("Quantidade", seletor);

        JPanel subtotal = criarColunaComLegenda("Subtotal", Estilo.criarRotulo(
                Estilo.formatarPreco(item.calcularSubtotal()), Estilo.FONTE_NEGRITO.deriveFont(16f), Estilo.VERMELHO));

        BotaoModerno botaoRemover = BotaoModerno.perigo("Remover");
        botaoRemover.addActionListener(e -> removerItem(item));
        JPanel areaBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        areaBotao.setOpaque(false);
        areaBotao.add(botaoRemover);

        adicionarColuna(linha, new IlustracaoProduto(item.getProduto(), 64, 64), 0);
        adicionarColuna(linha, produto, 1);
        adicionarColuna(linha, quantidade, 2);
        adicionarColuna(linha, subtotal, 3);
        adicionarColuna(linha, areaBotao, 4);
        return linha;
    }

    /** Coluna com uma legenda pequena em cima e o valor embaixo. */
    private JPanel criarColunaComLegenda(String legenda, JComponent valor) {
        JPanel valorAlinhado = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        valorAlinhado.setOpaque(false);
        valorAlinhado.add(valor);

        JPanel coluna = new JPanel(new BorderLayout(0, 4));
        coluna.setOpaque(false);
        coluna.add(Estilo.criarRotulo(legenda, Estilo.FONTE_PEQUENA, Estilo.TEXTO_SECUNDARIO), BorderLayout.NORTH);
        coluna.add(valorAlinhado, BorderLayout.CENTER);
        return coluna;
    }

    /** Posiciona um componente na coluna indicada, mantendo as colunas alinhadas entre as linhas. */
    private void adicionarColuna(JPanel linha, JComponent componente, int coluna) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = coluna;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, coluna == 0 ? 0 : 16, 0, 0);

        int altura = componente.getPreferredSize().height;
        if (LARGURAS_COLUNAS[coluna] == 0) {
            // A coluna do produto ocupa todo o espaço que sobrar
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            componente.setMinimumSize(new Dimension(0, altura));
            componente.setPreferredSize(new Dimension(0, altura));
        } else {
            Dimension tamanho = new Dimension(LARGURAS_COLUNAS[coluna], altura);
            componente.setPreferredSize(tamanho);
            componente.setMinimumSize(tamanho);
        }
        linha.add(componente, gbc);
    }

    /** Texto clicável que mostra a observação do item ou convida a adicionar uma. */
    private JButton criarLinkObservacao(ItemPedido item) {
        String texto = item.temObservacao() ? "Obs.: " + item.getObservacao() : "+ Adicionar observação";
        JButton link = new JButton(texto);
        link.setFont(Estilo.FONTE_PEQUENA.deriveFont(Font.BOLD));
        link.setForeground(Estilo.LARANJA_ESCURO);
        link.setContentAreaFilled(false);
        link.setBorderPainted(false);
        link.setFocusPainted(false);
        link.setBorder(BorderFactory.createEmptyBorder());
        link.setHorizontalAlignment(SwingConstants.LEFT);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.setToolTipText("Clique para escrever ou editar a observação");
        link.addActionListener(e -> editarObservacao(item));
        return link;
    }

    private void editarObservacao(ItemPedido item) {
        Object resposta = JOptionPane.showInputDialog(this,
                "Observação para " + item.getProduto().getNome()
                        + " (até " + ItemPedido.TAMANHO_MAXIMO_OBSERVACAO + " caracteres):",
                "Observação", JOptionPane.PLAIN_MESSAGE, null, null, item.getObservacao());

        if (resposta == null) {
            return; // o usuário cancelou
        }
        try {
            item.setObservacao(resposta.toString());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Observação inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        atualizarTela();
    }

    private void alterarQuantidade(ItemPedido item, int novaQuantidade) {
        try {
            pedido.alterarQuantidade(item, novaQuantidade);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Quantidade inválida", JOptionPane.WARNING_MESSAGE);
        }
        atualizarTela();
    }

    private void esvaziarPedido() {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja remover todos os produtos do pedido?",
                "Esvaziar pedido", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            pedido.limparPedido();
            atualizarTela();
        }
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
