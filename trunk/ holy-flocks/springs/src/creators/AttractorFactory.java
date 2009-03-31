package creators;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Factory;
import guiAndAbstracts.Individual;
import guiAndAbstracts.SliderProperties;


/**
 * Makes number of attractors equivalent to that indicated by GUI slider.
 * 
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
 */

public class AttractorFactory extends Factory
{
    /**
     * Construct factory.
     */
    public AttractorFactory (String label)
    {
        super(label);
    }


    /**
     * Creates given number of attractors within the given canvas.
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
        Point center = new Point(bounds.width / 2, bounds.height / 2);
        // create proper movers
        int radius = Math.min(bounds.width, bounds.height) / 2;
        int circumference = (int)(2 * Math.PI * radius);
        int size = circumference / number;
        int id=information.getID();
        for (float k = 0; k < 360; k += 360.0f / number)
        {
            int speed = canvas.nextIntInRange(5, 15);
            individualList.add(
                new Attractor(
                    new Point((int)(center.x + Math.cos(Math.toRadians(k)) * radius),
                              (int)(center.y + Math.sin(Math.toRadians(k)) * radius)),
                    new Dimension(size, size),
                    new Point((int)(Math.cos(Math.toRadians(k + 70) * speed)),
                              (int)(Math.sin(Math.toRadians(k + 70) * speed))),
                    new Color(1 - k / 360, 0.5f, k / 360),
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
