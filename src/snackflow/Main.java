package snackflow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import snackflow.model.Cardapio;
import snackflow.model.Pedido;
import snackflow.view.CardapioView;
import snackflow.view.TelaAbertura;


public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível aplicar o tema do sistema. Usando o padrão.");
        }

        SwingUtilities.invokeLater(() -> {
            Cardapio cardapio = new Cardapio();
            Pedido pedido = new Pedido();

            TelaAbertura abertura = new TelaAbertura(() -> {
                CardapioView telaCardapio = new CardapioView(cardapio, pedido);
                telaCardapio.setVisible(true);
            });
            abertura.iniciar();
        });
    }
}
