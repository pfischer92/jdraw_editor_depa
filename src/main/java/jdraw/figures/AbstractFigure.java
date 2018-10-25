package jdraw.figures;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {
	
    private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public final void addFigureListener(FigureListener listener) {
        if (listener != null && !listeners.contains(listener)) {
        	listeners.add(listener);
        }
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

    abstract public Figure clone();
}
