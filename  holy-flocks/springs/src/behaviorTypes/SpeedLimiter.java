package behaviorTypes;

import constantsFiles.Constants;
import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior to limit the speed to a reasonable maximum and minimum. (Set in constants file).
 * Good extension would be GUI ability to change MAX_SPEED setting.
 * @author Eric
 *
 */
public class SpeedLimiter implements Behavior , Constants
{

	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		m.getVelocity().x = m.limitSpeed(m.getVelocity().x, MAX_SPEED);
		m.getVelocity().y = m.limitSpeed(m.getVelocity().y, MAX_SPEED);
	}

}
