package creators;

import guiAndAbstracts.*;
import constantsFiles.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.*;

public class FlockMemberFactory extends Factory implements Constants
{
	public FlockMemberFactory (String label)
    {
        super(label);
    }
	/**
     * Creates given number of flock members within the given canvas.
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
        Group group = canvas.getGroup();
        for (int i=0; i<information.getNumberObjects(); i++)
        {
        //	group.addMember(arg0)
        }
        // create proper movers
        int radius = FLOCK_INDIVIDUAL_RADIUS;
        int circumference = (int)(2 * Math.PI * radius);
        int size = circumference;
        int id = information.getID();
        for (float k = 0; k < 360; k += 360.0f / number)
        {
        	individualList.add(
                new FlockMember(
                		new Point(canvas.nextIntInRange(bounds.width / 2, bounds.width),
                                canvas.nextIntInRange(bounds.height / 2, bounds.height)),
                    new Dimension(FLOCK_INDIVIDUAL_RADIUS,FLOCK_INDIVIDUAL_RADIUS),
                    new Point((int) Math.round(Math.random()*3),
                    		(int) Math.round(Math.random()*3)),
                    new Color(1 - k / 360, 0.5f, k / 360),
                    trailSize,
                    information.getParentName()+k+"",
                	information.getBehaviors(),
                	id));
        	id++;
        }
        return individualList;
    }
}
