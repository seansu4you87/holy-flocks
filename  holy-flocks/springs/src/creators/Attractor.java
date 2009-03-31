package creators;

import java.awt.*;
import behaviorTypes.*;
import guiAndAbstracts.Canvas;
import java.util.*;


/**
 * Creates a moving object that seeks the center of the canvas.
 * 
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class Attractor extends Mover
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
	
    public Attractor (Point center,
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
        myBehaviors.add(new Attraction());
    }

    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draws the shape as a simple circle.
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
