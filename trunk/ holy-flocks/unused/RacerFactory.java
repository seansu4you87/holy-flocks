package unused;

import java.awt.*;


import flocks.PointD;
import flocks.Canvas;
import flocks.Factory;


/**
 * NAME : 
 * COURSE : CompSci 6
 * PURPOSE : 
 *  Creates any number of Racer objects in a line down the left side of the canvas.
 */
public class RacerFactory extends Factory
{
    /**
     * Construct factory.
     */
    public RacerFactory ()
    {
        super("Create Racers");
    }


    /**
     * Creates given number of racers within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param number number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public void createMovers (Canvas canvas, int number, int trailSize)
    {
        // current size of canvas
        Dimension bounds = canvas.getSize();
        Dimension size = new Dimension(20, bounds.height / number);
        // create each shapes and add to list
        for (float k = 0; k < number; k++)
        {
            canvas.add(
                new Racer(
                    new PointD(0, (int)((k + 0.5) * size.height)),
                    size,
                    new PointD(0, 0),
                    new Color(1 - (k / number), 0.5f, k / number),
                    trailSize));
        }
    }
}
