package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;


public class DecoratorFigure extends AbstractFigure {

    private final Figure inner;

    public DecoratorFigure(Figure f) {
        super();
        inner = f;
    }

    protected Figure getInnerFigure(){
        return inner;
    }

    @Override
    public void draw(Graphics g) {
        inner.draw(g);
    }

    @Override
    public void move(int dx, int dy) {
        inner.move(dx, dy);
    }

    @Override
    public boolean contains(int x, int y) {
        return inner.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        inner.setBounds(origin, corner);
    }

    @Override
    public Rectangle getBounds() {
        return inner.getBounds();
    }

    @Override
    public <T> T getInstanceOf(Class<T> type) {
        return type.isAssignableFrom(this.getClass()) ? type.cast(this) : inner.getInstanceOf(type);
    }

    @Override
    public boolean isInstanceOf(Class<?> type) {
        return type.isAssignableFrom(this.getClass());
    }
}



