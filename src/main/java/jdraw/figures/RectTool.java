/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 */
public class RectTool extends AbstractDrawTool {
	public RectTool(DrawContext context) {
		super(context, "Rectangle", "rectangle.png", Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

    public RectTool(DrawContext context, String name, String icon) {
        super(context, name, icon, Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

	protected Figure createFigure(int x, int y) {
		return new Rect(x, y, 0, 0);
	}


}
