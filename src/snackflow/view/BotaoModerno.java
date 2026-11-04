package snackflow.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class BotaoModerno extends JButton {

    private static final Color FUNDO_DESABILITADO = new Color(0xF1E4D2);

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
        setBorder(BorderFactory.createEmptyBorder(11, 22, 11, 22));

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

    public static BotaoModerno primario(String texto) {
        return new BotaoModerno(texto, Estilo.LARANJA, Estilo.LARANJA_ESCURO, Estilo.TEXTO_CLARO, null);
    }

    public static BotaoModerno contorno(String texto) {
        return new BotaoModerno(texto, Estilo.CARD, new Color(0xFCE9CC), Estilo.LARANJA_ESCURO, Estilo.LARANJA);
    }

    public static BotaoModerno perigo(String texto) {
        return new BotaoModerno(texto, new Color(0xFBE1DC), new Color(0xF6C9C1), Estilo.VERMELHO, null);
    }

    public static BotaoModerno filtro(String texto) {
        BotaoModerno botao = contorno(texto);
        botao.setBorder(BorderFactory.createEmptyBorder(7, 18, 7, 18));
        return botao;
    }

    public void setSelecionado(boolean selecionado) {
        if (selecionado) {
            alterarCores(Estilo.LARANJA, Estilo.LARANJA_ESCURO, Estilo.TEXTO_CLARO, null);
        } else {
            alterarCores(Estilo.CARD, new Color(0xFCE9CC), Estilo.LARANJA_ESCURO, Estilo.LARANJA);
        }
    }

    public void alterarCores(Color corFundo, Color corHover, Color corTexto, Color corBorda) {
        this.corFundo = corFundo;
        this.corHover = corHover;
        this.corBorda = corBorda;
        setForeground(corTexto);
        repaint();
    }

    public static BotaoModerno redondo(String simbolo) {
        BotaoModerno botao = new BotaoModerno(simbolo, Estilo.CREME, new Color(0xF2B66E), Estilo.VINHO, null);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBorder(BorderFactory.createEmptyBorder());
        botao.setPreferredSize(new Dimension(32, 32));
        return botao;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!isEnabled()) {
            g2.setColor(FUNDO_DESABILITADO);
        } else if (mouseEmCima) {
            g2.setColor(corHover);
        } else {
            g2.setColor(corFundo);
        }
    
        int arredondamento = getHeight();
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arredondamento, arredondamento);

        if (corBorda != null && isEnabled()) {
            g2.setColor(corBorda);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arredondamento, arredondamento);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
