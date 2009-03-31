package behaviorTypes;

import creators.*;
import java.awt.*;
/**
 * Behavior to move set of movers toward the center of the canvas window.
 * 
 * @author emw12
 *
 */
public class Attraction implements Behavior
{
	public void computeNewVelocity(Mover mover, VelocityInfo information)
	{
		Dimension bounds = information.getSize();
        int dx = bounds.width / 2 - mover.getCenter().x;
        int dy = bounds.height / 2 - mover.getCenter().y;
        mover.getVelocity().x = (int)(mover.getVelocity().x + dx * 0.01);
        mover.getVelocity().y = (int)(mover.getVelocity().y + dy * 0.01);
	}
}
