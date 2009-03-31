package creators;

import java.awt.*;
import java.util.Collection;
import java.util.Random;

import behaviorTypes.Behavior;

import guiAndAbstracts.Canvas;


/**
 *  Represents a shape that moves randomly across the canvas.
 *  
 *  @author Robert C. Duvall
 */
public class SwarmLeader extends Mover
{
    private static final int MAX_SPEED = 20;


    /**
     * Construct a shape at the given position, with the given velocity, 
     * size, and color.
     *
     * @param center position of the shape
     * @param size dimensions of the shape
     * @param velocity speed and direction of the shape
     * @param color color of the shape
     * @param trailSize number of lines to draw following the shape
     */
    public SwarmLeader (Point center,
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
    }

    /**
     * Describes how to draw a swarm member on the screen.
     *
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        // draw trail if it exists
        super.paint(pen);
        // draw shape as a changing line
        pen.setColor(myColor);
        pen.drawLine(myCenter.x - myVelocity.x / 2,
                     myCenter.y - myVelocity.y / 2, 
                     myCenter.x + myVelocity.x / 2, 
                     myCenter.y + myVelocity.y / 2);
    }

	public void computeNewVelocity(VelocityInfo v)
	{
		//Dimension bounds = canvas.getSize();
        // move randomly, but in the general direction currently moving
		Random r = new Random();
        myVelocity.x = limitSpeed(myVelocity.x + nextIntInRange(-5, 5, r), MAX_SPEED);
        myVelocity.y = limitSpeed(myVelocity.y + nextIntInRange(-5, 5, r), MAX_SPEED);
	    myCenter.x = wrapCoordinate(myCenter.x, (int) v.getSize().getWidth());
	    myCenter.y = wrapCoordinate(myCenter.y, (int) v.getSize().getHeight());
	}
	
	public int nextIntInRange (int min, int max, Random r)
    {
		return r.nextInt(max - min + 1) + min;
    }

}
