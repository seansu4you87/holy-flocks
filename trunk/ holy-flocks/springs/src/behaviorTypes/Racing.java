package behaviorTypes;

import creators.*;
import java.util.*;
/**
 * Behavior used to emulate a race from left to write across the screen.
 * Using this behavior in conjunction with other behavior types often leads to
 * poor results. (i.e. They won't move from left to right but sporadically).
 * @author Eric
 *
 */
public class Racing implements Behavior
{
	private Random myRandomGenerator = new Random();
	
	public void computeNewVelocity(Mover m, VelocityInfo v)
	{
		if (m.getCenter().x < v.getSize().getWidth())
        {
            m.getVelocity().x = myRandomGenerator.nextInt(10) + 1;
        }
        else
        {
            m.getVelocity().x = 0;            
        }
	}
}
