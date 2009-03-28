package simulationFramework;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import javax.swing.*;

import old.BouncerFactory;
import old.Canvas;
import old.Factory;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
	private ISimulationModel myModel;
	private Canvas myDisplay;
	private Timer myTimer = new Timer(DEFAULT_DELAY, new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			myDisplay.update();
			myDisplay.repaint();
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
	public void init(Dimension size, Canvas display) {
		myDisplay = display;
//		myModel = model;
		
		// create container to display animations
		makeOutputDisplay(size);

		// create user interface controls
		makeMenus();
		makeControlPanel();
		makeDisplaySettings();
		makeDebugControls();
	}

	private void makeDisplaySettings() {
		JPanel settings = new JPanel();

		JMenuItem addToSimulation = new JMenuItem("Add to Simulation...");
		addToSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doAddToSimulation();
			}
		});
		settings.add(addToSimulation);

		JMenuItem editSettings = new JMenuItem("Settings...");
		editSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doEditSettings();
			}
		});
		settings.add(editSettings);

		getContentPane().add(settings, BorderLayout.EAST);
//		return settings;
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
				myDisplay.clear();
				String key = (String) creatureMenu.getSelectedItem();
				map.get(key).createMovers(myDisplay, movers.getValue(),
						trail.getValue());
				myDisplay.repaint();
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
				myDisplay.update();
				myDisplay.repaint();
				stop();
			}
		});
		simulationControls.add(step);

		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				start();
				myDisplay.clear();
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
		myDisplay = new Canvas(size);
		myDisplay.setPreferredSize(size);
		myDisplay.setSize(size);
		myDisplay.setFocusable(true);
		myDisplay.requestFocus();

		// add our user interface components to applet
		getContentPane().add(myDisplay, BorderLayout.CENTER);
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

		JMenuItem addToSimulation = new JMenuItem("Add to Simulation...");
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
	 */
	private void doAddToSimulation() {
		JDialog dialog = new JDialog();
//	   JDialog dialog = (new JOptionPane()).createDialog("hi");
	   dialog.setModal(true);
	   dialog.setResizable(true);
//	   dialog.setPreferredSize(getSize());
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
				myDisplay.clear();
				String key = (String) creatureMenu.getSelectedItem();
				map.get(key).createMovers(myDisplay, movers.getValue(),
						trail.getValue());
				myDisplay.repaint();
				stop();
			}
		});
		creationPanel.add(createButton);
	   dialog.add(creationPanel, BorderLayout.CENTER);
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
					"http://www.uke.edu/~zy9/cps108/flocks.html"));
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
