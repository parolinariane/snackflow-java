package snackflow.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Painel com cantos arredondados, usado para criar os "cards" da interface.
 */
public class PainelArredondado extends JPanel {

    private Color corFundo;
    private Color corBorda;
    private int arredondamento;

    public PainelArredondado(LayoutManager layout, Color corFundo, Color corBorda, int arredondamento) {
        super(layout);
        this.corFundo = corFundo;
        this.corBorda = corBorda;
        this.arredondamento = arredondamento;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(corFundo);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arredondamento, arredondamento);

        if (corBorda != null) {
            g2.setColor(corBorda);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arredondamento, arredondamento);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
