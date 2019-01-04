package jdraw.std;

import jdraw.framework.DrawCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Script implements DrawCommand {

    public List<DrawCommand> commands = new LinkedList<>();

    @Override
    public void redo() {
        ListIterator<DrawCommand> it = commands.listIterator();
        while (it.hasNext()) { it.next().redo(); }
    }

    @Override
    public void undo() {
        int size = commands.size();
        ListIterator<DrawCommand> it = commands.listIterator(size); while (it.hasPrevious()) { it.previous().undo(); }
    }
}
