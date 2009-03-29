package simulationFramework;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import old.Canvas;

/**
 * Creates an applet that can be viewed over the web.
 * 
 * @author Weiping Zhang
 */
public class SimulationViewer {
    private ISimulationModel myModel;
    private Canvas myDisplay;

    public SimulationViewer(String title, Dimension size) {
	Applet app = new Applet();
	app.init(size, myDisplay, myModel);
	// app.init(size, myModel);

	// create container that will work with Window manager
	JFrame frame = new JFrame(title);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// add our user interface components to Frame and show it
	frame.getContentPane().add(app, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
    }

    public void setModel(ISimulationModel model) {
	myModel = model;
	myModel.setView(this);
    }
}
