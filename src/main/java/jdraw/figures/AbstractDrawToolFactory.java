package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

public abstract class AbstractDrawToolFactory implements DrawToolFactory {

    private String name;
    private String icon;
    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public String getIconName() { return icon; }

    @Override
    public void setIconName(String name) {icon = name; }

    public abstract DrawTool createTool(DrawContext context);
}
