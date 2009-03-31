package behaviorTypes;

import java.util.Random;

import constantsFiles.Constants;
import creators.Mover;
import creators.VelocityInfo;
/**
 * Behavior introduces random variation to individual movement at specific time intervals
 * determined by constant RANDOMIZER_TIME.  Could add extensibility/functionality by placing
 * a text box to control random time.
 * @author Eric
 *
 */
public class RandomIntervalVelocity implements Behavior , Constants
{
	private Random myRandomGenerator = new Random();
	private int myClock = 1;
	
	public void computeNewVelocity(Mover m, VelocityInfo v) 
	{
		if(myClock % RANDOMIZER_TIME == 0)
		{
			m.getVelocity().x += (int) Math.round(3*myRandomGenerator.nextGaussian());
			m.getVelocity().y += (int) Math.round(3*myRandomGenerator.nextGaussian());
			myClock = 1;
		}
		else
		{
			myClock++;
		}
	}

}
