/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Patrick Fischer
 *
 */
public class StdDrawModel implements DrawModel, FigureListener {

	private final ArrayList<Figure> figures;
	private final CopyOnWriteArrayList<DrawModelListener> modelListener;
	
	/**
     * Create a new draw model that holds all figures and provides an observer mechanism to keep in touch will all changes.
     */
    public StdDrawModel() {
        this.figures = new ArrayList<>();
        this.modelListener = new CopyOnWriteArrayList<DrawModelListener>();
    }
	
	
	@Override
	public void addFigure(Figure f) {
		if(!figures.contains(f)) {
			figures.add(f);
			
			f.addFigureListener(this);
			updateModel(f, DrawModelEvent.Type.FIGURE_ADDED);
		}
	}

	@Override
	public Iterable<Figure> getFigures() {
		return figures;
	}

	@Override
	public void removeFigure(Figure f) {
		if(figures.contains(f)) {
			figures.remove(f);
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		if(listener != null && !modelListener.contains(listener)) {
			modelListener.add(listener);
		}
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		if(listener != null && modelListener.contains(listener)) {
			modelListener.remove(listener);
		}
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new MyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if (!figures.contains(f)) {
            throw new IllegalArgumentException();
        }

        if (index < 0 || index >= figures.size()) {
            throw new IndexOutOfBoundsException();
        }

        figures.remove(f);
        figures.add(index, f);

        updateModel(f, DrawModelEvent.Type.DRAWING_CHANGED);
	}

	@Override
	public void removeAllFigures() {
		for (Figure f : figures) {
            f.removeFigureListener(this);
        }

        figures.clear();

        updateModel(null, DrawModelEvent.Type.DRAWING_CLEARED);
	}

	@Override
	public void figureChanged(FigureEvent e) {
		Figure f = e.getFigure();

        updateModel(f, DrawModelEvent.Type.FIGURE_CHANGED);
		
	}
	
	 private void updateModel(Figure f, DrawModelEvent.Type t) {
	        DrawModelEvent event = new DrawModelEvent(this, f, t);
	        for (DrawModelListener modellistener : modelListener) {
	            modellistener.modelChanged(event);
	        }
	    }
}
