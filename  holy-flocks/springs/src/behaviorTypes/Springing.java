package behaviorTypes;

import java.awt.Point;

import creators.Force;
import creators.Mass;
import creators.Mover;
import creators.VelocityInfo;

/**
 * Behavior to spring
 * @author seansu4you87
 *
 */
public class Springing implements Behavior
{
    private Force GRAVITY = new Force(90, 1);

    public void computeNewVelocity (Mover m, VelocityInfo v)
    {
        System.out.println("computeNewVelocity from springing is being called");
        Mass mass = (Mass) m;
        Force myForce = mass.getForce();
        Force myPreciseVelocity = mass.getPreciseVelocity();
        double myMass = mass.getMass();
        
        myForce.add(GRAVITY);
        myForce.scale(1.0 / myMass);
        myPreciseVelocity.add(myForce);
        myForce.reset();

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

        // convert force back into Mover's velocity
        //m.setVelocity((int)Math.round(myPreciseVelocity.getXChange()),
        //            (int)Math.round(myPreciseVelocity.getYChange()));
        m.getVelocity().x = (int)Math.round(myPreciseVelocity.getXChange());
        m.getVelocity().y = (int)Math.round(myPreciseVelocity.getXChange());
        
    }

}
