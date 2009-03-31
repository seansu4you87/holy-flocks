package guiAndAbstracts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import creators.*;


/**
 * Animates any number of ovals that start at random positions, moving
 * in random directions, and "bounce" elastically, i.e., as a perfect 
 * reflection, when they hit the boundaries of the window.
 *
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
@SuppressWarnings("serial")
public class Canvas extends JComponent
{
    // things to be animated
    //private List<Mover> myMovers;
		
    private Group myFlock;
	// additional state for adding and removing of shapes during animation
    private List<Individual> myMoversToRemove;
    private ListIterator<Individual> myIterator;
    private Individual myCurrent;
    // like a dice, generates a series of random numbers
    private Random myGenerator;
    
    private boolean myIsDebug;
    private int myX;
    private int myY;
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
        //myMovers = new ArrayList<Mover>();
        myFlock = new Flock("World", null, 0);
        myX=0;
        myY=0;
        myMoversToRemove = new ArrayList<Individual>();
        createMouseMotionListener();
        myIsDebug = false;
    }

    /**
     * Creates a Mouse Listener that keeps track of mouse position on the canvas.
     * Used for EscapeTheMouse behavior and "user interaction" with the program.
     * 
     */
    public void createMouseMotionListener()
    {
    	this.addMouseMotionListener( new MouseMotionListener()
        {
            public void mouseDragged (MouseEvent e)
            {
            	getMousePosition(e);
            }
            public void mouseMoved (MouseEvent e)
            {
            	getMousePosition(e);
            }
            
            private void getMousePosition (MouseEvent e)
            {
            	myX = e.getX();
            	myY = e.getY();
            }
            
        });
    }
    
    public Group getGroup()
    {
    	return myFlock;
    }
    
    public int getMyX() {
		return myX;
	}


	public int getMyY() {
		return myY;
	}
    
    /**
     * Remember given mover so it is painted on Canvas.
     * 
     * Note, movers are rendered in the order they are added.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void add (Individual shape) 
    {
        //if (myIterator == null)  myMovers.add(shape);
    	if (myIterator == null)  myFlock.addMember(shape);
        else                     myIterator.add(shape);
    }
    
    
    /**
     * Forget given mover so it is not painted on Canvas.
     * 
     * @param m mover to be added to those that are drawn
     */
    public void remove (Individual shape)
    {
        //if (myIterator == null)       myMovers.remove(shape);
    	if (myIterator == null)       myFlock.removeMember(shape);
        else if (myCurrent == shape)  myIterator.remove();
        else                          myMoversToRemove.add(shape);
    }
    
    
    /**
     * Remove all shapes from the canvas.
     */
    public void clear ()
    {
        //if (myIterator == null)     myMovers.clear();
    	if (myIterator == null)     myFlock.clearMembers();
        //else                        myMovers.addAll(myMovers);
    	else
    	{
    		Collection<Individual> movers = myFlock.getMembers();
    		movers.addAll(movers);
    		myFlock.setMembers(movers);
    	}
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
        //for (Mover m : myMovers)
        for (Individual m : myFlock.getMembers())
        {
            m.setDebugFlag(myIsDebug);
        	m.paint(pen);
        }
    }


    /**
     * Called by each step of timer, multiple times per second.
     * 
     * This should update the state of the animated shapes by just
     * a little so they appear to move over time.
     */
    
    public void update()
    {
        // animate each mover, taking care to add or remove new ones appropriately
        //myIterator = myMovers.listIterator();
    	myFlock.update(this);//will eventually make all necessary calculations for collision detection and calculates velocity for every flock member based on the 3 rules.
    	myIterator = ((List<Individual>)myFlock.getMembers()).listIterator();
        while (myIterator.hasNext())
        {
            myCurrent = (Individual)myIterator.next();
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
        for (Individual current : myMoversToRemove)
        {
            //myMovers.remove(current);
        	myFlock.removeMember(current);
        }
        myMoversToRemove.clear();        
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
	 * Removes every painted object from the canvas.
	 */
	public void removeAllSprites()
    {
    	myFlock.getMembers().clear();
    }
	
	/**
	 * Returns a debug flag determining whether to paint debug information or not
	 * when painting an object.
	 * 
	 * @return boolean value to pass onto movers to decide whether to paint text or not
	 */
    public boolean isMyIsDebug()
    {
		return myIsDebug;
	}

    /**
     * Sets debug flag that determines whether or not to print debug text.
     * @param isDebug
     */
	public void setMyIsDebug(boolean isDebug)
	{
		myIsDebug = isDebug;
	}

}
