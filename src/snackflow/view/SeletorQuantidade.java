package snackflow.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class SeletorQuantidade extends JPanel {

    private int quantidade;
    private int minimo;
    private int maximo;

    private JLabel lblQuantidade;
    private BotaoModerno botaoMenos;
    private BotaoModerno botaoMais;
    private Runnable acaoAoAlterar;

    public SeletorQuantidade(int minimo, int maximo) {
        super(new FlowLayout(FlowLayout.CENTER, 4, 0));
        this.minimo = minimo;
        this.maximo = maximo;
        setOpaque(false);

        botaoMenos = BotaoModerno.redondo("−");
        botaoMais = BotaoModerno.redondo("+");
        lblQuantidade = Estilo.criarRotulo("", Estilo.FONTE_NEGRITO.deriveFont(16f), Estilo.VINHO);
        lblQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantidade.setPreferredSize(new Dimension(30, 32));

        botaoMenos.setToolTipText("Diminuir quantidade");
        botaoMais.setToolTipText("Aumentar quantidade");
        botaoMenos.addActionListener(e -> alterarPeloUsuario(quantidade - 1));
        botaoMais.addActionListener(e -> alterarPeloUsuario(quantidade + 1));

        add(botaoMenos);
        add(lblQuantidade);
        add(botaoMais);

        setQuantidade(minimo);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setAcaoAoAlterar(Runnable acaoAoAlterar) {
        this.acaoAoAlterar = acaoAoAlterar;
    }

    private void alterarPeloUsuario(int novaQuantidade) {
        int anterior = quantidade;
        setQuantidade(novaQuantidade);
        if (quantidade != anterior && acaoAoAlterar != null) {
            acaoAoAlterar.run();
        }
    }

    public void setQuantidade(int novaQuantidade) {
        if (novaQuantidade < minimo || novaQuantidade > maximo) {
            return;
        }
        quantidade = novaQuantidade;
        lblQuantidade.setText(String.valueOf(quantidade));
        botaoMenos.setEnabled(quantidade > minimo);
        botaoMais.setEnabled(quantidade < maximo);
    }
}
