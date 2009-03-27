package flocks;

import java.awt.*;

/**
 * Represents a ball that bounces around within the given bounds.
 * 
 * Draws itself as a circle.
 * 
 * @author Robert C. Duvall
 */
public class BouncingBall extends Mover
{
    /**
     * Create a bouncer
     * 
     * @param center initial position
     * @param size diameter of the bouncer
     * @param velocity amount to move in x- and y-direction
     * @param color color of the bouncer
     */
    public BouncingBall (PointD center,
                         Dimension size,
                         PointD velocity,
                         Color color,
                         int trailSize)
    {
        super(center, size, velocity, color, trailSize);
    }


    /**
     * Determine how the shape animates by making a small change to some
     * attribute (position, color, size, etc.) over time.
     * 
     * @param bounds within which bouncer appears
     */
    public void update (Canvas canvas)
    //torus world
    {
        // move shape by velocity
        super.update(canvas);
        Dimension bounds = canvas.getSize();

        // check for move out of bounds on side walls 
        int radius = mySize.width / 2;
        if (myCenter.x < 0)
        {
            myCenter.x = bounds.getWidth() - myCenter.x;
        }
        else if (myCenter.x > bounds.width)
        {
            myCenter.x = myCenter.x - bounds.width;
        }

        // check for move out of bounds on ceiling and floor
        radius = mySize.height / 2;
        if (myCenter.y < radius)
        {
            myCenter.y = bounds.getHeight() - myCenter.y;
        }
        else if (myCenter.y > bounds.height)
        {
            myCenter.y = myCenter.y - bounds.height;
        }
    }
    
    //bounce world
    /*{
        // move shape by velocity
        super.update(canvas);
        Dimension bounds = canvas.getSize();

        // check for move out of bounds on side walls 
        int radius = mySize.width / 2;
        if (myCenter.x < radius)
        {
            myVelocity.x *= -1;
            myCenter.x = radius;
        }
        else if (myCenter.x > bounds.width - radius)
        {
            myVelocity.x *= -1;
            myCenter.x = bounds.width - radius;
        }

        // check for move out of bounds on ceiling and floor
        radius = mySize.height / 2;
        if (myCenter.y < radius)
        {
            myVelocity.y *= -1;
            myCenter.y = radius;
        }
        else if (myCenter.y > bounds.height - radius)
        {
            myVelocity.y *= -1;
            myCenter.y = bounds.height - radius;
        }
    }*/


    /**
     * Render a graphical view of bouncer
     * 
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        // draw trail if it exists
        super.paint(pen);
        // draw shape
        pen.setColor(myColor);
        pen.fillOval((int) (myCenter.x - mySize.width / 2),
                     (int) (myCenter.y - mySize.height / 2),
                     mySize.width, mySize.height);
    }
}
