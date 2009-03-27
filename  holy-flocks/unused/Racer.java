package unused;

import java.awt.*;

import flocks.Mover;
import flocks.PointD;
import flocks.Canvas;


/**
 * NAME : 
 * COURSE : CompSci 6
 * PURPOSE : 
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
    public Racer (PointD center,
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
     * Currently, moves the shape in random increments to the right until 
     * reaches the end of the canvas.
     *
     * @param bounds which encloses this shape
     */
    public void update (Canvas canvas)
    {
        // move shape by velocity
        super.update(canvas);
        Dimension bounds = canvas.getSize();
        if (myCenter.x < bounds.width)
        {
            myVelocity.x = canvas.nextIntInRange(1, 10);
        }
        else
        {
            myVelocity.x = 0;            
        }
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
