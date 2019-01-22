package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

import java.awt.*;

public class OvalTool extends AbstractDrawTool {

    public OvalTool(DrawContext context){
        super(context, "Oval", "oval.png", Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public OvalTool(DrawContext context, String name, String icon){
        super(context, name, icon, Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    @Override
    protected Figure createFigure(int x, int y) {
        return new Oval(x,y,0, 0);
    }
}
