package creators;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Factory;
import guiAndAbstracts.Individual;
import guiAndAbstracts.SliderProperties;

/**
 *  Creates any number of Walker objects along a diagonal down the center of the canvas.
 */
public class WalkerFactory extends Factory
{
    /**
     * Construct factory.
     */
    public WalkerFactory (String label)
    {
        super(label);
    }


    /**
     * Creates given number of walkers within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param number number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public List<Individual> createMovers (Canvas canvas, SliderProperties information)
    {
    	List<Individual> individualList = new ArrayList<Individual>();
    	int number = information.getNumberObjects(); 
    	int trailSize = information.getTrailSize();
        // current size of canvas
        Dimension bounds = canvas.getSize();
        // all shapes share the same size
        float width = bounds.width / (float)number;
        float height = bounds.height / (float)number;
        int id = information.getID();
        // create each shape and add to list
        for (float k = 0; k < number; k++)
        {
        	individualList.add(
                new Walker(
                    new Point((int)((k + 0.5) * width),
                              (int)((k + 0.5) * height)),
                    new Dimension((int)width, (int)height),
                    new Point(0, 0),
                    new Color(1 - (k / number), 0.5f, k / number),
                    trailSize,
                    information.getParentName()+k+"",
                	information.getBehaviors(),
                	id));
        	id++;
        }
        return individualList;
    }
}
