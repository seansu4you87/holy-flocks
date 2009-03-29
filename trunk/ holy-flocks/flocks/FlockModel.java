package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

import old.FlockOld;
import old.Mover;

import simulationFramework.ISimulationComponent;
import simulationFramework.ISimulationModel;
import simulationFramework.Applet;
import simulationFramework.SimulationViewer;

/**
 * models behavior of flocks in simulation -- evolved from canvas of screesavers
 * 
 * initializes/saves world to .flock file; adds/removes flocks from world;
 * clears simulation model; updates/paints world
 * 
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
@SuppressWarnings("serial")
public class FlockModel extends JComponent implements ISimulationModel {
	private ISimulationComponent myCurrent;

	// things to be animated
	private List<ISimulationComponent> myComponents;
	// additional state for adding and removing of shapes during animation
	private List<ISimulationComponent> myComponentsToRemove;
	// like a dice, generates a series of random numbers
	private Random myGenerator;
	private ListIterator<ISimulationComponent> myIterator;
	private SimulationViewer myView;

	public FlockModel(Dimension size) {
		// distinguish canvas from the rest of the window
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		// initialize animation state
		myGenerator = new Random();
		myComponents = new ArrayList<ISimulationComponent>();
		myComponentsToRemove = new ArrayList<ISimulationComponent>();
		myIterator = myComponents.listIterator();
		myComponents.add(new FlockMember("hi"));
	}

	@Override
	public void add(ISimulationComponent component) {
		if (myIterator == null)
			myComponents.add(component);
		else
			myIterator.add(component);
	}

	@Override
	public void clear() {
		if (myIterator == null)
			myComponents.clear();
		else
			myComponents.addAll(myComponents);
		myComponents.clear();
	}

	@Override
	public void initialize(Scanner s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics pen) {
		// distinguish canvas from the rest of the window
		pen.setColor(Color.WHITE);
		pen.fillRect(0, 0, getSize().width, getSize().height);
		// paint shapes to be animated
		for (ISimulationComponent f : myComponents) {
			f.paint(pen);
		}
	}

	@Override
	public void remove(ISimulationComponent component) {
		if (myIterator == null)
			myComponents.remove(component);
		else if (myCurrent == component)
			myIterator.remove();
		else
			myComponentsToRemove.add(component);
	}

	@Override
	public void setView(SimulationViewer view) {
		myView = view;
	}

	@Override
	public void update() {
		for (ISimulationComponent f : myComponents) {
			f.update(this);
		}
		// animate each mover, taking care to add or remove new ones
		// appropriately
		myIterator = myComponents.listIterator();
		while (myIterator.hasNext()) {
			myCurrent = myIterator.next();
			if (myComponentsToRemove.contains(myCurrent)) {
				myComponentsToRemove.remove(myCurrent);
				myIterator.remove();
			} else {
				myCurrent.update(this);
			}
		}
		myIterator = null;
		// clear out updates made during animation
		for (ISimulationComponent current : myComponentsToRemove) {
			myComponents.remove(current);
		}
		myComponentsToRemove.clear();
	}
	
	/**
	 * 
	 */
	public List<ISimulationComponent> getConstituents(){
		List<ISimulationComponent> flocks = new ArrayList<ISimulationComponent>();
		for(ISimulationComponent component : myComponents){
			if(component instanceof Flock){
				flocks.add((Flock) component);
			}
		}
		return flocks;
	}

	@Override
	public void write(FileWriter writer) {
		// TODO Auto-generated method stub

	}
}
