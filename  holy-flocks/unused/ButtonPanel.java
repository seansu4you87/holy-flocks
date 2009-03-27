package unused;

import javax.swing.JPanel;
import javax.swing.JButton;

import flocks.Canvas;
import flocks.Factory;
import flocks.RangeSlider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The collection of buttons to apply commands to the active canvas.
 * 
 * @author Robert C Duvall
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel
{
    // other user interface components
    private Canvas myView;
    private RangeSlider myNumToCreate;
    private RangeSlider myTrailSize;


    /**
     * Create a button panel to act on the given target.
     * 
     * @param target canvas upon which commands act on 
     * @param input component that provides additional input to commands
     */
    public ButtonPanel (Canvas target, RangeSlider numToCreate, RangeSlider trailSize)
    {
        myView = target;
        myNumToCreate = numToCreate;
        myTrailSize = trailSize;
    }

    /**
     * Create a button for the given command.
     * 
     * @param factory command to activate when button is pushed 
     */
    public void add (final Factory factory)
    {
        JButton b = new JButton(factory.getName());
        b.addActionListener(new ActionListener()
            {
                public void actionPerformed (ActionEvent e)
                {
                    myView.clear();
                    factory.createMovers(myView, myNumToCreate.getValue(), myTrailSize.getValue());
                    myView.repaint();
                }
            });
        add(b);
    }
}
