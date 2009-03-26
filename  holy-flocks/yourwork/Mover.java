package yourwork;

import java.awt.*;
import java.util.List;
import java.util.LinkedList;
import ignorethis.Canvas;


/**
 * NAME : 
 * COURSE : CompSci 6
 * PURPOSE : 
 *  Represents a shape that moves on its own
 */
public abstract class Mover
{
    // state
    protected PointD myCenter;
    protected PointD myVelocity;
    protected Dimension mySize;
    protected Color myColor;
    private int myMaxTrail;
    private List<PointD> myTrail;


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
    public Mover (PointD center, Dimension size, PointD velocity, Color color, int trailSize)
    {
        myCenter = new PointD(center.x, center.y);
        mySize = new Dimension(size.width, size.height);
        myVelocity = new PointD(velocity.x, velocity.y);
        myColor = color;
        
        myMaxTrail = trailSize;
        myTrail = new LinkedList<PointD>();
        myTrail.add(new PointD(myCenter));
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
        myTrail.add(new PointD(myCenter));
        if (myTrail.size() >= myMaxTrail)
        {
            myTrail.remove(0);
        }
    }


    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draw a trail of the shape's previous positions if it exists.
     * 
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        if (myMaxTrail > 0)
        {
            drawTrail(pen);
        }
    }


    /**
     * Reports shape's center.
     *
     * @return center of shape
     */
    public PointD getCenter ()
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
     * Changes shape's color.
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
    public PointD getVelocity ()
    {
        return myVelocity;
    }
    
    /**
     * Changes shape's velocity.
     */
    public void setVelocity (PointD velocity)
    {
        myVelocity = new PointD(velocity.x, velocity.y);
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
        PointD previous = myTrail.get(0);
        for (int k = 1; k < myTrail.size(); k++)
        {
            PointD current = myTrail.get(k);
            pen.drawLine((int)previous.x, (int)previous.y, (int)current.x, (int)current.y);
            previous = current;
        }
    }
}
