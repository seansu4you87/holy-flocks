package behaviorTypes;

import java.awt.Point;

import constantsFiles.Constants;

import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior to make movers in a specific radius attempt to avoid each other.
 * 
 * @author Eric
 *
 */
public class CollisionAvoidance implements Behavior, Constants
{

	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		for(Mover a: v.getFlock().getAdjacentMembers(m, FLOCKLET_RADIUS))
		{
			if(!m.equals(this))
			{
				if(a.getCenter().distance(m.getCenter()) < m.getSize().getWidth()*3)
				{
					Point tooclose = new Point((int)(m.getCenter().getX()-a.getCenter().getX()),
							(int)(m.getCenter().getY()-a.getCenter().getY()));
					tooclose = m.normalize(tooclose);
					m.getVelocity().x += tooclose.x;
					m.getVelocity().y += tooclose.y;
				}
			}
		}
	}

}
