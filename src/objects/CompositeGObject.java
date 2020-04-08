package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		x += dX;
		y += dY;

		for (GObject child : gObjects) {
			child.move(dX, dY);
		}
	}

	public void recalculateRegion() {
		GObject firstChild = gObjects.get(0);
		int maxX = 0;
		int maxY = 0;
		int minX = firstChild.x;
		int minY = firstChild.y;

		for (GObject child : gObjects) {
			if (child.x + child.width > maxX) maxX = child.x + child.width;
			if (child.x < minX) minX = child.x;
			if (child.y + child.height > maxY) maxY = child.y + child.height;
			if (child.y < minY) minY = child.y;
		}

		this.x = minX;
		this.y = minY;
		this.width = maxX - minX;
		this.height = maxY - minY;
	}

	@Override
	public void paintObject(Graphics g) {
		for (GObject child : gObjects) {
			child.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.setColor(Color.black);
		g.drawString("Group", x, y + height + 15);
	}
	
}
