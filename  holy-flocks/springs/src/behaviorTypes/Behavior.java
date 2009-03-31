package behaviorTypes;

import creators.*;
/**
 * Interface for creating standalone and interchangeable individual movement.  Specifically
 * used to update an individuals velocity.
 * 
 * @author Eric
 *
 */
public interface Behavior 
{
	public void computeNewVelocity(Mover m, VelocityInfo v);
}
