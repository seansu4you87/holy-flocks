package creators;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Factory;
import guiAndAbstracts.Individual;
import guiAndAbstracts.SliderProperties;


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
    public BouncerFactory (String label)
    {
        super(label);
    }


    /**
     * Creates given number of bouncers within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param numberToCreate number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public List<Individual> createMovers (Canvas canvas, SliderProperties information)
    {
    	List<Individual> individualList = new ArrayList<Individual>();
    	int number = information.getNumberObjects(); 
    	int trailSize = information.getTrailSize();
        // current size of canvas
        Dimension bounds = canvas.getSize();
        int id = information.getID();
        int maxVelocity = bounds.width / 40;
        // create each BouncingBall and add to collection
        for (int k = 0; k < number; k++)
        {
            int size = canvas.nextIntInRange(MIN_SIZE, MAX_SIZE);
            individualList.add(
                new BouncingSmiley(
                    new Point(canvas.nextIntInRange(size / 2, bounds.width - size / 2),
                              canvas.nextIntInRange(size / 2, bounds.width - size / 2)),
                    new Dimension(size, size),
                    new Point(canvas.nextNonZeroIntInRange(-maxVelocity, maxVelocity),
                              canvas.nextNonZeroIntInRange(-maxVelocity, maxVelocity)),
                    new Color(canvas.nextIntInRange(0, 255),
                              canvas.nextIntInRange(0, 255),
                              canvas.nextIntInRange(0, 255)),
                    trailSize,
                	information.getParentName()+k+"",
                	information.getBehaviors(),
                	id
            ));
            id++;
        }
        return individualList;
    }
    

}
