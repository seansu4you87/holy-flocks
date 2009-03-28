package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import simulationFramework.ISimulationModel;
import simulationFramework.PointD;

public class FlockMemberBall extends FlockMember {

    public FlockMemberBall(PointD center, PointD velocity, Dimension size,
	    Color color, String name, int trailSize) {
	super(center, velocity, size, color, name, trailSize);
	
    }
    
    public void update(ISimulationModel model){
	// move shape by velocity
	super.update(model);
	Dimension bounds = ((FlockModel) model).getSize();

	// check for move out of bounds on side walls
	int radius = getSize().width / 2;
	if (getCenter().getX() < 0) {
		getCenter().setX(bounds.getWidth() - getCenter().getX());
	} else if (getCenter().getX() > bounds.width) {
	    getCenter().setX(getCenter().getX() - bounds.width);
	}

	// check for move out of bounds on ceiling and floor
	radius = getSize().height / 2;
	if (getCenter().getY() < radius) {
	    getCenter().setY(bounds.getHeight() - getCenter().getY());
	} else if (getCenter().getY() > bounds.height) {
	    getCenter().setY(getCenter().getY() - bounds.height);
	}
    }
    
 // bounce world
	/*
	 * { // move shape by velocity super.update(canvas); Dimension bounds =
	 * canvas.getSize();
	 * 
	 * // check for move out of bounds on side walls int radius = mySize.width /
	 * 2; if (myCenter.x < radius) { myVelocity.x *= -1; myCenter.x = radius; }
	 * else if (myCenter.x > bounds.width - radius) { myVelocity.x *= -1;
	 * myCenter.x = bounds.width - radius; }
	 * 
	 * // check for move out of bounds on ceiling and floor radius =
	 * mySize.height / 2; if (myCenter.y < radius) { myVelocity.y *= -1;
	 * myCenter.y = radius; } else if (myCenter.y > bounds.height - radius) {
	 * myVelocity.y *= -1; myCenter.y = bounds.height - radius; } }
	 */

    public void paint(Graphics pen){
	super.paint(pen);
	pen.setColor(getColor());
	pen.fillOval((int) (getCenter().getX() - getSize().width / 2),
			(int) (getCenter().getY() - getSize().height / 2), getSize().width,
			getSize().height);
    }
}
