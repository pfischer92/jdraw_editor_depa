package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.std.AddFigureCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractDrawTool implements DrawTool {
    /**
     * the image resource path.
     */
    private static final String IMAGES = "/images/";

    /**
     * The context we use for drawing.
     */
    private final DrawContext context;

    /**
     * The context's view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    private final DrawView view;

    /**
     * Name of the figure.
     */
    private final String name;

    /**
     * Icon file name of the figure located in the images resource path.
     */
    private final String iconname;

    /**
     * Cursor used for the figure.
     */
    private final Cursor cursor;

    /**
     * Figure that is used for the draw tool.
     */
    private Figure figure;

    /**
     * Point to save the first mouse click
     */
    private Point anchor;


    public AbstractDrawTool(DrawContext context, String name, String iconmame, Cursor cursor){
        this.context = context;
        this.view = context.getView();
        this.name = name;
        this.iconname = iconmame;
        this.cursor = cursor;
    }
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }

    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (figure != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        figure = createFigure(x, y);
        view.getModel().addFigure(figure);
    }

    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        figure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = figure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        if (figure.getBounds().width == 0 && figure.getBounds().height == 0) {
            view.getModel().removeFigure(figure);
        }

        // Undo/redo
        this.context.getModel().getDrawCommandHandler().addCommand(new AddFigureCommand(context.getModel(), figure));

        anchor = null;
        figure = null;
        this.context.showStatusText(getName() + " Mode");
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + iconname));
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Create a draw tool specific figure.
     *
     * @param x X coordinate of the initial mouse click
     * @param y Y coordinate of the initial mouse click
     * @return Draw tool specific figure
     */
    protected abstract Figure createFigure(int x, int y);
}
