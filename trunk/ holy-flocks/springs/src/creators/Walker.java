package creators;

import java.awt.*;
import java.util.*;

import behaviorTypes.Behavior;
import behaviorTypes.Walking;

import guiAndAbstracts.Canvas;


/**
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
	
    public Walker (Point center,
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
        myBehaviors.add(new Walking());
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
}
