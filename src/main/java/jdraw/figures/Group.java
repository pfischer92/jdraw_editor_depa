package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Group implements Figure {
    private List<Figure> figuresOfGroup;
    private Rectangle bounds;

    public Group(List<Figure> selection) {
        this.figuresOfGroup = selection;
        getBounds();
    }

    @Override
    public void draw(Graphics g) {
        figuresOfGroup.forEach(figure -> figure.draw(g));
    }

    @Override
    public void move(int dx, int dy) {
        figuresOfGroup.forEach(figure -> figure.move(dx, dy));
    }

    @Override
    public boolean contains(int x, int y) {
        return bounds != null && bounds.contains(x,y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        if(!figuresOfGroup.isEmpty()){
            Iterator<Figure> partsIt = figuresOfGroup.iterator();
            Rectangle groupBound = partsIt.next().getBounds();
            while(partsIt.hasNext()){
                groupBound.add(partsIt.next().getBounds());
            }
            bounds = groupBound;
            return groupBound;
        }
        return null;
    }

    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public void addFigureListener(FigureListener listener) {

    }

    @Override
    public void removeFigureListener(FigureListener listener) {

    }

    @Override
    public Figure clone() {
        return null;
    }

    public Iterable<Figure> getGroupParts() {
        return figuresOfGroup;
    }
}
