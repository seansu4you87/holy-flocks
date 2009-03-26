package yourwork;

import java.awt.*;
import ignorethis.Canvas;


/**
 * NAME : 
 * COURSE : CompSci 6
 * PURPOSE : 
 *  Represents a shape that "walks" in random directions around the canvas.
 */
public class Walker extends Mover
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
    public Walker (PointD center,
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
     * Currently, moves the shape by a random amount in a random direction.
     *
     * @param bounds which encloses this shape
     */
    public void update (Canvas canvas)
    {
        // move shape by velocity
        super.update(canvas);
        double randAngle = Math.toRadians(canvas.nextIntInRange(0, 360));
        myVelocity.x = (int)(3 * Math.cos(randAngle));
        myVelocity.y = (int)(3 * Math.sin(randAngle));
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
        pen.fillRect((int) (myCenter.x - mySize.width / 2),
                     (int) (myCenter.y - mySize.height / 2),
                     mySize.width, mySize.height);
    }
}
