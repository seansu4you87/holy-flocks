package behaviorTypes;

import creators.Mover;
import creators.VelocityInfo;
/**
 * Naive Behavior that keeps an object in one place always.
 * Customary not to use with other behaviors since it nullifies
 * their effect or vice versa.
 * @author Eric
 *
 */
public class StandingStill implements Behavior
{

	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		m.getVelocity().x = 0;
		m.getVelocity().y = 0;
	}

}
