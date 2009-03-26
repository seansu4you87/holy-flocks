package ignorethis;

import java.awt.*;
import javax.swing.ImageIcon;
import yourwork.Mover;
import yourwork.PointD;


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
    public Image (PointD center,
                  Dimension size,
                  PointD velocity,
                  String fileName)
    {
        super(center, size, velocity, Color.BLACK, 0);
        setImage(fileName);
    }


    /**
     * Construct a shape at the given position, with the given size, and 
     * with the image referred to by the given filename.
     */
    public Image (PointD center,
                  Dimension size,
                  String fileName)
    {
        this(center, size, new PointD(0, 0), fileName);
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
                      (int) (getCenter().getX() - getSize().width / 2), 
					  (int) (getCenter().getY() - getSize().height / 2),
                      getSize().width, getSize().height,
                      null);
    }
}
