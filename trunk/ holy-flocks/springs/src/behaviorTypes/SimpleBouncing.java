package behaviorTypes;

import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior to simulate balls bouncing on the ground with elastic collisions and
 * rudimentary "gravity" turned on.
 * This algorithm still needs a little work before it looks exactly like the original
 * screensavers bouncers.
 * 
 * @author Eric
 *
 */
public class SimpleBouncing implements Behavior {

	@Override
	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		int radius = m.getSize().width / 2;
		//check for move out of bounds on both walls
		if (m.getCenter().x < radius)
		{
			m.getVelocity().x *= -1;
			m.getCenter().x = radius;
		}
		else if (m.getCenter().x > v.getSize().width - radius)
		{
			m.getVelocity().x *= -1;
			m.getCenter().x = v.getSize().width  - radius;
		}

		// check for move out of bounds on ceiling and floor
		radius = m.getSize().height / 2;
		if (m.getCenter().y < radius)
		{
			m.getVelocity().y *= -1;
			m.getCenter().y = radius;
		}
		else if (m.getCenter().y > v.getSize().height  - radius)
		{
			m.getVelocity().y *= -1;
			m.getCenter().y = v.getSize().height  - radius;
		}
		m.getVelocity().y++;
	}

}
