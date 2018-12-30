package jdraw.test;

import jdraw.figures.BorderDecorator;
import jdraw.figures.Rect;
import jdraw.framework.Figure;

public class BorderDecoratorTest extends FigureTest {
    @Override
    public Figure createFigure() {
        return new BorderDecorator( new Rect(5, 5, 10, 10));
    }
}
