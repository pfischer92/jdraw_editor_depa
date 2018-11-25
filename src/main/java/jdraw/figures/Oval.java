package jdraw.figures;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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
        propagateFigureEvent();
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle tmpRect = rectangle;
        Ellipse2D tmpOval = new Ellipse2D.Double(tmpRect.x, tmpRect.y, tmpRect.width, tmpRect.height);
        return tmpOval.contains(x, y);
    }


}
