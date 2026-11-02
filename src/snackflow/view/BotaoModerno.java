package snackflow.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Botão com cantos arredondados e efeito ao passar o mouse.
 * Use os métodos primario(), secundario() e perigo() para criar cada estilo.
 */
public class BotaoModerno extends JButton {

    private Color corFundo;
    private Color corHover;
    private Color corBorda;
    private boolean mouseEmCima = false;

    public BotaoModerno(String texto, Color corFundo, Color corHover, Color corTexto, Color corBorda) {
        super(texto);
        this.corFundo = corFundo;
        this.corHover = corHover;
        this.corBorda = corBorda;

        setForeground(corTexto);
        setFont(Estilo.FONTE_NEGRITO);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEmCima = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseEmCima = false;
                repaint();
            }
        });
    }

    /** Botão laranja, para a ação principal. */
    public static BotaoModerno primario(String texto) {
        return new BotaoModerno(texto, Estilo.LARANJA, Estilo.LARANJA_ESCURO, Estilo.TEXTO_CLARO, null);
    }

    /** Botão contornado em creme, para usar sobre o fundo vinho. */
    public static BotaoModerno secundario(String texto) {
        return new BotaoModerno(texto, Estilo.VINHO, Estilo.VINHO_CLARO, Estilo.CREME, Estilo.CREME);
    }

    /** Botão vermelho, para ações de remoção. */
    public static BotaoModerno perigo(String texto) {
        return new BotaoModerno(texto, Estilo.VERMELHO, Estilo.VERMELHO_ESCURO, Estilo.TEXTO_CLARO, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!isEnabled()) {
            g2.setColor(Estilo.BORDA);
        } else if (mouseEmCima) {
            g2.setColor(corHover);
        } else {
            g2.setColor(corFundo);
        }
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

        if (corBorda != null && isEnabled()) {
            g2.setColor(corBorda);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
