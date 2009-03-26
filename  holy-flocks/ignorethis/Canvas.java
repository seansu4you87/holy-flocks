package ignorethis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import yourwork.Flock;
import yourwork.Mover;


/**
 * Animates any number of ovals that start at random positions, moving
 * in random directions, and "bounce" elastically, i.e., as a perfect 
 * reflection, when they hit the boundaries of the window.
 *
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public class Canvas extends JComponent
{
    // things to be animated
    private List<Mover> myMovers;
    private List<Flock> myFlocks;
    // additional state for adding and removing of shapes during animation
    private List<Mover> myMoversToRemove;
    private ListIterator<Mover> myIterator;
    private Mover myCurrent;
    // like a dice, generates a series of random numbers
    private Random myGenerator;


    /**
     * Create a Canvas with the given size.
     * 
     * @param size width and height of canvas in pixels
     */
    public Canvas (Dimension size)
    {
        // distinguish canvas from the rest of the window
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        // initialize animation state
        myGenerator = new Random();
        myMovers = new ArrayList<Mover>();
        myMoversToRemove = new ArrayList<Mover>();
        
        myFlocks = new ArrayList<Flock>();
    }


    /**
     * Remember given mover so it is painted on Canvas.
     * 
     * Note, movers are rendered in the order they are added.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void add (Mover shape) 
    {
        if (myIterator == null)  myMovers.add(shape);
        else                     myIterator.add(shape);
    }
    
    public void add(Flock group)
    {
        myFlocks.add(group);
    }
    
    
    /**
     * Forget given mover so it is not painted on Canvas.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void remove (Mover shape)
    {
        if (myIterator == null)       myMovers.remove(shape);
        else if (myCurrent == shape)  myIterator.remove();
        else                          myMoversToRemove.add(shape);
    }
    
    
    /**
     * Remove all shapes from the canvas.
     */
    public void clear ()
    {
        if (myIterator == null)     myMovers.clear();
        else                        myMovers.addAll(myMovers);
        myFlocks.clear();
    }


    /**
     * Paint the contents of the canvas.
     *
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be 
     * displayed (i.e., creation, uncovering, change in status)
     *
     * @param pen used to paint shape on the screen
     */
    public void paintComponent (Graphics pen)
    {
        // distinguish canvas from the rest of the window
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // paint shapes to be animated
        for (Mover m : myMovers)
        {
            m.paint(pen);
        }
    }


    /**
     * Called by each step of timer, multiple times per second.
     * 
     * This should update the state of the animated shapes by just
     * a little so they appear to move over time.
     */
    public void update ()
    {
        for (Flock myFlock: myFlocks)
        {
            myFlock.update();
        }
        // animate each mover, taking care to add or remove new ones appropriately
        myIterator = myMovers.listIterator();
        while (myIterator.hasNext())
        {
            myCurrent = myIterator.next();
            if (myMoversToRemove.contains(myCurrent))
            {
                myMoversToRemove.remove(myCurrent);
                myIterator.remove();
            }
            else
            {
                myCurrent.update(this);
            }
        }
        myIterator = null;
        // clear out updates made during animation
        for (Mover current : myMoversToRemove)
        {
            myMovers.remove(current);
        }
        myMoversToRemove.clear();

        // BUGBUG: fix for the racer program
        ArrayList<Mover> racers = new ArrayList<Mover>();
        for (Mover m : myMovers)
        {
            if (m instanceof yourwork.Racer)
            {
                racers.add(m);
            }
        }
        if (racers.size() > 0)
        {
            colorLeadingRacer(racers);
        }
    }


    /**
     * Returns a random value between min and max, inclusive
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextIntInRange (int min, int max)
    {
        return myGenerator.nextInt(max - min + 1) + min;
    }


    /**
     * Returns a random value between min and max, inclusive
     * that is guaranteed not to be zero
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextNonZeroIntInRange (int min, int max)
    {
        int result = 0;

        while (result == 0)
        {
            result = nextIntInRange(min, max);
        }

        return result;
    }
    
    
    /**
     * Colors the racer that is farthest ahead RED and all the other
     * racers that are behind BLACK.  If several racers all share the 
     * lead, the one with the least y-value should be colored RED.
     * 
     * @param racers a collection with at least one racer in it
     */
    private void colorLeadingRacer (ArrayList<Mover> racers)
    {
        // can assume that there is at least one racer
        Mover leader = racers.get(0);
        double maxx = leader.getCenter().getX();
        for (Mover r : racers)
        {
            if (r.getCenter().getX() > maxx)
            {
                maxx = r.getCenter().getX();
                leader = r;
            }
            r.setColor(Color.BLACK);
        }
        leader.setColor(Color.RED);
    }
}
