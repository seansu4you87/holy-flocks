package creators;

import java.applet.Applet;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import behaviorTypes.Behavior;
import constantsFiles.Constants;
import guiAndAbstracts.Canvas;
import guiAndAbstracts.Individual;


/**
 * General implementation of individual specific to individuals that can move on screen.
 * 
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class Mover extends Individual implements Constants
{
    // state
	protected Point myCenter;
	protected Point myVelocity;
    protected Dimension mySize;
    protected Color myColor;
    private int myMaxTrail;
    private List<Point> myTrail;
    protected List<Behavior> myBehaviors;
    private boolean myIsPainting;


    /**
     * Construct a shape at the given position, with the given velocity, 
     * size, and color.  Also a size for its trail (if implemented).
     *
     * @param center position of the shape
     * @param size dimensions of the shape
     * @param velocity speed and direction of the shape
     * @param color color of the shape
     * @param trailSize number of lines to draw following the shape
     */
    public Mover (Point center, Dimension size, Point velocity, Color color, int trailSize, String name, Collection<Behavior> behaviors, int id)
    {
        super(name, behaviors, id);
    	myCenter = new Point(center.x, center.y);
        mySize = new Dimension(size.width, size.height);
        myVelocity = new Point(velocity.x, velocity.y);
        myColor = color;
        
        myMaxTrail = trailSize;
        myTrail = new LinkedList<Point>();
        myTrail.add(new Point(myCenter));
        myBehaviors = new ArrayList<Behavior>();
        myIsPainting = false;
    }


    /**
     * Describes how to "animate" the shape by changing its state.
     *
     * Currently, moves by the current velocity.
     *
     * @param bounds which encloses this shape
     */
    public void update (Canvas canvas)
    {
        myCenter.translate(myVelocity.x, myVelocity.y);
        myTrail.add(new Point(myCenter));
        if (myTrail.size() >= myMaxTrail)
        {
            myTrail.remove(0);
        }
    }


    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draw a trail of the shape's previous positions if it exists.
     * plus a rectangle if we are simply adding individuals or groups to the screen.
     * 
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        if (myMaxTrail > 0)
        {
            drawTrail(pen);
        }
        if(myDebugFlag)
        {
        	debugPaint(pen);
        }
        if(myIsPainting)
        {
        	pen.fillRect(myCenter.x - mySize.width / 2,
                    myCenter.y - mySize.height / 2,
                    mySize.width, mySize.height);
        }
    }
    
    /**
	*	Prints out debug information for a given Mover.
	*/
    public void debugPaint(Graphics pen)
    {
    	pen.drawString("Center: (" + myCenter.x +", "+myCenter.y + ")", myCenter.x + 20, myCenter.y + 20);
        pen.drawString("Velocity: (" + myVelocity.x + ", " +myVelocity.y + ")", myCenter.x + 20, myCenter.y + 10);
        pen.drawString("Size: (" + mySize.height + ", " + mySize.width + ")", myCenter.x + 20, myCenter.y);
    }


    /**
     * Reports shape's center.
     *
     * @return center of shape
     */
    public Point getCenter ()
    {
        return myCenter;
    }

    /**
     * Reports shape's size.
     *
     * @return size of shape
     */
    public Dimension getSize ()
    {
        return mySize;
    }

    /**
     * Changes shape's size.
     */
    public void setSize (int width, int height)
    {
        mySize = new Dimension(width, height);
    }

	/**
     * Reports shape's velocity.
     *
     * @return velocity of shape
     */
    public Point getVelocity ()
    {
        return myVelocity;
    }

    /**
     * Reports shape's color.
     *
     * @return color of shape
     */
    public Color getColor ()
    {
        return myColor;
    }

    /**
     * Changes shape's color.
     */
    public void setColor (Color c)
    {
        myColor = c;
    }

    
    /**
     * Draws a trail connecting the shape's previous positions.
     * 
     * @param pen used to paint shape on the screen
     */
    private void drawTrail (Graphics pen)
    {
        pen.setColor(myColor);
        // draw trail
        Point previous = myTrail.get(0);
        for (int k = 1; k < myTrail.size(); k++)
        {
            Point current = myTrail.get(k);
            pen.drawLine(previous.x, previous.y, current.x, current.y);
            previous = current;
        }
    }
    
    /**
     * Turns a point provided to the method into a point
     * corresponding to a "unit vector".  Used to normalize
     * points representing velocities.
     * 
     * @param p  velocity to be turned into unit vector
     * @return   unit vector point
     */
    public Point normalize(Point p)
    {
    	double length = Math.sqrt(p.getX()*p.getX() + p.getY()*p.getY());
    	if (length != 0)
    	{
    	p.x = (int) Math.round(p.getX()/length);
    	p.y = (int) Math.round(p.getY()/length);
    	}
    	else
    	{
    		p.x = 0;
    		p.y = 0;
    	}
    	return p;
    }
    
    /**
     * Executes each behavior in the mover's behavior list and wraps the coordinates
     * back onto the canvas if the object ran off of it.
     * 
     * @param v information class used to hold pieces of information relevant for
     * 			calculating velocities including canvas size and mouse position.
     */
    public void computeNewVelocity(VelocityInfo v)
    {
    	for(Behavior b : myBehaviors)
    	{
    			b.computeNewVelocity(this, v);
    	}
    	//default wrap
    	myCenter.x = wrapCoordinate(myCenter.x, v.getSize().width);
    	myCenter.y = wrapCoordinate(myCenter.y, v.getSize().width);
    }
    
    /**
     * Takes a mover that has moved off the canvas and wraps its center around 
     * so that they are always visible to the user.
     * 
     * @param value   current center position
     * @param bounds  maximum width or height of canvas
     * @return   new center position
     */
    public int wrapCoordinate (int value, int bounds)
    {
        return (value + bounds) % bounds;
    }
    
    /**
     * Takes current velocity and limits it to a set constant value in both
     * the positive and negative directions.
     * 
     * @param value  current velocity vector length
     * @param max  maximum allowable speed
     * @return  new speed after limiting
     */
    public int limitSpeed (int value, int max)
    {
    	if (value > MAX_SPEED)
        {
            value = MAX_SPEED;
        }
        else if (value < -1*MAX_SPEED)
        {
            value = -1*MAX_SPEED;
        }
        return value;
    }
    
    /**
     * Replaces movers behavior list with given list.
     * Allows dynamic updating of mover behavior.
     * 
     * @param behaviors  list of new behaviors for this mover.
     */
	public void setBehaviors(List<Behavior> behaviors) 
	{
		this.myBehaviors = behaviors;
	}
	
	/**
	 * Sets a boolean that controls whether to use the mover paint method
	 * or allow the subclasses to handle it.
	 * 
	 * @param isPainting boolean representing whether to paint an image at this "mover"
	 * 						level or allow subclasses to paint what they wish
	 */
	public void setIsPainting(boolean isPainting) 
	{
		this.myIsPainting = isPainting;
	}
	
	/**
     * Returns shape's left-most coordinate.
     */
    public int getLeft ()
    {
        return getCenter().x - getSize().width / 2;
    }

    
    /**
     * Returns shape's top-most coordinate.
     */
    public int getTop ()
    {
        return getCenter().y - getSize().height / 2;
    }


    /**
     * Returns shape's right-most coordinate.
     */
    public int getRight ()
    {
        return getCenter().x + getSize().width / 2;
    }


    /**
     * Reports shape's bottom-most coordinate.
     *
     * @return bottom-most coordinate
     */
    public int getBottom ()
    {
        return getCenter().y + getSize().height / 2;
    }
    
    public void setVelocity(Point velocity)
    {
        myVelocity = new Point(velocity.x, velocity.y);
    }
    
    public void setVelocity(int x, int y)
    {
        setVelocity(new Point(x, y));
    }
    
    public void setCenter(Point center)
    {
        myCenter = new Point(center.x, center.y);
    }
    
    public void setCenter(int x, int y)
    {
        setCenter(new Point(x, y));
    }
}
