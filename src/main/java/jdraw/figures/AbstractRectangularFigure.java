package jdraw.figures;

import jdraw.framework.FigureHandle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public abstract class AbstractRectangularFigure extends AbstractFigure {
    protected Rectangle rectangle;

    public AbstractRectangularFigure(int x, int y, int w, int h){
        rectangle = new Rectangle(x, y, w, h);
    }


    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) { // notification only if changed
            rectangle.translate(dx, dy);
            propagateFigureEvent();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle); rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rectangle)) { // notification only if
            propagateFigureEvent(); // there is a change
        }
    }

    @Override
    public Rectangle getBounds() {
            return new Rectangle(rectangle);
    }

    @Override
    public List<FigureHandle> getHandles() {
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthCenterHandle(this));
        handles.add(new NorthEastHandle(this));
        handles.add(new EastCenterHandle(this));
        handles.add(new SouthEastHandle(this));
        handles.add(new SouthCenterHandle(this));
        handles.add(new SouthWestHandle(this));
        handles.add(new WestCenterHandle(this));
        handles.add(new NorthWestHandle(this));
        return handles;
    }

    @Override
    public AbstractRectangularFigure clone() {
        AbstractRectangularFigure clone = (AbstractRectangularFigure) super.clone(); // copy the basis
        clone.rectangle = (Rectangle) this.rectangle.clone(); // copy this class's attributes
        return clone;
    }

    @Override
    public final <T> T getInstanceOf(Class<T> type) {
        return type.cast(this);
    }

    @Override
    public boolean isInstanceOf(Class<?> type) {
        return type.isAssignableFrom(this.getClass());
    }
}
