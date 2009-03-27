package flocks;

import java.awt.*;




/**
 *  Creates any number of Bouncer objects bouncing around the canvas.
 */
public class BouncerFactory extends Factory
{
    // make it easy to change features of the bouncers
    private static final int MIN_SIZE = 16;
    private static final int MAX_SIZE = 48;


    /**
     * Construct factory.
     */
    public BouncerFactory ()
    {
        super("Create Bouncers");
    }


    /**
     * Creates given number of bouncers within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param numberToCreate number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public void createMovers (Canvas canvas, int number, int trailSize)
    {
        // current size of canvas
        Dimension bounds = canvas.getSize();

        int maxVelocity = bounds.width / 40;
        
        Flock myFlock = new Flock();
        
        // create each BouncingBall and add to collection
        for (int k = 0; k < number; k++)
        {
            int size = canvas.nextIntInRange(MIN_SIZE, MAX_SIZE);
            
            BouncingSmiley temp = new BouncingSmiley(
                    new PointD(canvas.nextIntInRange(size / 2, bounds.width - size / 2),
                              canvas.nextIntInRange(size / 2, bounds.width - size / 2)),
                    new Dimension(10, 10),
                    new PointD(canvas.nextNonZeroIntInRange(-maxVelocity, maxVelocity),
                              canvas.nextNonZeroIntInRange(-maxVelocity, maxVelocity)),
                    new Color(canvas.nextIntInRange(0, 255),
                              canvas.nextIntInRange(0, 255),
                              canvas.nextIntInRange(0, 255)),
                    trailSize);
            myFlock.add(temp);
            canvas.add(myFlock);
            canvas.add(temp);
        }
    }
}
