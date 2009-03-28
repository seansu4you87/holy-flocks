package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import old.PointD;

import simulationFramework.ISimulationModel;
import simulationFramework.ISimulationComponent;
import simulationFramework.Applet;

/**
 * models individual flock members evolved from BouncingBall in screensavers
 * methods: add to/remove from flock update position paint itself
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
public class FlockMember implements ISimulationComponent {
	private PointD myCenter;
	private Color myColor;
	private int myMaxTrail;
	private String myName;
	protected Dimension mySize;
	private List<PointD> myTrail;
	private PointD myVelocity;

	public FlockMember(PointD center, PointD velocity, Dimension size,
			Color color, String name, int trailSize) {
		myCenter = new PointD(center);
		myVelocity = new PointD(velocity);
		mySize = new Dimension(size);
		myColor = color;
		myName = name;

		myMaxTrail = trailSize;
		myTrail = new LinkedList<PointD>();
		myTrail.add(new PointD(myCenter));
	}

	private void drawTrail(Graphics pen) {
		pen.setColor(myColor);
		// draw trail
		PointD previous = myTrail.get(0);
		for (int k = 1; k < myTrail.size(); k++) {
			PointD current = myTrail.get(k);
			pen.drawLine((int) previous.getX(), (int) previous.getY(),
					(int) current.getX(), (int) current.getY());
			previous = current;
		}
	}

	@Override
	public PointD getCenter() {
		return myCenter;
	}

	@Override
	public Color getColor() {
		return myColor;
	}

	public Dimension getSize() {
		return mySize;
	}

	@Override
	public PointD getVelocity() {
		return myVelocity;
	}

	@Override
	public void paint(Graphics pen) {
		if (myMaxTrail > 0) {
			drawTrail(pen);
		}
		pen.setColor(myColor);
		pen.fillOval((int) (myCenter.getX() - mySize.width / 2),
				(int) (myCenter.getY() - mySize.height / 2), mySize.width,
				mySize.height);
	}

	@Override
	public void setColor(Color color) {
		myColor = color;
	}

	public void setSize(int width, int height) {
		mySize = new Dimension(width, height);
	}

	public void setVelocity(PointD velocity) {
		myVelocity = new PointD(velocity);
	}

	@Override
	public void update(ISimulationModel model) {
		myCenter.translate(myVelocity);
		myTrail.add(new PointD(myCenter));
		if (myTrail.size() >= myMaxTrail) {
			myTrail.remove(0);
		}
		Dimension bounds = model.getSize();

		// check for move out of bounds on side walls
		int radius = mySize.width / 2;
		if (myCenter.getX() < 0) {
			myCenter.setX(bounds.getWidth() - myCenter.getX());
		} else if (myCenter.getX() > bounds.width) {
			myCenter.setX(myCenter.getX() - bounds.width);
		}

		// check for move out of bounds on ceiling and floor
		radius = mySize.height / 2;
		if (myCenter.getY() < radius) {
			myCenter.setY(bounds.getHeight() - myCenter.getY());
		} else if (myCenter.getY() > bounds.height) {
			myCenter.setY(myCenter.getY() - bounds.height);
		}
	}

}
