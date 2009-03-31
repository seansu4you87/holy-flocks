package guiAndAbstracts;

import java.awt.*;
import javax.swing.ImageIcon;

import creators.Flock;
import creators.Mover;
import creators.VelocityInfo;
import behaviorTypes.*;
import java.util.*;

/**
 * A Mover that represents an Image.
 *
 * Only the following formats are supported: png, jpg, gif.
 *
 * @author Robert C Duvall
 */
public class Image extends Mover
{
    private java.awt.Image myImage;

    
    /**
     * Construct a shape at the given position, with the given size and
     * velocity, and with the image referred to by the given filename.
     */
    public Image (Point center,
                  Dimension size,
                  Point velocity,
                  String fileName,
                  Collection<Behavior> behaviors,
                  int id
    			)
    {
        super(center, size, velocity, Color.BLACK, 0, fileName, behaviors, id);
        setImage(fileName);
    }


    /**
     * Construct a shape at the given position, with the given size, and 
     * with the image referred to by the given filename.
     */
    public Image (Point center,
                  Dimension size,
                  String fileName,
                  Collection<Behavior> behaviors,
                  int id
                  )
    {
        this(center, size, new Point(0, 0), fileName, behaviors, id);
    }


    /**
     * Set this shape's image to the image referred to by the given filename.
     */
    public void setImage (String fileName)
    {
        myImage = new ImageIcon(getClass().getResource("/" + fileName)).getImage();
    }


    /**
     * Describes how to draw the shape on the screen.
     *
     * Currently, draws the shape as an image.
     */
    public void paint (Graphics pen)
    {
        pen.drawImage(myImage,
                      getCenter().x - getSize().width / 2, 
					  getCenter().y - getSize().height / 2,
                      getSize().width, getSize().height,
                      null);
    }
    //TODO: convert
    public void computeNewVelocity(VelocityInfo v)
	{}
}
