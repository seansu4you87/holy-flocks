package creators;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Factory;
import guiAndAbstracts.Individual;
import guiAndAbstracts.SliderProperties;

/**
 * Class to create given number of racer objects on the canvas.
 * 
 *@author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class RacerFactory extends Factory
{
    /**
     * Construct factory.
     */
    public RacerFactory (String label)
    {
        super(label);
    }


    /**
     * Creates given number of racers within the given canvas.
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
        Dimension size = new Dimension(20, bounds.height / number);
        int id = information.getID();
        // create each shapes and add to list
        for (float k = 0; k < number; k++)
        {
        	individualList.add(
                new Racer(
                    new Point(0, (int)((k + 0.5) * size.height)),
                    size,
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
