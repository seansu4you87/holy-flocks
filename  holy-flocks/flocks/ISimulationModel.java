package flocks;
import java.io.FileWriter;
import java.util.Scanner;



public interface ISimulationModel {
	 /**
     * Initialize a new game/model from the information in the scanner.
     * @param s 
     */
    public void initialize(Scanner s);
    
    /**
     * Write information in model to file specified in writer so that
     * it can be read back in to play a game. Clients should close the
     * writer when all information is written.
     * @param writer is open/set for writing
     */
    public void write(FileWriter writer);
    
      
    /**
     * Set the model's view.
     * @param view is view for this model
     */
    public void setView(SimulationViewer view);
}
