package yourwork;

import java.awt.*;
import ignorethis.Canvas;
import ignorethis.Factory;


/**
 * NAME : 
 * COURSE : CompSci 6
 * PURPOSE : 
 *  Creates any number of Attractor objects in a circle around the center of the canvas.
 */
public class AttractorFactory extends Factory
{
    /**
     * Construct factory.
     */
    public AttractorFactory ()
    {
        super("Create Attractors");
    }


    /**
     * Creates given number of attractors within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param number number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public void createMovers (Canvas canvas, int number, int trailSize)
    {
        // current size of canvas
        Dimension bounds = canvas.getSize();
        Point center = new Point(bounds.width / 2, bounds.height / 2);
        // create proper movers
        int radius = Math.min(bounds.width, bounds.height) / 2;
        int circumference = (int)(2 * Math.PI * radius);
        int size = circumference / number;
        for (float k = 0; k < 360; k += 360.0f / number)
        {
            int speed = canvas.nextIntInRange(5, 15);
            canvas.add(
                new Attractor(
                    new PointD((int)(center.x + Math.cos(Math.toRadians(k)) * radius),
                              (int)(center.y + Math.sin(Math.toRadians(k)) * radius)),
                    new Dimension(size, size),
                    new PointD((int)(Math.cos(Math.toRadians(k + 70) * speed)),
                              (int)(Math.sin(Math.toRadians(k + 70) * speed))),
                    new Color(1 - k / 360, 0.5f, k / 360),
                    trailSize));
        }
    }
}
