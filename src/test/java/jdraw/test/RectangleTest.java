package jdraw.test;

import jdraw.figures.Rect;
import jdraw.framework.Figure;

public class RectangleTest extends FigureTest {
    @Override
    public Figure createFigure() {
        return new Rect(5, 5, 10, 10);
    }
}
