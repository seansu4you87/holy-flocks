package behaviorTypes;

import java.awt.Point;

import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior to make a group move in a similar direction.
 * 
 * @author emw12
 *
 */
public class Alignment implements Behavior 
{

	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		Point direction = v.getFlock().getAverageDirection(m);
		direction = m.normalize(direction);
		m.getVelocity().x += direction.x;
		m.getVelocity().y += direction.y;
	}

}
