package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GroupFigure extends AbstractFigure implements Figure {
    private List<Figure> figuresOfGroup;
    private Rectangle bounds;

    public GroupFigure(List<Figure> selection) {
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
    public GroupFigure clone() {
        GroupFigure copy = (GroupFigure) super.clone();
        copy.figuresOfGroup = new LinkedList<>();
        for (Figure f : figuresOfGroup) {
            copy.figuresOfGroup.add(f.clone());
        }
        return copy;
    }

    public Iterable<Figure> getGroupParts() {
        return figuresOfGroup;
    }
}
