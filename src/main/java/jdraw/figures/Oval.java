package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class Oval extends AbstractRectangularFigure {


    public Oval(int x, int y, int w, int h) {

        super(x,y,w,h);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        notifyListeners();
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle tmpRect = rectangle;
        Ellipse2D tmpOval = new Ellipse2D.Double(tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height);
        return tmpOval.contains(x, y);
    }


}
