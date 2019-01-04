package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

import java.util.Stack;

public class MyDrawCommandHandler implements DrawCommandHandler {

    private Stack<DrawCommand> undoStack = new Stack<>();
    private Stack<DrawCommand> redoStack = new Stack<>();
    private Script script = null;

    @Override
    public void addCommand(DrawCommand cmd) {
        redoStack.clear();
        if(script == null) {
            undoStack.push(cmd);
        } else {
            script.commands.add(cmd);
        }
    }

    @Override
    public void undo() {
        if(undoPossible()) {
            DrawCommand d = undoStack.pop();
            redoStack.push(d);
            d.undo();
        }
    }

    @Override
    public void redo() {
        if (redoPossible()) {
            DrawCommand d = redoStack.pop();
            undoStack.push(d);
            d.redo();
        }
    }

    @Override
    public boolean undoPossible() {
        return undoStack.size() > 0;
    }

    @Override
    public boolean redoPossible() {
        return redoStack.size() > 0;
    }

    @Override
    public void beginScript() {
        if(script != null) throw new IllegalStateException();
        script = new Script();
    }

    @Override
    public void endScript() {
        if (script == null) throw new IllegalStateException();
        Script tmp = script;
        script = null;
        if (tmp.commands.size() > 0) {
            addCommand(tmp);
        }
    }

    @Override
    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }
}
