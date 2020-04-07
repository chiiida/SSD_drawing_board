package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		CompositeGObject groupObject = new CompositeGObject();

		for (GObject child : gObjects) {
			if (child instanceof CompositeGObject) {
				groupObject = (CompositeGObject) child;
			} else {
				groupObject.add(child);
			}
		}

		groupObject.recalculateRegion();
		gObjects.clear();
		gObjects.add(groupObject);
		repaint();
	}

	public void deleteSelected() {
		gObjects.remove(target);
		repaint();
	}
	
	public void clear() {
		gObjects.clear();
		repaint();;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject child : gObjects) {
			child.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		private int x;
		private int y;
		
		private void deselectAll() {
			for (GObject child : gObjects) {
				child.deselected();
			}

			target = null;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			boolean mouseHit = false;

			deselectAll();
			for (GObject child : gObjects) {
				if (child.pointerHit(x, y)) {
					target = child;
					mouseHit = true;
					break;
				}
			}
			if (mouseHit) {target.selected();}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (target != null) {
				// find distance for move
				int dX = e.getX() - x;
				int dY = e.getY() - y;

				target.move(dX, dY);
				repaint();

				x = e.getX();
				y = e.getY();
			}
		}
	}
	
}