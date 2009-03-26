package yourwork;

import java.awt.*;
import ignorethis.Canvas;


/**
 *  Represents a shape that follows its given leader.
 *  
 *  @author Robert C. Duvall
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
    public SwarmFollower (PointD center,
                    Dimension size,
                    PointD velocity,
                    Color color,
                    int trailSize,
                    Mover leader)
    {
        super(center, size, velocity, color, trailSize);
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
        PointD target = myLeader.getCenter();
        double dx = target.x - myCenter.x;
        double dy = target.y - myCenter.y;
        myVelocity.x = (int)(dx * 0.1 + canvas.nextIntInRange(-5, 5));
        myVelocity.y = (int)(dy * 0.1 + canvas.nextIntInRange(-5, 5));

        myVelocity.x = limitSpeed(myVelocity.x, MAX_SPEED);
        myVelocity.y = limitSpeed(myVelocity.y, MAX_SPEED);
        myCenter.x = wrapCoordinate(myCenter.x, bounds.width);
        myCenter.y = wrapCoordinate(myCenter.y, bounds.height);
    }
}
