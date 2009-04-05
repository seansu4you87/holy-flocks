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
    private static final int MIN_SIZE = 16;
    private static final int MAX_SIZE = 48;
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
        List<Individual> individualList = new ArrayList<Individual>();
        List<Mass> masses = new ArrayList<Mass>();
        int number = information.getNumberObjects();
        Dimension bounds = canvas.getSize();
        
        for (int i = 0; i < number/50; i++)
        {
            int size = canvas.nextIntInRange(MIN_SIZE, MAX_SIZE);
            masses.add(new Mass(new Point(canvas.nextIntInRange(size / 2, bounds.width - size / 2),
                                          canvas.nextIntInRange(size / 2, bounds.width - size / 2)),
                                          new Dimension(size, size),
                                          canvas.nextIntInRange(0, 10),
                                          information.getBehaviors(),
                                          i));
        }
        System.out.println(masses.get(0).getBehaviors());
        for (Mass m1: masses)
        {
            for (Mass m2: masses)
            {
                int length = canvas.nextIntInRange(100, 150);
                //individualList.add(new Spring(m1, m2, length, 0.005));
            }
            individualList.add(m1);
        }
        return individualList;
    }
}
