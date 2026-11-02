package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import snackflow.model.Cardapio;
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

    private JLabel lblContadorItens;
    private JLabel lblResumo;
    private JLabel lblMensagem;
    private Timer timerMensagem;

    public CardapioView(Cardapio cardapio, Pedido pedido) {
        this.cardapio = cardapio;
        this.pedido = pedido;

        setTitle("SnackFlow - Cardápio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(820, 640));
        setSize(980, 800);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Estilo.FUNDO);
        raiz.add(criarCabecalho(), BorderLayout.NORTH);
        raiz.add(criarConteudo(), BorderLayout.CENTER);
        raiz.add(criarRodape(), BorderLayout.SOUTH);
        setContentPane(raiz);

        atualizarResumo();
    }

    private JPanel criarCabecalho() {
        PainelArredondado selo = new PainelArredondado(new BorderLayout(), Estilo.LARANJA, null, 30);
        selo.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        lblContadorItens = Estilo.criarRotulo("", Estilo.FONTE_NEGRITO, Estilo.TEXTO_CLARO);
        selo.add(lblContadorItens);

        return Estilo.criarCabecalho(selo);
    }

    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 20));
        conteudo.setBackground(Estilo.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(26, 28, 26, 28));

        JPanel titulos = new JPanel();
        titulos.setOpaque(false);
        titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));
        titulos.add(Estilo.criarRotulo("Nosso Cardápio", Estilo.FONTE_TITULO, Estilo.VINHO));
        titulos.add(Estilo.criarRotulo("Escolha seus favoritos e monte o seu pedido.",
                Estilo.FONTE_NORMAL, Estilo.TEXTO_SECUNDARIO));

        JPanel grade = new JPanel(new GridLayout(0, 2, 20, 20));
        grade.setOpaque(false);
        for (Produto produto : cardapio.getProdutos()) {
            grade.add(new CardProduto(produto, this));
        }

        conteudo.add(titulos, BorderLayout.NORTH);
        conteudo.add(grade, BorderLayout.CENTER);

        JScrollPane rolagem = new JScrollPane(conteudo);
        rolagem.setBorder(null);
        rolagem.getViewport().setBackground(Estilo.FUNDO);
        rolagem.getVerticalScrollBar().setUnitIncrement(16);
        return rolagem;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(Estilo.VINHO);
        rodape.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 0, 0, 0, Estilo.LARANJA),
                BorderFactory.createEmptyBorder(14, 28, 14, 28)));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        lblResumo = Estilo.criarRotulo("", Estilo.FONTE_SUBTITULO, Estilo.CREME);
        lblMensagem = Estilo.criarRotulo(" ", Estilo.FONTE_PEQUENA, Estilo.TEXTO_CLARO);
        textos.add(lblResumo);
        textos.add(lblMensagem);

        BotaoModerno botaoVerPedido = BotaoModerno.primario("Ver meu pedido");
        botaoVerPedido.addActionListener(e -> abrirTelaPedido());

        rodape.add(textos, BorderLayout.CENTER);
        rodape.add(botaoVerPedido, BorderLayout.EAST);
        return rodape;
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

        lblContadorItens.setText("Meu pedido: " + textoItens);
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
