package creators;

import guiAndAbstracts.Canvas;
import behaviorTypes.*;
import constantsFiles.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
/**
 *  Mover representing a "bird" within a flock that can move coherently with the other
 *  flock members.
 *	@author David Eitel, Eric Wheeler
 */
public class FlockMember extends Mover implements Constants
{
	/**
     * Construct a shape at the given position, with the given velocity, 
     * size, behaviors, and color.
     *
     * @param center position of the shape
     * @param size dimensions of the shape
     * @param velocity speed and direction of the shape
     * @param color color of the shape
     * @param trailSize number of lines to draw following the shape
     */
     
	public FlockMember (Point center,
            Dimension size,
            Point velocity,
            Color color,
            int trailSize,
            String name,
            Collection<Behavior> behaviors,
            int id
            )
	{
		super(center, size, velocity, color, trailSize, name, behaviors, id);
		myBehaviors.add(new Cohesion());
		myBehaviors.add(new Alignment());
		myBehaviors.add(new CollisionAvoidance());
		myBehaviors.add(new RandomIntervalVelocity());
		myBehaviors.add(new SpeedLimiter());
	}
	
	
	 /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draws the shape as a simple circle.
     *
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        // draw trail if it exists
        pen.setColor(myColor);
        super.paint(pen);
        // draw shape
        pen.fillRect(myCenter.x - mySize.width / 2,
                     myCenter.y - mySize.height / 2,
                     mySize.width, mySize.height);        
    }
    /**
     * Set up specialized updating requirements.
     */
    public void computeNewVelocity(VelocityInfo v)
    {
    	super.computeNewVelocity(v);
	    myCenter.x = wrapCoordinate(myCenter.x, (int) v.getSize().getWidth());
	    myCenter.y = wrapCoordinate(myCenter.y, (int) v.getSize().getHeight());
    }
    

}