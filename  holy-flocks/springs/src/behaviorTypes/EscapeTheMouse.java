package behaviorTypes;

import java.awt.Point;

import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior used in conjunction with a mouse listener to make individuals avoid the mouse cursor.
 * 
 * @author Eric
 *
 */
public class EscapeTheMouse implements Behavior {

	@Override
	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		Point difference = new Point(m.getCenter().x - v.getMyMousePosition().x, m.getCenter().y - v.getMyMousePosition().y);
		difference = m.normalize(difference);
		m.getVelocity().x += difference.x;
		m.getVelocity().y += difference.y;
		if(m.getCenter().distance(v.getMyMousePosition()) < m.getSize().width*2)
		{
			m.getVelocity().x += 2*difference.x;
			m.getVelocity().y += 2*difference.y;
		}
	}

}
