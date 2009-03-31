package creators;

import java.awt.*;
import java.util.Collection;

import behaviorTypes.Behavior;
import behaviorTypes.SimpleBouncing;
import guiAndAbstracts.Canvas;


/**
 * Represents a ball that bounces around within the given bounds.
 * 
 * Draws itself as a circle.
 * 
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
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
    public BouncingBall (Point center,
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
         myBehaviors.add(new SimpleBouncing());
    }

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
        pen.fillOval(myCenter.x - mySize.width / 2,
                     myCenter.y - mySize.height / 2,
                     mySize.width, mySize.height);
    }
}
