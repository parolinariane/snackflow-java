package snackflow.view;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;


public class PainelRolavel extends JPanel implements Scrollable {

    public PainelRolavel(LayoutManager layout) {
        super(layout);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle areaVisivel, int orientacao, int direcao) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle areaVisivel, int orientacao, int direcao) {
        return areaVisivel.height;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
