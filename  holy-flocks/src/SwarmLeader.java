package yourwork;

import java.awt.*;

import ignorethis.Canvas;


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
    public SwarmLeader (PointD center,
                  Dimension size,
                  PointD velocity,
                  Color color,
                  int trailSize)
    {
        super(center, size, velocity, color, trailSize);
    }


    /**
     * Describes how to "animate" the shape by changing its state.
     *
     * @param bounds which encloses this shape
     */
    public void update (Canvas canvas)
    {
        // move shape by velocity
        super.update(canvas);
        Dimension bounds = canvas.getSize();
        // move randomly, but in the general direction currently moving
        myVelocity.x = limitSpeed(myVelocity.x + canvas.nextIntInRange(-5, 5), MAX_SPEED);
        myVelocity.y = limitSpeed(myVelocity.y + canvas.nextIntInRange(-5, 5), MAX_SPEED);
        myCenter.x = wrapCoordinate(myCenter.x, bounds.width);
        myCenter.y = wrapCoordinate(myCenter.y, bounds.height);
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
        pen.drawLine((int) (myCenter.x - myVelocity.x / 2),
                     (int) (myCenter.y - myVelocity.y / 2), 
                     (int) (myCenter.x + myVelocity.x / 2), 
                     (int) (myCenter.y + myVelocity.y / 2));
    }


    /**
     * @return the given value constrained to the given bounds, 
     *         but if it goes off one side, it is mapped to the other side 
     */
    protected double wrapCoordinate (double value, double bounds)
    {
        if (value < 0)
        {
            value = bounds;
           }
        else if (value > bounds)
        {
            value = 0;
           }
        return value;
        // OR:
        // return (value + bounds) % bounds;
    }


    /**
     * @return the given value constrained to the given bounds, 
     *         but if it goes off one side, it is set to that max value 
     */
    protected double limitSpeed (double value, int max)
    {
        if (value > MAX_SPEED)
        {
            value = MAX_SPEED;
        }
        else if (value < -MAX_SPEED)
        {
            value = -MAX_SPEED;
        }
        return value;
        // OR:
        // return (int)(20.0 * value / Math.abs(value));
    }
}
