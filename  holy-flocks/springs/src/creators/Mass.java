package creators;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Image;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import behaviorTypes.*;


/**
 * 
 * @author Robert C. Duvall
 */
public class Mass extends Image
{
    private Force GRAVITY = new Force(90, 1);
    private Force myPreciseVelocity;
    private Force myForce;
    private double myMass;


    public Mass (Point center, Dimension size, double mass, Collection<Behavior> behaviors, int id)
    {
        super(center, size, new Point(), "images/mass.gif", behaviors, id);
        myMass = mass;
        myForce = new Force();
        myPreciseVelocity = new Force();
        myBehaviors.add(new Springing());
    }

   /* public void update (Canvas canvas)
    {
        // add gravity towards bottom
        myForce.add(GRAVITY);
        myForce.scale(1.0 / myMass);
        myPreciseVelocity.add(myForce);
        myForce.reset();

        // check for move out of bounds
        Force impulse = new Force();
        if (getLeft() < 0)
        {
            impulse = new Force(0, 2);
            getCenter().x = getSize().width / 2;
        }
        else if (getRight() > canvas.getSize().width)
        {
            impulse = new Force(180, 2);
            getCenter().x = canvas.getSize().width - getSize().width / 2;
        }
        if (getTop() < 0)
        {
            impulse = new Force(270, 2);
            getCenter().y = getSize().height / 2;
        }
        else if (getBottom() > canvas.getSize().height)
        {
            impulse = new Force(90, 2);
            getCenter().y = canvas.getSize().height - getSize().height / 2;
        }
        impulse.scale(myPreciseVelocity.getRelativeMagnitude(impulse));
        myPreciseVelocity.add(impulse);

        // convert force back into Mover's velocity
        setVelocity((int)Math.round(myPreciseVelocity.getXChange()),
                    (int)Math.round(myPreciseVelocity.getYChange()));
        super.update(canvas);
    }*/
    
    public Force getForce()
    {
        return myForce;
    }
    
    public Force getPreciseVelocity()
    {
        return myPreciseVelocity;
    }
    
    public double getMass()
    {
        return myMass;
    }


    /**
     * New method, specific to Mass objects.
     * 
     * @param f force to be applied to this mass
     */
    public void applyForce (Force f)
    {
        myForce.add(f);
    }
}
