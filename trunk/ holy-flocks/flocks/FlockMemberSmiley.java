package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import simulationFramework.PointD;

public class FlockMemberSmiley extends FlockMemberBall {

    public FlockMemberSmiley(PointD center, PointD velocity, Dimension size,
	    Color color, String name, int trailSize) {
	super(center, velocity, size, color, name, trailSize);
    }
    
	public void paint(Graphics pen) {
		// draw trail if it exists
		super.paint(pen);
		// draw shape
		pen.setColor(java.awt.Color.BLACK);
		// left eye
		pen
				.fillOval(
						(int) (getCenter().getX() - getSize().width / 8 - getSize().width / 20),
						(int) (getCenter().getY() - getSize().height / 8 - getSize().height / 10),
						getSize().width / 10, getSize().height / 5);
		// right eye
		pen
				.fillOval(
						(int) (getCenter().getX() + getSize().width / 8 - getSize().width / 20),
						(int) (getCenter().getY() - getSize().height / 8 - getSize().height / 10),
						getSize().width / 10, getSize().height / 5);
		// smile!
		pen
				.drawArc(
						(int) (getCenter().getX() - 11 * getSize().width / 40),
						(int) (getCenter().getY() + getSize().height / 20 - 11 * getSize().height / 40),
						11 * getSize().width / 20, 11 * getSize().height / 20, 345,
						-150);
	}
}
