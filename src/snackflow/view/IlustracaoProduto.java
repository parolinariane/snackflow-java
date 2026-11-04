package snackflow.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import snackflow.model.Produto;


public class IlustracaoProduto extends JComponent {

    private static final Color PAO = new Color(0xE9A23B);
    private static final Color PAO_CLARO = new Color(0xF4C06A);
    private static final Color QUEIJO = new Color(0xF7C948);
    private static final Color ALFACE = new Color(0x8DB33A);
    private static final Color BATATA = new Color(0xF5C242);
    private static final Color BATATA_CONTORNO = new Color(0xDDA22A);
    private static final Color BACON = new Color(0xB5452F);
    private static final Color BACON_CLARO = new Color(0xEE9A80);
    private static final Color EMPANADO = new Color(0xE3A43A);
    private static final Color EMPANADO_CLARO = new Color(0xF6CD6E);
    private static final Color SUCO = new Color(0xF29A3A);
    private static final Color CHOCOLATE = new Color(0x5A2E1F);
    private static final Color CHOCOLATE_CLARO = new Color(0x74402B);
    private static final Color SOMBRA = new Color(78, 21, 21, 40);

    private Produto produto;

    public IlustracaoProduto(Produto produto, int largura, int altura) {
        this.produto = produto;
        setPreferredSize(new Dimension(largura, altura));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int largura = getWidth();
        int altura = getHeight();

        int arredondamento = Math.min(24, Math.min(largura, altura) / 3);
        g2.setPaint(new GradientPaint(0, 0, new Color(0xFDE7C4), 0, altura, new Color(0xF6BE7C)));
        g2.fill(new RoundRectangle2D.Double(0, 0, largura, altura, arredondamento, arredondamento));

        if (largura > altura * 1.6) {
            Color chamaSuave = new Color(217, 102, 43, 45);
            IconeChama.desenhar(g2, largura - altura * 0.55, altura * 0.18, altura * 0.42, altura * 0.62, chamaSuave, null);
            IconeChama.desenhar(g2, altura * 0.12, altura * 0.38, altura * 0.28, altura * 0.42, chamaSuave, null);
        }

        double tamanho = Math.min(largura, altura) * 0.8;
        g2.translate((largura - tamanho) / 2, (altura - tamanho) / 2);
        g2.scale(tamanho / 100, tamanho / 100);

        switch (produto.getId()) {
            case 1:
                desenharHamburguer(g2, false);
                break;
            case 2:
                desenharHamburguer(g2, true);
                break;
            case 3:
                desenharBatataFrita(g2);
                break;
            case 4:
                desenharOnionRings(g2);
                break;
            case 5:
                desenharRefrigerante(g2);
                break;
            case 6:
                desenharSuco(g2);
                break;
            case 7:
                desenharMilkShake(g2);
                break;
            case 8:
                desenharBrownie(g2);
                break;
            default:
                IconeChama.desenhar(g2, 25, 10, 50, 80, Estilo.LARANJA, Estilo.CREME);
        }
        g2.dispose();
    }

    private void desenharHamburguer(Graphics2D g, boolean comBacon) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(14, 86, 72, 10));

        g.setColor(PAO);
        g.fill(new RoundRectangle2D.Double(14, 70, 72, 18, 16, 16));

        g.setColor(Estilo.VINHO);
        g.fill(new RoundRectangle2D.Double(10, 58, 80, 15, 14, 14));

        Path2D queijo = new Path2D.Double();
        queijo.moveTo(12, 54);
        queijo.lineTo(88, 54);
        queijo.lineTo(88, 60);
        queijo.lineTo(70, 60);
        queijo.lineTo(64, 69);
        queijo.lineTo(58, 60);
        queijo.lineTo(32, 60);
        queijo.lineTo(25, 67);
        queijo.lineTo(19, 60);
        queijo.lineTo(12, 60);
        queijo.closePath();
        g.setColor(QUEIJO);
        g.fill(queijo);

        g.setColor(ALFACE);
        g.fill(new RoundRectangle2D.Double(9, 48, 82, 9, 9, 9));

        if (comBacon) {
            Path2D bacon = new Path2D.Double();
            bacon.moveTo(3, 57);
            bacon.curveTo(20, 50, 30, 64, 50, 57);
            bacon.curveTo(70, 50, 80, 64, 97, 57);
            g.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(BACON);
            g.draw(bacon);
            g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(BACON_CLARO);
            g.draw(bacon);
        }

        Path2D paoDeCima = new Path2D.Double();
        paoDeCima.moveTo(10, 51);
        paoDeCima.curveTo(10, 12, 90, 12, 90, 51);
        paoDeCima.closePath();
        g.setColor(PAO);
        g.fill(paoDeCima);

        g.setColor(PAO_CLARO);
        g.fill(new Ellipse2D.Double(24, 26, 22, 8));

        g.setColor(Estilo.CARD);
        double[][] gergelim = {{36, 36}, {50, 26}, {60, 36}, {46, 42}, {68, 28}, {28, 42}};
        for (double[] ponto : gergelim) {
            g.fill(new Ellipse2D.Double(ponto[0], ponto[1], 6, 3.5));
        }
    }

    private void desenharBatataFrita(Graphics2D g) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(20, 88, 60, 9));

        double[][] palitos = {{24, 28}, {32, 14}, {40, 22}, {48, 8}, {56, 18}, {64, 12}, {70, 26}};
        for (double[] palito : palitos) {
            RoundRectangle2D forma = new RoundRectangle2D.Double(palito[0], palito[1], 8, 60 - palito[1], 3, 3);
            g.setColor(BATATA);
            g.fill(forma);
            g.setColor(BATATA_CONTORNO);
            g.draw(forma);
        }

        Path2D caixa = new Path2D.Double();
        caixa.moveTo(18, 42);
        caixa.quadTo(50, 54, 82, 42);
        caixa.lineTo(72, 92);
        caixa.lineTo(28, 92);
        caixa.closePath();
        g.setColor(Estilo.VERMELHO);
        g.fill(caixa);

        IconeChama.desenhar(g, 42, 60, 16, 22, Estilo.CREME, null);
    }

    private void desenharOnionRings(Graphics2D g) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(10, 84, 80, 11));

        g.setColor(Estilo.VINHO);
        g.fill(new Ellipse2D.Double(64, 70, 28, 16));
        g.setColor(Estilo.VERMELHO);
        g.fill(new Ellipse2D.Double(67, 71, 22, 8));

        double[][] aneis = {{12, 52, 42, 30}, {40, 46, 40, 28}, {24, 22, 40, 28}};
        for (double[] anel : aneis) {
            Ellipse2D forma = new Ellipse2D.Double(anel[0], anel[1], anel[2], anel[3]);
            g.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(EMPANADO);
            g.draw(forma);
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(EMPANADO_CLARO);
            g.draw(forma);
        }
    }

    private void desenharSuco(Graphics2D g) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(28, 88, 44, 9));

        g.setColor(Estilo.VERMELHO);
        g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(46, 34, 36, 4);

        Path2D copo = new Path2D.Double();
        copo.moveTo(30, 20);
        copo.lineTo(70, 20);
        copo.lineTo(64, 92);
        copo.lineTo(36, 92);
        copo.closePath();
        g.setColor(new Color(255, 255, 255, 110));
        g.fill(copo);

        Path2D suco = new Path2D.Double();
        suco.moveTo(31.2, 34);
        suco.lineTo(68.8, 34);
        suco.lineTo(64, 92);
        suco.lineTo(36, 92);
        suco.closePath();
        g.setColor(SUCO);
        g.fill(suco);

        g.setColor(Estilo.CARD);
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(copo);

        g.setColor(SUCO);
        g.fill(new Ellipse2D.Double(56, 6, 26, 26));
        g.setColor(Estilo.CREME);
        g.fill(new Ellipse2D.Double(59, 9, 20, 20));
        g.setColor(SUCO);
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(69, 10, 69, 28);
        g.drawLine(60, 19, 78, 19);
    }

    private void desenharBrownie(Graphics2D g) {
        g.setColor(Estilo.CARD);
        g.fill(new Ellipse2D.Double(8, 74, 84, 18));

        g.setColor(CHOCOLATE);
        g.fill(new RoundRectangle2D.Double(20, 46, 60, 36, 10, 10));
        g.setColor(CHOCOLATE_CLARO);
        g.fill(new RoundRectangle2D.Double(20, 42, 60, 14, 10, 10));

        Path2D calda = new Path2D.Double();
        calda.moveTo(22, 44);
        calda.lineTo(78, 44);
        calda.lineTo(78, 52);
        calda.quadTo(74, 64, 70, 52);
        calda.lineTo(52, 52);
        calda.quadTo(47, 68, 42, 52);
        calda.lineTo(30, 52);
        calda.quadTo(26, 60, 22, 52);
        calda.closePath();
        g.setColor(Estilo.LARANJA);
        g.fill(calda);

        g.setColor(Estilo.CARD);
        g.fill(new Ellipse2D.Double(34, 18, 32, 30));
        g.setColor(new Color(0xF3DDBB));
        g.fill(new Ellipse2D.Double(40, 22, 10, 6));
    }

    private void desenharRefrigerante(Graphics2D g) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(26, 88, 48, 9));

        g.setColor(Estilo.LARANJA);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(58, 22, 66, 2);

        Path2D copo = new Path2D.Double();
        copo.moveTo(27, 30);
        copo.lineTo(73, 30);
        copo.lineTo(66, 92);
        copo.lineTo(34, 92);
        copo.closePath();
        g.setColor(Estilo.VERMELHO);
        g.fill(copo);

        Path2D faixa = new Path2D.Double();
        faixa.moveTo(29.3, 50);
        faixa.lineTo(70.7, 50);
        faixa.lineTo(68.5, 70);
        faixa.lineTo(31.5, 70);
        faixa.closePath();
        g.setColor(Estilo.CREME);
        g.fill(faixa);
        IconeChama.desenhar(g, 44, 51.5, 12, 17, Estilo.VERMELHO, null);

        g.setColor(Estilo.CARD);
        g.fill(new RoundRectangle2D.Double(32, 15, 36, 9, 8, 8));
        g.fill(new RoundRectangle2D.Double(23, 21, 54, 10, 8, 8));
    }

    private void desenharMilkShake(Graphics2D g) {
        g.setColor(SOMBRA);
        g.fill(new Ellipse2D.Double(28, 88, 44, 9));

        g.setColor(Estilo.VERMELHO);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(60, 26, 70, 4);

        Path2D copo = new Path2D.Double();
        copo.moveTo(28, 42);
        copo.lineTo(72, 42);
        copo.lineTo(64, 92);
        copo.lineTo(36, 92);
        copo.closePath();
        g.setColor(Estilo.VINHO_CLARO);
        g.fill(copo);

        g.setColor(new Color(255, 255, 255, 60));
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(36, 50, 40, 84);

        g.setColor(Estilo.LARANJA);
        g.fill(new RoundRectangle2D.Double(26, 38, 48, 7, 7, 7));

        g.setColor(Estilo.CARD);
        g.fill(new Ellipse2D.Double(24, 26, 26, 18));
        g.fill(new Ellipse2D.Double(37, 17, 26, 22));
        g.fill(new Ellipse2D.Double(50, 26, 26, 18));

        g.setColor(Estilo.VINHO);
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(51, 10, 56, 2);
        g.setColor(Estilo.VERMELHO);
        g.fill(new Ellipse2D.Double(44, 8, 13, 13));
    }
}
