package creators;

import java.awt.*;
import java.util.Collection;

import behaviorTypes.Behavior;

import guiAndAbstracts.Canvas;


/**
 *  Represents a shape that follows its given leader.
 *  
 *  @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class SwarmFollower extends SwarmLeader
{
    private static final int MAX_SPEED = 12;
    // state
    private Mover myLeader;


    /**
     * Construct a ball at the given position, with the given velocity, 
     * size, and color.
     *
     * @param center position of the ball
     * @param size dimensions of the ball
     * @param velocity speed and direction of the ball
     * @param color color of the ball
     * @param trailSize number of lines to draw following the shape
     */
    public SwarmFollower (Point center,
                    Dimension size,
                    Point velocity,
                    Color color,
                    int trailSize,
                    Mover leader,
                    String name,
                    Collection<Behavior> behaviors,
                    int id
                    )
    {
        super(center, size, velocity, color, trailSize, name, behaviors, id);
        myLeader = leader;
    }


    /**
     * Describes how to "animate" follower by changing its state.
     *
     * @param bounds which encloses this ball
     */
    public void update (Canvas canvas)
    {
        // move shape by velocity
        super.update(canvas);
        Dimension bounds = canvas.getSize();
        // move closer to the leader, but in the general direction currently moving
        Point target = myLeader.getCenter();
        int dx = target.x - myCenter.x;
        int dy = target.y - myCenter.y;
        myVelocity.x = (int)(dx * 0.1 + canvas.nextIntInRange(-5, 5));
        myVelocity.y = (int)(dy * 0.1 + canvas.nextIntInRange(-5, 5));

        myVelocity.x = limitSpeed(myVelocity.x, MAX_SPEED);
        myVelocity.y = limitSpeed(myVelocity.y, MAX_SPEED);
        myCenter.x = wrapCoordinate(myCenter.x, bounds.width);
        myCenter.y = wrapCoordinate(myCenter.y, bounds.height);
    }
    
    
    
}
