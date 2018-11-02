package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

public class Line extends AbstractFigure {

    private List<FigureHandle> handles;

    private static final int LINE_DISTANCE = 5;

    private Line2D.Double line;

    public Line(int x1, int y1, int x2, int y2) {
        this.line = new Line2D.Double(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(line);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        line.x1 += dx;
        line.y1 += dy;
        line.x2 += dx;
        line.y2 += dy;

        notifyListeners();
    }

    @Override
    public boolean contains(int x, int y) {
        return line.ptSegDistSq(x, y) < LINE_DISTANCE;
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        line.x1 = origin.x;
        line.y1 = origin.y;
        line.x2 = corner.x;
        line.y2 = corner.y;

        notifyListeners();
    }

    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }

    @Override
    public AbstractFigure clone() {
        Line copy = (Line) super.clone();
        copy.line = (Line2D.Double) copy.line.clone();
        copy.handles = null;
        return copy;
    }

    public java.util.List<FigureHandle> getHandles() {
        handles = new LinkedList<>();
        if(line.x1 < line.x2 && line.y1 < line.y2){
            handles.add(new NorthWestHandle(this));
            handles.add(new SouthEastHandle(this));
        }
        return handles;
    }
}
