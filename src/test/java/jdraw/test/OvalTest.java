package jdraw.test;

import jdraw.figures.Oval;
import jdraw.framework.Figure;

public class OvalTest extends FigureTest {
    @Override
    public Figure createFigure() {
        return new Oval(5,5,10,10);
    }
}
