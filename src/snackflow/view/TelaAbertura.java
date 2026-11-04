package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 * Tela de abertura (splash screen) exibida enquanto o SnackFlow "carrega".
 * É uma janela sem barra de título, com uma barra de progresso animada.
 * Ao terminar, ela se fecha e executa a ação recebida no construtor (abrir o cardápio).
 */
public class TelaAbertura extends JWindow {

    private static final int LARGURA = 520;
    private static final int ALTURA = 340;
    private static final int INTERVALO_MS = 25;   // tempo entre cada passo da animação
    private static final String[] MENSAGENS = {
        "Acendendo a brasa...",
        "Separando os ingredientes...",
        "Preparando o cardápio...",
        "Tudo pronto!"
    };

    private Runnable acaoAoTerminar;
    private BarraProgresso barra;
    private JLabel lblMensagem;
    private Timer timer;
    private int progresso = 0;

    public TelaAbertura(Runnable acaoAoTerminar) {
        this.acaoAoTerminar = acaoAoTerminar;

        setSize(LARGURA, ALTURA);
        setLocationRelativeTo(null);
        arredondarCantos();

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Estilo.VINHO);
        raiz.add(criarConteudo(), BorderLayout.CENTER);
        raiz.add(new FaixaChamas(), BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(BorderFactory.createEmptyBorder(36, 40, 24, 40));

        IconeChama chama = new IconeChama(64, 80, Estilo.LARANJA, Estilo.CREME);
        chama.setMaximumSize(chama.getPreferredSize());

        JLabel titulo = Estilo.criarRotulo("SnackFlow", Estilo.FONTE_LOGO.deriveFont(44f), Estilo.CREME);
        JLabel slogan = Estilo.criarRotulo("LANCHES  •  PEDIDOS RÁPIDOS", Estilo.FONTE_PEQUENA, Estilo.LARANJA);

        barra = new BarraProgresso();
        lblMensagem = Estilo.criarRotulo(MENSAGENS[0], Estilo.FONTE_PEQUENA, Estilo.TEXTO_CLARO);

        JComponent[] componentes = {chama, titulo, slogan, barra, lblMensagem};
        for (JComponent componente : componentes) {
            componente.setAlignmentX(CENTER_ALIGNMENT);
        }

        conteudo.add(chama);
        conteudo.add(Box.createVerticalStrut(8));
        conteudo.add(titulo);
        conteudo.add(slogan);
        conteudo.add(Box.createVerticalStrut(30));
        conteudo.add(barra);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(lblMensagem);
        return conteudo;
    }

    /** Deixa a janela com cantos arredondados, se o sistema operacional permitir. */
    private void arredondarCantos() {
        GraphicsDevice tela = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (tela.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
            setShape(new RoundRectangle2D.Double(0, 0, LARGURA, ALTURA, 32, 32));
        }
    }

    /** Mostra a tela e começa a animação da barra de progresso. */
    public void iniciar() {
        setVisible(true);
        timer = new Timer(INTERVALO_MS, e -> avancar());
        timer.start();
    }

    private void avancar() {
        progresso++;
        barra.setProgresso(progresso);

        // Troca a mensagem conforme o progresso avança (0-24%, 25-49%, 50-74%, 75-100%)
        int indiceMensagem = Math.min(progresso / 25, MENSAGENS.length - 1);
        lblMensagem.setText(MENSAGENS[indiceMensagem]);

        if (progresso >= 100) {
            timer.stop();
            dispose();
            acaoAoTerminar.run();
        }
    }

    /** Barra de progresso arredondada desenhada nas cores da marca. */
    private static class BarraProgresso extends JComponent {

        private int progresso = 0;

        BarraProgresso() {
            Dimension tamanho = new Dimension(320, 10);
            setPreferredSize(tamanho);
            setMaximumSize(tamanho);
        }

        void setProgresso(int progresso) {
            this.progresso = progresso;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int altura = getHeight();

            g2.setColor(new Color(255, 255, 255, 40));
            g2.fillRoundRect(0, 0, getWidth(), altura, altura, altura);

            int larguraPreenchida = getWidth() * progresso / 100;
            if (larguraPreenchida > 0) {
                g2.setColor(Estilo.LARANJA);
                g2.fillRoundRect(0, 0, Math.max(larguraPreenchida, altura), altura, altura, altura);
            }
            g2.dispose();
        }
    }
}
