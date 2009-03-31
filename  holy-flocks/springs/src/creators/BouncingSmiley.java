package creators;

import java.awt.*;
import java.util.Collection;

import behaviorTypes.Behavior;

import guiAndAbstracts.Canvas;


/**
 * Represents a smiley face that bounces around within the given bounds.
 * 
 * Draws itself as a smiley face.
 * 
 * @author @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class BouncingSmiley extends BouncingBall
{
    /**
     * Create a bouncer
     * 
     * @param center initial position
     * @param size diameter of the bouncer
     * @param velocity amount to move in x- and y-direction
     * @param color color of the bouncer
     */
    public BouncingSmiley (Point center,
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
     * Describes how to "animate" the shape by changing its state.
     *
     * Currently, moves by the current velocity.
     *
     * @param bounds which encloses this shape
     */
    public void update (Canvas canvas)
    {
        super.update(canvas);
        myVelocity.y++;
    }


    /**
     * Paint graphical view of bouncing smiley
     *
     * @param pen knows how to draw on the screen
     */
    public void paint (Graphics pen)
    {
        // draw trail if it exists
        super.paint(pen);
        // draw shape
        pen.setColor(java.awt.Color.BLACK);
        // left eye
        pen.fillOval(myCenter.x - mySize.width / 8 - mySize.width / 20,
                     myCenter.y - mySize.height / 8 - mySize.height / 10,
                     mySize.width / 10, mySize.height / 5);
        // right eye
        pen.fillOval(myCenter.x + mySize.width / 8 - mySize.width / 20,
                     myCenter.y - mySize.height / 8 - mySize.height / 10,
                     mySize.width / 10, mySize.height / 5);
        // smile!
        pen.drawArc(myCenter.x - 11 * mySize.width / 40,
                    myCenter.y + mySize.height / 20 - 11 * mySize.height / 40,
                    11 * mySize.width / 20, 11 * mySize.height / 20,
                    345, -150);
    }
}
