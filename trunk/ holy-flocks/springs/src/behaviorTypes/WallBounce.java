package behaviorTypes;

import creators.Force;
import creators.Mass;
import creators.Mover;
import creators.VelocityInfo;

/**
 * Behavior to simulate the masses being repelled by the walls
 * @author Sean Yu, Weiping Zhang
 *
 */
public class WallBounce implements Behavior
{

    public void computeNewVelocity (Mover m, VelocityInfo v)
    {
        System.out.println("WallBounce");
        Mass mass = (Mass) m;
        Force myPreciseVelocity = mass.getPreciseVelocity();
        // check for move out of bounds
        Force impulse = new Force();
        if (mass.getLeft() < 0)
        {
            impulse = new Force(0, 2);
            mass.getCenter().x = mass.getSize().width / 2;
        }
        else if (mass.getRight() > v.getSize().width)
        {
            impulse = new Force(180, 2);
            mass.getCenter().x = v.getSize().width - mass.getSize().width / 2;
        }
        if (mass.getTop() < 0)
        {
            impulse = new Force(270, 2);
            mass.getCenter().y = mass.getSize().height / 2;
        }
        else if (mass.getBottom() > v.getSize().height)
        {
            impulse = new Force(90, 2);
            mass.getCenter().y = v.getSize().height - mass.getSize().height / 2;
        }
        impulse.scale(myPreciseVelocity.getRelativeMagnitude(impulse));
        myPreciseVelocity.add(impulse);
        
        m.getVelocity().x = (int)Math.round(myPreciseVelocity.getXChange());
        m.getVelocity().y = (int)Math.round(myPreciseVelocity.getXChange());
    }

}
