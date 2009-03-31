package guiAndAbstracts;

import javax.swing.JPanel;
import javax.swing.JButton;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import constantsFiles.*;

/**
 * The collection of buttons to apply commands to the active canvas.
 * 
 * @author Robert C Duvall
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements Constants
{
    // other user interface components
    private Canvas myView;
    private Map<String, RangeSlider> mySliderMap;

    /**
     * Create a button panel to act on the given target.
     * 
     * @param target canvas upon which commands act on 
     * @param input component that provides additional input to commands
     */
    public ButtonPanel (Canvas target, Map<String, RangeSlider> sliderMap)
    {
        myView = target;
        mySliderMap = sliderMap;
    }


    /**
     * Create a button for the given command.
     * 
     * @param factory command to activate when button is pushed 
     */
    public void add (final Factory factory, final Canvas canvas, final Applet parent)
    {
        JButton b = new JButton(factory.getName());
        b.addActionListener(new ActionListener()
            {
                public void actionPerformed (ActionEvent e)
                {
                    myView.clear();
                    SliderProperties information = new SliderProperties(myView);
                    information.setNumberObjects(mySliderMap.get(MOVERS_LABEL).getValue());
                    information.setTrailSize(mySliderMap.get(TRAIL_LABEL).getValue());
                    List<Individual> individuals = factory.createMovers(myView, information);
                    canvas.getGroup().setMembers(individuals);
                    parent.buildObjectTreeList();
                    myView.repaint();
                }
            });
        add(b);
    }
}
