package snackflow.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Painel com cantos arredondados e sombra opcional, usado para criar os "cards" da interface.
 */
public class PainelArredondado extends JPanel {

    // Espaço reservado na parte de baixo do painel para desenhar a sombra
    public static final int TAMANHO_SOMBRA = 5;

    private Color corFundo;
    private Color corBorda;
    private int arredondamento;
    private boolean comSombra = false;

    public PainelArredondado(LayoutManager layout, Color corFundo, Color corBorda, int arredondamento) {
        super(layout);
        this.corFundo = corFundo;
        this.corBorda = corBorda;
        this.arredondamento = arredondamento;
        setOpaque(false);
    }

    public void setComSombra(boolean comSombra) {
        this.comSombra = comSombra;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int largura = getWidth() - 1;
        int altura = getHeight() - 1 - (comSombra ? TAMANHO_SOMBRA : 0);

        if (comSombra) {
            // Duas camadas vinho bem transparentes, deslocadas para baixo, simulam uma sombra suave
            g2.setColor(new Color(78, 21, 21, 14));
            g2.fillRoundRect(0, TAMANHO_SOMBRA, largura, altura, arredondamento, arredondamento);
            g2.setColor(new Color(78, 21, 21, 18));
            g2.fillRoundRect(1, 3, largura - 2, altura, arredondamento, arredondamento);
        }

        g2.setColor(corFundo);
        g2.fillRoundRect(0, 0, largura, altura, arredondamento, arredondamento);

        if (corBorda != null) {
            g2.setColor(corBorda);
            g2.drawRoundRect(0, 0, largura, altura, arredondamento, arredondamento);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
