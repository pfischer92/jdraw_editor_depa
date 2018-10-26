package jdraw.grid;

import jdraw.framework.DrawGrid;

import java.awt.*;

public class Grid20 implements DrawGrid {
    private int stepX = 20;
    private int stepY = 20;

    @Override
    public Point constrainPoint(Point p) {
        int x = ((p.x + stepX/2)/stepX) * stepX;
        int y = ((p.y + stepY/2)/stepY) * stepY;
        return new Point(x, y);
    }

    @Override
    public int getStepX(boolean right) {
        return stepX;
    }

    @Override
    public int getStepY(boolean down) {
        return stepY;
    }

    @Override
    public void activate() {
        System.out.println("Grid10 activated");
    }

    @Override
    public void deactivate() {
        System.out.println("Grid10 deactivated");
    }

    @Override
    public void mouseDown() {
        System.out.println("Grid10 mousedown");
    }

    @Override
    public void mouseUp() {
        System.out.println("Grid10 mouseup");
    }
}
