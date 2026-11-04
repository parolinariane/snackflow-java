package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import snackflow.model.ItemPedido;
import snackflow.model.Produto;

/**
 * Card que exibe um produto do cardápio: ilustração, nome, descrição, preço,
 * seletor de quantidade e o botão "Adicionar ao pedido".
 */
public class CardProduto extends PainelArredondado {

    private Produto produto;
    private CardapioView telaCardapio;
    private SeletorQuantidade seletorQuantidade;

    public CardProduto(Produto produto, CardapioView telaCardapio) {
        super(new BorderLayout(0, 12), Estilo.CARD, null, 26);
        this.produto = produto;
        this.telaCardapio = telaCardapio;

        setComSombra(true);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 16 + TAMANHO_SOMBRA, 12));
        add(new IlustracaoProduto(produto, 200, 130), BorderLayout.NORTH);
        add(criarTextos(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
    }

    private JPanel criarTextos() {
        JPanel textos = new JPanel(new BorderLayout(0, 4));
        textos.setOpaque(false);
        textos.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

        JTextArea descricao = new JTextArea(produto.getDescricao());
        descricao.setFont(Estilo.FONTE_NORMAL.deriveFont(13f));
        descricao.setForeground(Estilo.TEXTO_SECUNDARIO);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setEditable(false);
        descricao.setFocusable(false);
        descricao.setOpaque(false);
        descricao.setRows(3);
        // Impede que o cursor de texto faça a tela rolar sozinha até esta descrição
        ((DefaultCaret) descricao.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        JPanel categoriaENome = new JPanel(new BorderLayout(0, 2));
        categoriaENome.setOpaque(false);
        categoriaENome.add(Estilo.criarRotulo(produto.getCategoria().getDescricao().toUpperCase(),
                Estilo.FONTE_PEQUENA.deriveFont(Font.BOLD, 11f), Estilo.LARANJA_ESCURO), BorderLayout.NORTH);
        categoriaENome.add(Estilo.criarRotulo(produto.getNome(), Estilo.FONTE_SUBTITULO, Estilo.VINHO),
                BorderLayout.CENTER);

        textos.add(categoriaENome, BorderLayout.NORTH);
        textos.add(descricao, BorderLayout.CENTER);
        return textos;
    }

    public Produto getProduto() {
        return produto;
    }

    private JPanel criarRodape() {
        seletorQuantidade = new SeletorQuantidade(1, ItemPedido.QUANTIDADE_MAXIMA);

        JPanel precoEQuantidade = new JPanel(new BorderLayout());
        precoEQuantidade.setOpaque(false);
        precoEQuantidade.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        precoEQuantidade.add(Estilo.criarRotulo(Estilo.formatarPreco(produto.getPreco()),
                Estilo.FONTE_PRECO, Estilo.VERMELHO), BorderLayout.WEST);
        precoEQuantidade.add(seletorQuantidade, BorderLayout.EAST);

        BotaoModerno botaoAdicionar = BotaoModerno.primario("Adicionar ao pedido");
        botaoAdicionar.addActionListener(e -> adicionarAoPedido());

        JPanel rodape = new JPanel(new BorderLayout(0, 12));
        rodape.setOpaque(false);
        rodape.add(precoEQuantidade, BorderLayout.NORTH);
        rodape.add(botaoAdicionar, BorderLayout.SOUTH);
        return rodape;
    }

    private void adicionarAoPedido() {
        int quantidade = seletorQuantidade.getQuantidade();
        if (telaCardapio.adicionarAoPedido(produto, quantidade)) {
            seletorQuantidade.setQuantidade(1);
        }
    }
}
