package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractFigureHandle implements FigureHandle {
    private static final int HANDLE_SIZE = 6;
    protected Figure owner;
    protected Point corner;

    public AbstractFigureHandle(Figure owner) {
        this.owner = owner;
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        g.setColor(Color.WHITE);
        g.fillRect(loc.x - 3, loc.y - 3, 6, 6);
        g.setColor(Color.BLACK);
        g.drawRect(loc.x - 3, loc.y - 3, 6, 6);
    }

    @Override
    public boolean contains(int x, int y) {
        Point loc = getLocation();

        return Math.abs(x-loc.x) < HANDLE_SIZE / 2 &&
                Math.abs(y -loc.y) < HANDLE_SIZE / 2;
    }


    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        corner = null;
    }
}
