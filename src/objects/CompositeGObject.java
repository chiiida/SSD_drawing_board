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
		// TODO: Implement this method.
		var minX = gObjects.get(0).x;
		for (GObject gObject : gObjects) {
			if (gObject.x < minX) {
				minX = gObject.x;
			}
		}
		var maxX = gObjects.get(0).x + gObjects.get(0).width;
		for (GObject gObject : gObjects) {
			if ( (gObject.x+gObject.height) > maxX) {
				maxX = gObject.x+gObject.width;
			}
		}
		var minY = gObjects.get(0).y;
		for (GObject gObject : gObjects) {
			if (gObject.y < minY) {
				minY = gObject.y;
			}
		}
		var maxY = gObjects.get(0).y + gObjects.get(0).height;
		for (GObject gObject : gObjects) {
			if ( (gObject.y+gObject.height) > maxY) {
				maxY = gObject.y+gObject.height;
			}
		}
		this.x = minX;
		this.y = minY;
		this.width = maxX-minX;
		this.height = maxY- minY;
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
