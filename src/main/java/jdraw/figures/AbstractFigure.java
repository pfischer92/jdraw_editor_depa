package jdraw.figures;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {
	
    private List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public final void addFigureListener(FigureListener listener) {
        if (listener != null && !listeners.contains(listener)) {
        	listeners.add(listener);
        }
    }

    @Override
    public List<FigureHandle> getHandles(){
        List<FigureHandle> handles = new LinkedList<>();
        handles.add(new NorthWestHandle(this));
        handles.add(new SouthEastHandle(this));
        return handles;
    }

    @Override
    public final void removeFigureListener(FigureListener listener) {
    	if (listener != null && listeners.contains(listener)) {
        	listeners.remove(listener);
        }
    }

    /**
     * Update all listeners about the figure change.
     */
    protected final void notifyListeners() {
        FigureEvent event = new FigureEvent(this);
        for (FigureListener listener : listeners) {
            listener.figureChanged(event);
        }
    }

    public AbstractFigure clone(){
        try {
            AbstractFigure copy = (AbstractFigure) super.clone();
            copy.listeners = new LinkedList<>();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(); }
    }
}
