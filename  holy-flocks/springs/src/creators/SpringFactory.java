package creators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.*;
import guiAndAbstracts.*;
import constantsFiles.*;


/**
 * Creates a number of masses and springs, whose attributes are read from the given data file.
 */
public class SpringFactory extends Factory implements Constants
{
    /**
     * Construct factory.
     */
    public SpringFactory (String label)
    {
        super(label);
    }


    /**
     * Creates a number of bouncing balls within the given canvas using data from the given file.
     *
     * @param canvas within which shapes should appear
     * @param input scanner attached to a file with the appropriate format
     */
    public List<Individual> createMovers (Canvas canvas, SliderProperties information)
    {
        List<Individual> masses = new ArrayList<Individual>();

        // create an initial scenario to animate
        masses.add(new Mass (new Point(100, 100), new Dimension(50, 50), Color.GREEN, 5));
        masses.add(new Mass(new Point(300, 100), new Dimension(50, 50), Color.RED, 7));
        masses.add(new Mass(new Point(200, 300), new Dimension(50, 50), Color.MAGENTA, 10));
        masses.add(new Mass(new Point(200, 300), new Dimension(50, 50), Color.MAGENTA, 10));
        canvas.add(new Spring((Mass)masses.get(0), (Mass)masses.get(1), 150, 0.005));
        canvas.add(new Spring((Mass)masses.get(0), (Mass)masses.get(2), 100, 0.005));
        canvas.add(new Spring((Mass)masses.get(1), (Mass)masses.get(2), 100, 0.005));
        
        //canvas.add(new Spring(masses.get(0), masses.get(3), 150, 0.005));
        //canvas.add(new Spring(masses.get(1), masses.get(3), 150, 0.005));
        //canvas.add(new Spring(masses.get(2), masses.get(3), 150, 0.005));
        

        for (Individual m : masses)
        {
            canvas.add(m);
        }
        return masses;
    }
}
