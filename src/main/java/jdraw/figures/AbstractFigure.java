package jdraw.figures;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {
	
    private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();
    private final List<FigureHandle> handles = new CopyOnWriteArrayList<>();

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

    @Override
    public final List<FigureHandle> getHandles() {
        return (!handles.isEmpty()) ? new CopyOnWriteArrayList<>(handles) : null;
    }

    /**
     * Add a new figure handle.
     *
     * @param handle New figure handle
     */
    protected final void addFigureHandle(FigureHandle handle) {
    	if (handles != null && !handles.contains(handle)) {
        	handles.add(handle);
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
