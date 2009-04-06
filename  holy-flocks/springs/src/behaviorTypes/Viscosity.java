package behaviorTypes;

import creators.Force;
import creators.Mass;
import creators.Mover;
import creators.VelocityInfo;

/**
 * Behavior to simulate viscosity in the air.  
 * This needs a lot of work because right now it merely slows down the balls 
 * and brings it down to the "ground".
 * @author Sean Yu, Weiping Zhang
 *
 */
public class Viscosity implements Behavior
{

    public void computeNewVelocity (Mover m, VelocityInfo v)
    {
        System.out.println("Viscosity");
        Mass mass = (Mass)m;
        Force opposite = mass.getPreciseVelocity().negate();
        opposite.scale(0.01);
        mass.getPreciseVelocity().add(opposite);
        m.getVelocity().x = (int)Math.round(mass.getPreciseVelocity().getXChange());
        m.getVelocity().y = (int)Math.round(mass.getPreciseVelocity().getYChange());
    }
}
