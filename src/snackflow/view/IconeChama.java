package snackflow.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import javax.swing.JComponent;


public class IconeChama extends JComponent {

    private Color corExterna;
    private Color corInterna;

    public IconeChama(int largura, int altura, Color corExterna, Color corInterna) {
        this.corExterna = corExterna;
        this.corInterna = corInterna;
        setPreferredSize(new Dimension(largura, altura));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        desenhar(g2, 0, 0, getWidth(), getHeight(), corExterna, corInterna);
        g2.dispose();
    }

    public static void desenhar(Graphics2D g, double x, double y, double largura, double altura,
                                Color corExterna, Color corInterna) {
        Path2D externa = new Path2D.Double();
        externa.moveTo(0.50, 1.00);
        externa.curveTo(0.20, 1.00, 0.04, 0.80, 0.12, 0.56);
        externa.curveTo(0.16, 0.44, 0.22, 0.38, 0.22, 0.26);
        externa.curveTo(0.32, 0.36, 0.36, 0.44, 0.37, 0.50);
        externa.curveTo(0.36, 0.30, 0.44, 0.12, 0.60, 0.00);
        externa.curveTo(0.58, 0.18, 0.66, 0.30, 0.72, 0.38);
        externa.curveTo(0.76, 0.32, 0.80, 0.26, 0.82, 0.18);
        externa.curveTo(0.96, 0.40, 0.98, 0.62, 0.90, 0.78);
        externa.curveTo(0.82, 0.94, 0.68, 1.00, 0.50, 1.00);
        externa.closePath();

        Path2D interna = new Path2D.Double();
        interna.moveTo(0.50, 0.94);
        interna.curveTo(0.33, 0.94, 0.27, 0.80, 0.33, 0.68);
        interna.curveTo(0.38, 0.59, 0.46, 0.52, 0.50, 0.42);
        interna.curveTo(0.57, 0.53, 0.70, 0.62, 0.70, 0.76);
        interna.curveTo(0.70, 0.87, 0.62, 0.94, 0.50, 0.94);
        interna.closePath();

        AffineTransform escala = new AffineTransform();
        escala.translate(x, y);
        escala.scale(largura, altura);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(corExterna);
        g.fill(escala.createTransformedShape(externa));
        if (corInterna != null) {
            g.setColor(corInterna);
            g.fill(escala.createTransformedShape(interna));
        }
    }
}
