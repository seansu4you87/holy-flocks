package simulationFramework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import javax.swing.*;

import flocks.Flock;
import flocks.FlockFactory;
import flocks.FlockMemberBall;
import flocks.FlockModel;

import old.BouncerFactory;
import old.Canvas;
import old.Factory;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Creates an applet to be viewed over the web.
 * 
 * @author Weiping Zhang
 */
@SuppressWarnings("serial")
public class Applet extends JApplet {
	// constants
	public static final String MOVERS_LABEL = "Number of shapes to create";
	public static final int MIN_MOVERS = 1;
	public static final int MAX_MOVERS = 100;
	public static final String TRAIL_LABEL = "Length of trail to display";
	public static final int MIN_TRAIL = 0;
	public static final int MAX_TRAIL = 10;
	// animate 25 times per second if possible
	public static final int DEFAULT_DELAY = 1000 / 25; // in milliseconds

	// state
	private FlockModel myModel;
//	private Canvas myDisplay;
	private Timer myTimer = new Timer(DEFAULT_DELAY, new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
//			myDisplay.update();
//			myDisplay.repaint();
			myModel.update();
			myModel.repaint();
		}
	});
	private JFileChooser myChooser = new JFileChooser(System
			.getProperty("user.dir"));

	// /**
	// * Initializes the applet --- called by the browser.
	// */
	// public void init()
	// {
	// // create container to display animations
	// init(new Dimension(Integer.parseInt(getParameter("width")),
	// Integer.parseInt(getParameter("height"))));
	// }

	/**
	 * Initializes the applet --- called by main.
	 */
	public void init(Dimension size, Canvas display, ISimulationModel model) {
//		myDisplay = display;
		myModel = (FlockModel)model;
		
		// create container to display animations
		makeOutputDisplay(size);

		// create user interface controls
		makeMenus();
		makeControlPanel();
		makeDebugControls();
	}

	/**
	 * Create Panel to give extra information on flock members Meant for
	 * debugging purposes
	 */
	private void makeDebugControls() {
		JPanel creationPanel = new JPanel();

		final Map<String, Factory> map = new HashMap<String, Factory>();
		map.put("Circles", new BouncerFactory());
		final JComboBox creatureMenu = new JComboBox(map.keySet().toArray());
		creationPanel.add(creatureMenu);

		final RangeSlider movers = new RangeSlider(MOVERS_LABEL, MIN_MOVERS,
				MAX_MOVERS);
		creationPanel.add(movers);

		final RangeSlider trail = new RangeSlider(TRAIL_LABEL, MIN_TRAIL,
				MAX_TRAIL);
		creationPanel.add(trail);

		JButton createButton = new JButton("Create Flock");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				myDisplay.clear();
				myModel.clear();
				String key = (String) creatureMenu.getSelectedItem();
//				map.get(key).createMovers(myDisplay, movers.getValue(),
//						trail.getValue());
//				myDisplay.repaint();
				myModel.repaint();
				stop();
			}
		});
		creationPanel.add(createButton);

		// add our user interface components to applet
		getContentPane().add(creationPanel, BorderLayout.SOUTH);
	}

	/**
	 * Create button panel to control simulation Buttons: start, stop, step,
	 * clear
	 */
	private void makeControlPanel() {
		JPanel simulationControls = new JPanel();

		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				start();
			}
		});
		simulationControls.add(start);

		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
		});
		simulationControls.add(stop);

		JButton step = new JButton("Step");
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				myDisplay.update();
//				myDisplay.repaint();
				myModel.update();
				myModel.repaint();
				stop();
			}
		});
		simulationControls.add(step);

		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				start();
//				myDisplay.clear();
				myModel.clear();
			}
		});
		simulationControls.add(clear);

		// add our user interface components to applet
		getContentPane().add(simulationControls, BorderLayout.NORTH);
	}

	/**
	 * Creates center canvas for simulation
	 * 
	 * @param size
	 */
	private void makeOutputDisplay(Dimension size) {
		myModel = new FlockModel(size);
		myModel.setPreferredSize(size);
		myModel.setSize(size);
		myModel.setFocusable(true);
		myModel.requestFocus();
//		myDisplay = new Canvas(size);
//		myDisplay.setPreferredSize(size);
//		myDisplay.setSize(size);
//		myDisplay.setFocusable(true);
//		myDisplay.requestFocus();

		// add our user interface components to applet
//		getContentPane().add(myDisplay, BorderLayout.CENTER);
//	}
		getContentPane().add(myModel, BorderLayout.CENTER);
	}

	/**
	 * Creates menu bar for applet
	 */
	private void makeMenus() {
		JMenuBar mb = new JMenuBar();
		mb.add(makeFileMenu());
		mb.add(makeSimulationMenu());
		setJMenuBar(mb);
	}

	/**
	 * Creates flock menu containing buttons to add to flocks or edit flocks
	 * 
	 * @return JMenu
	 */
	private JMenu makeSimulationMenu() {
		JMenu simulation = new JMenu("Simulation");

		JMenuItem addToSimulation = new JMenuItem("Create New Flock...");
		addToSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doAddToSimulation();
			}
		});
		simulation.add(addToSimulation);

		JMenuItem editSettings = new JMenuItem("Settings...");
		editSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doEditSettings();
			}
		});
		simulation.add(editSettings);

		return simulation;
	}

	/**
	 * Pop-up menu dialogue to edit existing flocks and their members;
	 * add/remove existing members from flocks; set flocking behaviors
	 */
	private void doEditSettings() {
		// TODO

	}

	/**
	 * Pop-up menu dialogue to create new flock members; Either add to existing
	 * flock or create new flock; Specify color and shape of members to be
	 * added;
	 * 
	 * made public so that helper methods have access to text area/combo box values
	 * 
	 */
	private void doAddToSimulation() {
		final JDialog dialog = new JDialog();
		dialog.setTitle("Add New Flock to Simulation");
		dialog.setModal(true);
		dialog.setResizable(true);
		
		List<Integer> numbers = new ArrayList<Integer>();
		for(int k=0; k<=255; k++){
			numbers.add(k);
		}
		
		JPanel optionsPanel = new JPanel();
		
		final JTextArea promptNumber = new JTextArea("Number of Members: ");
		promptNumber.setEditable(false);
		optionsPanel.add(promptNumber);
		
		final JComboBox numberToAdd = new JComboBox(numbers.subList(1, 101).toArray());
		optionsPanel.add(numberToAdd);
		
		final JTextArea promptTrail = new JTextArea("Length of Trail: ");
		promptTrail.setEditable(false);
		optionsPanel.add(promptTrail);
		
		final JComboBox trailToAdd = new JComboBox(numbers.subList(1, 11).toArray());
		optionsPanel.add(trailToAdd);		
		
		final JTextArea promptShape = new JTextArea("Shape of Members: ");
		promptShape.setEditable(false);
		optionsPanel.add(promptShape);
		
		String[] shapes = {"Cricle", "Smiley"};
		final JComboBox shapeToAdd = new JComboBox(shapes);
		optionsPanel.add(shapeToAdd);
		
		final JTextArea promptColor = new JTextArea("Color of Members: ");
		promptColor.setEditable(false);
		optionsPanel.add(promptColor);
		
		final JComboBox colorToAdd = new JComboBox(numbers.subList(0, 256).toArray());
		optionsPanel.add(colorToAdd);
		
		JPanel namePanel = new JPanel();
		
		JTextArea promptName = new JTextArea("Enter Name of New Flock: ");
		promptName.setEditable(false);
		namePanel.add(promptName);
		
		final JTextField flockName = new JTextField(20);
		namePanel.add(flockName);

		JPanel buttonPanel = new JPanel();
		JButton addToExistingFlock = new JButton("Add");
		addToExistingFlock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String name = flockName.getText();
				int number = (Integer) numberToAdd.getSelectedItem();
				String shape = (String) shapeToAdd.getSelectedItem();
				int color = (Integer) colorToAdd.getSelectedItem();
				int trailSize = (Integer) trailToAdd.getSelectedItem();
				myModel.clear();
				FlockFactory.createFlockMembers(myModel, name, number, shape, color, trailSize);
				((Component) myModel).repaint();
				dialog.dispose();
			}

		});
		buttonPanel.add(addToExistingFlock);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		buttonPanel.add(cancel);

		dialog.add(namePanel, BorderLayout.NORTH);
	   dialog.add(optionsPanel, BorderLayout.CENTER);
	   dialog.add(buttonPanel, BorderLayout.SOUTH);

	   dialog.pack();
	   dialog.setVisible(true);

		
	}


	

	/**
	 * Creates file menu containing buttons to open/save files, help
	 * documentations, and exit program
	 * 
	 * @return JMenu
	 */
	private JMenu makeFileMenu() {
		JMenu file = new JMenu("File");

		JMenuItem open = new JMenuItem("Open File...");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doOpen();
			}
		});
		file.add(open);

		JMenuItem save = new JMenuItem("Save As...");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doSave();
			}
		});
		file.add(save);

		file.addSeparator();

		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doHelp();
			}
		});
		file.add(help);

		file.addSeparator();

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		file.add(exit);

		return file;
	}

	/**
	 * Displays program documentations
	 */
	private void doHelp() {
		JFrame frame = new JFrame("Help");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setSize(new Dimension(500, 500));

		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		try {
			editorPane.setPage(new URL(
					"/www.duke.edu/~zy9/cps108/flocks.html"));
			JScrollPane scrollPane = new JScrollPane(editorPane);
			frame.add(scrollPane);
			frame.pack();
			frame.setVisible(true);
		} catch (IOException e) {
			showError("Attempted to read a bad URL: ");
		}
		
	}

	/**
	 * Save current simulation settings to file
	 */
	private void doSave() {
		if (myChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = myChooser.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file);
				// TODO
//				myModel.write(fw);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open a file containing simulation settings, load settings to simulation
	 */
	private void doOpen() {
		if (myChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = null;
			// TODO
//			try {
//				file = myChooser.getSelectedFile();
//				myModel.initialize(new Scanner(file));
//			} catch (FileNotFoundException e) {
//				showError("Could not open " + file.getName());
//			}
		}
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * Starts the applet's action, i.e., starts the animation.
	 */
	public void start() {
		// start the animation
		myTimer.start();
	}

	/**
	 * Stops the applet's action, i.e., the animation.
	 */
	public void stop() {
		// stop the animation
		myTimer.stop();
	}
}
