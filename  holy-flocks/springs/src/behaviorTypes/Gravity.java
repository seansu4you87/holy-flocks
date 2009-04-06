package behaviorTypes;

import creators.Force;
import creators.Mass;
import creators.Mover;
import creators.VelocityInfo;

/**
 * Behavior to simulate gravity.
 * @author Sean Yu, Weiping Zhang
 *
 */
public class Gravity implements Behavior
{

    public void computeNewVelocity (Mover m, VelocityInfo v)
    {
        System.out.println("Gravity");
        Mass mass = (Mass) m;
        mass.getForce().add(new Force(90, 1));
        mass.getForce().scale(1.0 / mass.getMass());
        mass.getPreciseVelocity().add(mass.getForce());
        mass.getForce().reset();
        
        m.getVelocity().x = (int)Math.round(mass.getPreciseVelocity().getXChange());
        m.getVelocity().y = (int)Math.round(mass.getPreciseVelocity().getYChange());
        
    }

}
