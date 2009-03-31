package behaviorTypes;

import java.awt.Point;

import creators.Flock;
import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior used to make a localized group of movers attract to the center of the group.  "Center seeking".
 * 
 * @author Eric
 *
 */
public class Cohesion implements Behavior
{

	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		Flock f = v.getFlock();
		Point flockCenter = f.getAverageLocation(m);
		Point difference = new Point((int)Math.round(flockCenter.x-m.getCenter().x), (int)Math.round(flockCenter.y-m.getCenter().y));
		difference = m.normalize(difference);
		m.getVelocity().x += difference.x;
		m.getVelocity().y += difference.y;
	}

}
