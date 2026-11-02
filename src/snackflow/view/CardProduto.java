package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import snackflow.model.Produto;

/**
 * Card que exibe um produto do cardápio, com seletor de quantidade
 * e o botão "Adicionar ao pedido".
 */
public class CardProduto extends PainelArredondado {

    private static final int QUANTIDADE_MAXIMA = 20;

    private Produto produto;
    private CardapioView telaCardapio;
    private JSpinner spinnerQuantidade;

    public CardProduto(Produto produto, CardapioView telaCardapio) {
        super(new BorderLayout(0, 14), Estilo.CARD, Estilo.BORDA, 24);
        this.produto = produto;
        this.telaCardapio = telaCardapio;

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(criarTopo(), BorderLayout.NORTH);
        add(criarDescricao(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
    }

    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout(14, 0));
        topo.setOpaque(false);

        // "Selo" do produto: a inicial do nome dentro de um círculo vinho
        PainelArredondado icone = new PainelArredondado(new GridBagLayout(), Estilo.VINHO, null, 60);
        icone.setPreferredSize(new Dimension(60, 60));
        icone.add(Estilo.criarRotulo(produto.getNome().substring(0, 1),
                Estilo.FONTE_TITULO.deriveFont(28f), Estilo.CREME));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(Estilo.criarRotulo(produto.getNome(), Estilo.FONTE_SUBTITULO, Estilo.VINHO));
        textos.add(Estilo.criarRotulo(Estilo.formatarPreco(produto.getPreco()), Estilo.FONTE_PRECO, Estilo.VERMELHO));

        topo.add(icone, BorderLayout.WEST);
        topo.add(textos, BorderLayout.CENTER);
        return topo;
    }

    private JTextArea criarDescricao() {
        JTextArea descricao = new JTextArea(produto.getDescricao());
        descricao.setFont(Estilo.FONTE_NORMAL);
        descricao.setForeground(Estilo.TEXTO_SECUNDARIO);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setEditable(false);
        descricao.setFocusable(false);
        descricao.setOpaque(false);
        descricao.setRows(2);
        return descricao;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(10, 0));
        rodape.setOpaque(false);

        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, QUANTIDADE_MAXIMA, 1));
        spinnerQuantidade.setFont(Estilo.FONTE_NEGRITO);
        JFormattedTextField campo = ((JSpinner.DefaultEditor) spinnerQuantidade.getEditor()).getTextField();
        campo.setColumns(2);
        campo.setHorizontalAlignment(JFormattedTextField.CENTER);
        // Mantém o texto digitado (mesmo inválido) para podermos avisar o usuário
        campo.setFocusLostBehavior(JFormattedTextField.PERSIST);

        JPanel quantidade = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        quantidade.setOpaque(false);
        quantidade.add(Estilo.criarRotulo("Qtd.", Estilo.FONTE_NORMAL, Estilo.TEXTO));
        quantidade.add(spinnerQuantidade);

        BotaoModerno botaoAdicionar = BotaoModerno.primario("Adicionar ao pedido");
        botaoAdicionar.addActionListener(e -> adicionarAoPedido());

        JPanel alinhamentoQuantidade = new JPanel(new GridBagLayout());
        alinhamentoQuantidade.setOpaque(false);
        alinhamentoQuantidade.add(quantidade);

        rodape.add(alinhamentoQuantidade, BorderLayout.WEST);
        rodape.add(botaoAdicionar, BorderLayout.EAST);
        return rodape;
    }

    private void adicionarAoPedido() {
        try {
            // Confirma o valor digitado; lança exceção se for inválido ou fora do intervalo
            spinnerQuantidade.commitEdit();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Informe uma quantidade entre 1 e " + QUANTIDADE_MAXIMA + ".",
                    "Quantidade inválida", JOptionPane.WARNING_MESSAGE);
            spinnerQuantidade.setValue(1);
            ((JSpinner.DefaultEditor) spinnerQuantidade.getEditor()).getTextField().setValue(1);
            return;
        }

        int quantidade = (Integer) spinnerQuantidade.getValue();
        if (telaCardapio.adicionarAoPedido(produto, quantidade)) {
            spinnerQuantidade.setValue(1);
        }
    }
}
