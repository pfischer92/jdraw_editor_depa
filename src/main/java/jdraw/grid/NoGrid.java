package jdraw.grid;

import jdraw.framework.DrawGrid;

import java.awt.*;

public class NoGrid implements DrawGrid {
    @Override
    public Point constrainPoint(Point p) {
        return p;
    }

    @Override
    public int getStepX(boolean right) {
        return 1;
    }

    @Override
    public int getStepY(boolean down) {
        return 1;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}
