package creators;

import java.awt.*;
import java.util.Collection;
import java.util.ArrayList;
import behaviorTypes.Behavior;
import behaviorTypes.Racing;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Individual;


/**
 *  Represents a shape that races across the canvas.
 */
public class Racer extends Mover
{
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
    public Racer (Point center,
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
        myBehaviors.add(new Racing());
    }


    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draws the shape as a simple rectangle.
     *
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        // draw trail if it exists
        super.paint(pen);
        // draw shape
        pen.setColor(myColor);
        pen.fillRect(myCenter.x - mySize.width / 2,
                     myCenter.y - mySize.height / 2,
                     mySize.width, mySize.height);
    }


	/**
     * Colors the racer that is farthest ahead RED and all the other
     * racers that are behind BLACK.  If several racers all share the 
     * lead, the one with the least y-value should be colored RED.
     * 
     * @param racers a collection with at least one racer in it
     */
    private void colorLeadingRacer (ArrayList<Individual> racers)
    {
        // can assume that there is at least one racer
        Mover leader = (Mover)racers.get(0);
        int maxx = leader.getCenter().x;
        for (Individual i : racers)
        {
            if(i instanceof Mover)
            {
            	Mover r = (Mover)i;
            	if (r.getCenter().x > maxx)
	            {
	                maxx = r.getCenter().x;
	                leader = r;
	            }
	            r.setColor(Color.BLACK);
            }
        }
        leader.setColor(Color.RED);
    }
    
	
	public void computeNewVelocity(VelocityInfo v) 
	{
		super.computeNewVelocity(v);
		colorLeadingRacer((ArrayList) v.getFlock().getMembers());	
	}
}
