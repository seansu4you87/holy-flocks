package simulationFramework;

import java.awt.*;

import flocks.FlockModel;

/**
 * executable to start the program sets the view and the model
 * 
 * @author Weiping Zhang, Sean Yu
 */
public class SimulationMain {
	// constants
	public static final Dimension SIZE = new Dimension(600, 600);
	public static final String TITLE = "Holy Flocks!";

	// main --- where the program starts
	public static void main(String args[]) {
		SimulationViewer sv = new SimulationViewer(TITLE, SIZE);
		ISimulationModel sm = new FlockModel(SIZE);
		sv.setModel(sm);
	}
}
