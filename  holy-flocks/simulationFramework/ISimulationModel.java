package simulationFramework;

import java.awt.Graphics;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
public interface ISimulationModel {
	/**
	 * Remember given mover so it is painted on Canvas.
	 * 
	 * Note, movers are rendered in the order they are added.
	 * 
	 * @param SimulationComponent
	 */
	public void add(ISimulationComponent component);

	/**
	 * Remove all shapes from the canvas.
	 */
	public void clear();

	/**
	 * Initialize a new game/model from the information in the scanner.
	 * 
	 * @param s
	 */
	public void initialize(Scanner s);

	/**
	 * Paint the contents of the canvas.
	 * 
	 * Never called by you directly, instead called by Java runtime when area of
	 * screen covered by this container needs to be displayed (i.e., creation,
	 * uncovering, change in status)
	 * 
	 * @param pen
	 *            used to paint shape on the screen
	 */
	public void paint(Graphics pen);

	/**
	 * Forget given mover so it is not painted on Canvas.
	 * 
	 * @param SimulationComponent
	 */
	public void remove(ISimulationComponent component);

	/**
	 * Set the model's view.
	 * 
	 * @param simulationViewer
	 *            is view for this model
	 */
	public void setView(SimulationViewer simulationViewer);

	/**
	 * Called by each step of timer, multiple times per second.
	 * 
	 * This should update the state of the animated shapes by just a little so
	 * they appear to move over time.
	 */
	public void update();

	/**
	 * Write information in model to file specified in writer so that it can be
	 * read back in to play a game. Clients should close the writer when all
	 * information is written.
	 * 
	 * @param writer
	 *            is open/set for writing
	 */
	public void write(FileWriter writer);
}
