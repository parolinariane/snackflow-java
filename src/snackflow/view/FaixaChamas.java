package snackflow.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * Faixa decorativa com chamas repetidas, inspirada nas estampas da identidade visual.
 */
public class FaixaChamas extends JComponent {

    private static final int ALTURA = 26;
    private static final int ESPACO = 30;

    public FaixaChamas() {
        setPreferredSize(new Dimension(100, ALTURA));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Estilo.CREME);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Alterna a altura das chamas para dar um efeito de estampa
        for (int i = 0, x = 8; x < getWidth(); i++, x += ESPACO) {
            int deslocamento = (i % 2 == 0) ? 3 : 6;
            IconeChama.desenhar(g2, x, deslocamento, 13, 17, Estilo.LARANJA, null);
        }
        g2.dispose();
    }
}
