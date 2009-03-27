package flocks;

import java.awt.*;




/**
 * Creates window that can be moved, resized, and closed by the user.
 *
 * @author Robert C. Duvall
 */
public class Main
{
    // constants
    public static final Dimension SIZE = new Dimension(600, 800);
    public static final String TITLE = "Holy Flocks!";


    // main --- where the program starts
    public static void main (String args[])
    {
        SimulationViewer sv = new SimulationViewer(TITLE, SIZE);
        ISimulationModel sm = new FlockModel();
        sv.setModel(sm);
    }
}
