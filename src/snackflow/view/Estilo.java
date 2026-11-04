package snackflow.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Estilo {

    // Paleta principal
    public static final Color LARANJA = new Color(0xD9662B);
    public static final Color LARANJA_ESCURO = new Color(0xB9511C);
    public static final Color VERMELHO = new Color(0xB3241E);
    public static final Color VERMELHO_ESCURO = new Color(0x8F1A15);
    public static final Color VINHO = new Color(0x4E1515);
    public static final Color VINHO_CLARO = new Color(0x6B2020);
    public static final Color CREME = new Color(0xF7C98A);
    public static final Color PRETO = new Color(0x1A0A0A);

    public static final Color FUNDO = new Color(0xFBE6C4);
    public static final Color CARD = new Color(0xFFF6E8);
    public static final Color BORDA = new Color(0xEBC08A);
    public static final Color TEXTO = new Color(0x3A1010);
    public static final Color TEXTO_SECUNDARIO = new Color(0x7A4A38);
    public static final Color TEXTO_CLARO = new Color(0xFFF6E8);

    private static final String FAMILIA_TEXTO = "Segoe UI";
    private static final String FAMILIA_TITULO = escolherFonte("Cooper Black", "Georgia", Font.SERIF);
    private static final int ESTILO_TITULO = FAMILIA_TITULO.equals("Cooper Black") ? Font.PLAIN : Font.BOLD;

    public static final Font FONTE_LOGO = new Font(FAMILIA_TITULO, ESTILO_TITULO, 30);
    public static final Font FONTE_TITULO = new Font(FAMILIA_TITULO, ESTILO_TITULO, 30);
    public static final Font FONTE_SUBTITULO = new Font(FAMILIA_TITULO, ESTILO_TITULO, 20);
    public static final Font FONTE_PRECO = new Font(FAMILIA_TEXTO, Font.BOLD, 20);
    public static final Font FONTE_NORMAL = new Font(FAMILIA_TEXTO, Font.PLAIN, 14);
    public static final Font FONTE_NEGRITO = new Font(FAMILIA_TEXTO, Font.BOLD, 14);
    public static final Font FONTE_PEQUENA = new Font(FAMILIA_TEXTO, Font.PLAIN, 12);

    private static final NumberFormat FORMATO_REAL =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    private Estilo() {
    }

    public static String formatarPreco(double valor) {
        return FORMATO_REAL.format(valor);
    }

    public static JLabel criarRotulo(String texto, Font fonte, Color cor) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(fonte);
        rotulo.setForeground(cor);
        return rotulo;
    }

    public static JPanel criarCabecalho(JComponent complementoDireita) {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(VINHO);
        barra.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(criarRotulo("SnackFlow", FONTE_LOGO, CREME));
        textos.add(criarRotulo("LANCHES  •  PEDIDOS RÁPIDOS", FONTE_PEQUENA, LARANJA));

        JPanel marca = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        marca.setOpaque(false);
        marca.add(new IconeChama(40, 50, LARANJA, CREME));
        marca.add(textos);

        barra.add(marca, BorderLayout.WEST);
        if (complementoDireita != null) {
            JPanel alinhamento = new JPanel(new GridBagLayout());
            alinhamento.setOpaque(false);
            alinhamento.add(complementoDireita);
            barra.add(alinhamento, BorderLayout.EAST);
        }

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.add(barra, BorderLayout.CENTER);
        cabecalho.add(new FaixaChamas(), BorderLayout.SOUTH);
        return cabecalho;
    }

    private static String escolherFonte(String... familias) {
        List<String> instaladas = Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        for (String familia : familias) {
            if (instaladas.contains(familia)) {
                return familia;
            }
        }
        return Font.SERIF;
    }
}
