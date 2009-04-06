/**
 * 
 */
package creators;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Individual;

import java.util.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import behaviorTypes.Behavior;
import constantsFiles.*;
import guiAndAbstracts.*;


/**
 * @author Sean Yu, Weiping Zhang
 * 
 * Assembly composed of Masses and Springs
 *
 */
public class Assembly extends Group implements Constants
{

    private Collection<Individual> myMembers;
    private Collection<Mass> myMasses;
    private Random myGen;
    
    public Assembly()
    {
        super("", null, 0);
        myMembers = new ArrayList<Individual>();
        myMasses = new ArrayList<Mass>();
        myGen = new Random();
    }
    
    public Assembly(String name, Collection<Behavior> behaviors, int id)
    {
        super(name, behaviors, id);
        myMembers = new ArrayList<Individual>();
        myMasses = new ArrayList<Mass>();
        myGen = new Random();
    }
    
    public Assembly(Assembly a)
    {
        super(a.getName(), a.getBehaviors(), a.getID());
        myMembers = new ArrayList<Individual>(a.getMembers());
        myMasses = new ArrayList<Mass>(a.getMasses());
        myGen = new Random();
    }
    
    public boolean addMember (Mass member)
    {
        // TODO add spring between the closest member
        myMembers.add(member);
        return myMasses.add(member);
    }
    
    public Collection<Mass> getMasses()
    {
        return myMasses;
    }
    
    /**
     * Adds a spring between each mass
     */
    public void addAllSprings()
    {
        for (Mass m1: myMasses)
        {
            for (Mass m2: myMasses)
            {
                int length = myGen.nextInt(50) + 100;
                myMembers.add(new Spring(m1, m2, length, 0.0005));
            }
        }
    }
    
    /**
     * Adds a spring between the mouse and the closest mass
     * @param m
     */
    public void addSpringFromMouse(Mass m)
    {
        m.getPreciseVelocity().reset();
        Mass end = getClosestMass(m);
        int distance = (int) m.getCenter().distance(end.getCenter());
        Spring mouseSpring = new Spring(m, end, distance, 0.0005);
        myMembers.add(mouseSpring);
    }
    
    /**
     * returns the closest mass to the parameter
     * @param m
     * @return
     */
    public Mass getClosestMass(Mass m)
    {
        Mass result = ((ArrayList<Mass>) myMasses).get(0);
        double shortest = result.getCenter().distance(m.getCenter());
        for (Mass mass: myMasses)
        {
            double distance = result.getCenter().distance(mass.getCenter());
            if (distance < shortest)
            {
                shortest = distance;
                result = mass;
            }
        }
        return result;
    }

    public void clearMembers ()
    {
        myMembers.clear();
    }


    public Collection<Individual> getMembers ()
    {
        return myMembers;
    }


    public boolean isMember (Individual member)
    {
        return myMembers.contains(member);
    }

    public void paint (Graphics pen)
    {
        for (Individual i: myMembers)
        {
            i.paint(pen);
        }
    }


    public boolean removeMember (Individual member)
    {
        return myMembers.remove(member);
    }

    public void setMembers (Collection<Individual> members)
    {
        myMembers = members;
    }

    public void update (Canvas canvas)
    {
        Dimension size = canvas.getSize();
        for(Individual i: myMembers)
        {
            Mover m = (Mover)i;
            VelocityInfo info = new VelocityInfo();
            info.setSize(size);
            info.setMyMousePosition(new Point(canvas.getMyX(), canvas.getMyY()));
            m.computeNewVelocity(info);
            m.update(canvas);
        }
    }

    @Override
    public boolean addMember (Individual member)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
