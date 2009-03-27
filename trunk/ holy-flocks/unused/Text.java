package unused;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import yourwork.Mover;
import yourwork.PointD;


/**
 * A Mover that represents an Text String
 *
 * @author Robert C Duvall
 */
public class Text extends Mover
{
    // possible font styles
    public static final String FONT_SERIF = "Serif";
    public static final String FONT_SANSSERIF = "SansSerif";
    public static final String FONT_MONOSPACED = "Monospaced";
    
    private String myText;
    private Font myFont;


    /**
     * Construct a shape at the given position, with the given velocity, 
     * size, and color, displaying the given text string.
     */
    public Text (PointD center, Color color, String text)
    {
        super(center, new Dimension(0,0), new PointD (0, 0), color, 0);
        myFont = new Font("Default", Font.BOLD, 14);
        myText = text;
    }


    /**
     * @return the text of this shape
     */
    public String getText ()
    {
        return myText;
    }


    /**
     * Change the text of this shape
     */
    public void setText (String s)
    {
        myText = s;
    }

    
    /**
     * Change the font of this shape's text.
     */
    public void setFont (String type, int size)
    {
        myFont = new Font(type, Font.BOLD, size);
    }

    
    /**
     * Describes how to draw the shape on the screen.
     *
     * @param pen used to paint shape on the screen
     */
    public void paint (Graphics pen)
    {
        // set attributes
        pen.setColor(getColor());
        pen.setFont(myFont);

        // get text size
        TextLayout layout = new TextLayout(myText, myFont,
                                           ((Graphics2D)pen).getFontRenderContext());
        float height = layout.getAscent() + layout.getDescent();
        Rectangle2D bounds = layout.getBounds();
        bounds.setRect(-bounds.getWidth() / 2, -height / 2,
                        bounds.getWidth(), bounds.getHeight());
        setSize((int)bounds.getWidth(), (int)bounds.getHeight());

        // draw text
        layout.draw((Graphics2D)pen,
                    (float)(getCenter().getX() - bounds.getWidth() / 2),
                    (float)(getCenter().getY() + bounds.getHeight() / 2 - layout.getDescent()));
    }
}
