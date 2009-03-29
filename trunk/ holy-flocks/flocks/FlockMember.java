package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import simulationFramework.ISimulationComponent;
import simulationFramework.ISimulationModel;
import simulationFramework.Applet;
import simulationFramework.PointD;

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
	private Dimension mySize;
	private List<PointD> myTrail;
	private PointD myVelocity;
	
	public FlockMember(String name){
		myName = name;
	}

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
	}

}
