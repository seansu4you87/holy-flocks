package creators;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Image;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import behaviorTypes.*;


/**
 * 
 * @author Robert C. Duvall, Sean Yu, Weiping Zhang
 */
public class Mass extends Image
{
    private Force GRAVITY = new Force(90, 1);
    private Force myPreciseVelocity;
    private Force myForce;
    private double myMass;
    private Random myGen = new Random();


    public Mass (Point center, Dimension size, double mass, Collection<Behavior> behaviors, int id)
    {
        super(center, size, new Point(), "images/mass.gif", behaviors, id);
        myMass = mass;
        myForce = new Force();
        myPreciseVelocity = new Force(myGen.nextInt(360), 1);
    }

    public void update (Canvas canvas)
    {
        setVelocity((int)Math.round(myPreciseVelocity.getXChange()),
                    (int)Math.round(myPreciseVelocity.getYChange()));
        super.update(canvas);
    }
    
    public Force getForce()
    {
        return myForce;
    }
    
    public Force getPreciseVelocity()
    {
        return myPreciseVelocity;
    }
    
    public void setPreciseVelocity(Force newPreciseVelocity)
    {
        myPreciseVelocity = newPreciseVelocity;
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
