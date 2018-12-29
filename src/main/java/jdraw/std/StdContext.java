/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.figures.*;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.grid.Grid10;
import jdraw.grid.Grid20;
import jdraw.grid.NoGrid;

/**
 * Standard implementation of interface DrawContext.
 * 
 * @see DrawView
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {
    private final List<Figure> clipboardFigures = new ArrayList<>();
	/**
	 * Constructs a standard context with a default set of drawing tools.
	 * @param view the view that is displaying the actual drawing.
	 */
	public StdContext(DrawView view) {
		super(view, null);
	}

	/**
	 * Constructs a standard context. The drawing tools available can be
	 * parameterized using <code>toolFactories</code>.
	 * 
	 * @param view  the view that is displaying the actual drawing.
	 * @param toolFactories  a list of DrawToolFactories that are available to the user
	 */
	public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super(view, toolFactories);
	}

	/**
	 * Creates and initializes the "Edit" menu.
	 * 
	 * @return the new "Edit" menu.
	 */
	@Override
	protected JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		final JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		editMenu.add(undo);
		undo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.undoPossible()) {
					h.undo();
				}
			}
		);

		final JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		editMenu.add(redo);
		redo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.redoPossible()) {
					h.redo();
				}
			}
		);
		editMenu.addSeparator();

		JMenuItem sa = new JMenuItem("SelectAll");
		sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
		editMenu.add(sa);
		sa.addActionListener( e -> {
				for (Figure f : getModel().getFigures()) {
					getView().addToSelection(f);
				}
				getView().repaint();
			}
		);

		editMenu.addSeparator();

		JMenuItem cut = new JMenuItem("Cut");
		editMenu.add(cut);
		cut.addActionListener(e -> {
		    clipboardFigures.clear();
            for(Figure f : getView().getSelection()){
                getModel().removeFigure(f);
                clipboardFigures.add(f);
            }
        });

        JMenuItem copy = new JMenuItem("Copy");
        editMenu.add(copy);
        cut.addActionListener(e -> {
            clipboardFigures.clear();
            for(Figure f : getView().getSelection()){
                clipboardFigures.add(f);
            }
        });


        JMenuItem paste = new JMenuItem("Paste");
        editMenu.add(paste);
        paste.addActionListener(e -> {
            if(clipboardFigures.isEmpty()){
            } else {
                Figure tmp = null;
                for(Figure f : clipboardFigures){
                    tmp = f.clone();
                    tmp.move(10,10);
                    getModel().addFigure(tmp);
                }
                clipboardFigures.clear();
            }
        });

		editMenu.addSeparator();
		JMenuItem clear = new JMenuItem("Clear");
		editMenu.add(clear);
		clear.addActionListener(e -> {
			getModel().removeAllFigures();
		});
		
		editMenu.addSeparator();

		JMenuItem group = new JMenuItem("Group");
        editMenu.add(group);
        group.addActionListener(e -> {
            List<Figure> selection = getView().getSelection();
            if(selection != null && selection.size() >= 2){
                GroupFigure g = new GroupFigure(new ArrayList<>(selection));
                DrawModel m = getView().getModel();
                for (Figure f : selection){
                    m.removeFigure(f);
                }
                m.addFigure(g);
                getView().addToSelection(g);
            }
        });

        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.setEnabled(true);
        ungroup.addActionListener(actionEvent -> {
            List<Figure> list = getView().getSelection();
            Iterable<Figure> list2;
            for (Figure f : list) {
                if (f.getInstanceOf(GroupFigure.class) != null) {

                    GroupFigure g = (GroupFigure) f.getInstanceOf(GroupFigure.class);
                    DrawModel model = getView().getModel();
                    list2 = g.getGroupParts();
                    for (Figure f1 : list2) {
                        model.addFigure(f1);
                    }
                    model.removeFigure(f);
                }
            }
            getView().repaint();
        });
        editMenu.add(ungroup);


		editMenu.addSeparator();

		JMenu orderMenu = new JMenu("Order...");
		JMenuItem frontItem = new JMenuItem("Bring To Front");
		frontItem.addActionListener(e -> {
			bringToFront(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(frontItem);
		JMenuItem backItem = new JMenuItem("Send To Back");
		backItem.addActionListener(e -> {
			sendToBack(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(backItem);
		editMenu.add(orderMenu);

		JMenu grid = new JMenu("Grid...");
		JMenuItem grid10 = new JMenuItem("Grid10");
        grid10.addActionListener(e -> getView().setGrid(new Grid10()));
		grid.add(grid10);

        JMenuItem grid20 = new JMenuItem("Grid20");
        grid20.addActionListener(e -> getView().setGrid(new Grid20()));
        grid.add(grid20);

        JMenuItem noGrid = new JMenuItem("No Grid");
        noGrid.addActionListener(e -> getView().setGrid(new NoGrid()));
        grid.add(noGrid);

		editMenu.add(grid);

        JMenu decorators = new JMenu("Decorators...");

        JMenuItem borderDecorator = new JMenuItem("Add border decoration");
        borderDecorator.addActionListener(e -> {
            List<Figure> selectedFigures = getView().getSelection();
            getView().clearSelection();
            for(Figure selectedFigure : selectedFigures){
                BorderDecorator decoratedFigure = new BorderDecorator(selectedFigure);
                getModel().removeFigure(selectedFigure);
                getModel().addFigure(decoratedFigure);
                getView().addToSelection(decoratedFigure);
            }
        });
        decorators.add(borderDecorator);
        editMenu.add(decorators);
		
		return editMenu;
	}

	/**
	 * Creates and initializes items in the file menu.
	 * 
	 * @return the new "File" menu.
	 */
	@Override
	protected JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(e -> doOpen());

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		fileMenu.add(save);
		save.addActionListener(e ->	doSave());

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> System.exit(0));
		
		return fileMenu;
	}

	@Override
	protected void doRegisterDrawTools() {
		// TODO Add new figure tools here
		DrawTool rectangleTool = new RectTool(this);
		addTool(rectangleTool);

		DrawTool lineTool = new LineTool(this);
		addTool(lineTool);

		OvalTool ovalTool = new OvalTool(this);
		addTool(ovalTool);
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the front, i.e. moves them to the end of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to front
	 */
	public void bringToFront(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		int pos = 0;
		for (Figure f : model.getFigures()) {
			pos++;
			if (selection.contains(f)) {
				orderedSelection.add(0, f);
			}
		}
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, --pos);
		}
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the back, i.e. moves them to the front of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to the back
	 */
	public void sendToBack(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		for (Figure f : model.getFigures()) {
			if (selection.contains(f)) {
				orderedSelection.add(f);
			}
		}
		int pos = 0;
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, pos++);
		}
	}

	/**
	 * Handles the saving of a drawing to a file.
	 */
	private void doSave() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
		chooser.setDialogTitle("Save Graphic");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));
		
		int res = chooser.showSaveDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// save graphic
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			if(filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
				file = new File(chooser.getCurrentDirectory(), file.getName() + "." + ((FileNameExtensionFilter)filter).getExtensions()[0]);
			}
            try{
                if(chooser.getFileFilter() == filter && !filter.accept(file)){
                    file = new File(chooser.getCurrentDirectory(), file.getName() + ".draw");
                }
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
                    for (Figure f : getModel().getFigures()){
                        oos.writeObject(f.clone());
                    }
                    oos.writeObject(null);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
		}
	}

	/**
	 * Handles the opening of a new drawing from a file.
	 */
	private void doOpen() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("")
				.getFile());
		chooser.setDialogTitle("Open Graphic");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".draw");
			}
		});

		int res = chooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// read jdraw graphic
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {
                DrawModel m = getModel();
                m.removeAllFigures();
                while (true) {
                    try {
                        Object x = ois.readObject();
                        if (x == null) {
                            break;
                        }
                        m.addFigure((Figure) x);
                    } catch (ClassNotFoundException e) {
                        System.out.println("Figure not found" + e.getMessage());
                    }
                }
            } catch (Exception e){
                System.out.println(e);
            }
		}
	}
}
