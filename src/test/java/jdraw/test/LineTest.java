package jdraw.test;

import jdraw.figures.Line;
import jdraw.framework.Figure;

public class LineTest extends FigureTest {
    @Override
    public Figure createFigure() {
        return new Line(0,0,2,2);
    }
}
