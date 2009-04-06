package guiAndAbstracts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JOptionPane;

import behaviorTypes.Behavior;

import reflection.Reflection;

import creators.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import constantsFiles.*;

/**
 * Creates an applet that be viewed over the web.
 *
 *
 *Added Create Springs and Add Assembly buttons.  Added method addAssembly
 * @author David Eitel, Eric Wheeler, Robert C. Duvall, Sean Yu, Weiping Zhang
 */
@SuppressWarnings("serial")
public class Applet extends JApplet implements Constants
{
    // state
    private Canvas myDisplay;
    private Timer myTimer;
    private boolean myIsPlaying;
	private ResourceBundle myResources;
	private DynamicTree myTree;
	private int myCurrentID;//current uniqueid - keep track of this
	private String myFormation;
	private List<Behavior> myBehaviors;
	private Collection<Point> myPointList;
	private int myCurrentSelection; //current item that is selected on the sidebar tree.
	private Map<String, RangeSlider> mySliderMap;
	
    /**
     * Initializes the applet --- called by the browser.
     */
    public void init ()
    {
        // create container to display animations
    	myIsPlaying = true;
    	myCurrentID = 1;
    	myCurrentSelection = 0;
    	myResources = ResourceBundle.getBundle(RESOURCES + DEFAULT_LANGUAGE);
    	myTree = new DynamicTree();
    	myIsPlaying = true;
    	myBehaviors = new ArrayList<Behavior>();
        init(new Dimension(Integer.parseInt(getParameter(PARAMETER_WIDTH)),
                           Integer.parseInt(getParameter(PARAMETER_HEIGHT))));
        mySliderMap = new HashMap<String, RangeSlider>();
        myBehaviors = new ArrayList<Behavior>();
        myPointList = new ArrayList<Point>();
    }


    /**
     * Initializes the applet --- called by main.
     */
    public void init (Dimension size)
    {
    	myCurrentSelection = 0;
    	myCurrentID = 1;
    	myBehaviors = new ArrayList<Behavior>();
    	myResources = ResourceBundle.getBundle(RESOURCES + DEFAULT_LANGUAGE);
    	setLookAndFeel();
    	// create container to display animations
        myDisplay = new Canvas(size);
        myDisplay.setPreferredSize(size);
        myDisplay.setSize(size);
        myDisplay.setFocusable(true);
        myDisplay.requestFocus();
        myResources = ResourceBundle.getBundle(RESOURCES + DEFAULT_LANGUAGE);
        mySliderMap = new HashMap<String, RangeSlider>();
        myBehaviors = new ArrayList<Behavior>();
        myPointList = new ArrayList<Point>();
        myFormation = RANDOM_FORMATION;
        
        //create sliders
        JPanel sliders = makeSliders();
        
        // add commands to test here
        ButtonPanel commands = makeCommandButtons();
        
        ButtonPanel utility = makeUtilityButtons();
        //adding utility buttons here
        
        
        //Creating the menu bar for quitting, choosing behaviors and selecting initial formations.
        JMenuBar menuBar = makeMenuBar();

        //Create tree representing Groups on screen
    	initializeTree();
        
        // add our user interface components to applet
        JPanel panelNorthMain = new JPanel();
        JPanel panelNorth1 = new JPanel(new FlowLayout(FlowLayout.LEFT));//help from here: http://www.coderanch.com/t/340114/Swing-AWT-SWT-JFace/java/Left-align-Jpanel-or-JTabbedPane
        JPanel panelNorth2 = new JPanel();
        panelNorth2.setLayout(new BoxLayout(panelNorth2, BoxLayout.PAGE_AXIS));
        panelNorthMain.setLayout(new BoxLayout(panelNorthMain, BoxLayout.PAGE_AXIS));
        panelNorth1.add(menuBar);
        panelNorth2.add(commands);
        panelNorth2.add(utility);
        panelNorthMain.add(panelNorth1);
        panelNorthMain.add(panelNorth2);
        
        getContentPane().add(panelNorthMain, BorderLayout.NORTH);
        getContentPane().add(myDisplay, BorderLayout.CENTER);
        getContentPane().add(sliders, BorderLayout.SOUTH);
        getContentPane().add(myTree, BorderLayout.WEST);        
    }
    
    /**
     * Makes the sliders
     * @returns a JPanel to be added to the contentpane.
     */
    public JPanel makeSliders()
    {
    	// create user interface controls
        mySliderMap.put(MOVERS_LABEL, new RangeSlider(MOVERS_LABEL, MIN_MOVERS, MAX_MOVERS));
        mySliderMap.put(TRAIL_LABEL, new RangeSlider(TRAIL_LABEL, MIN_TRAIL, MAX_TRAIL));
        mySliderMap.put(SIZE_LABEL, new RangeSlider(SIZE_LABEL, MIN_SIZE, MAX_SIZE));
        mySliderMap.put(RED_LABEL, new RangeSlider(RED_LABEL, MIN_COLOR, MAX_COLOR));
        mySliderMap.put(GREEN_LABEL, new RangeSlider(GREEN_LABEL, MIN_COLOR, MAX_COLOR));
        mySliderMap.put(BLUE_LABEL, new RangeSlider(BLUE_LABEL, MIN_COLOR, MAX_COLOR));
        JPanel sliders = new JPanel();
        for(String s: mySliderMap.keySet())
        {
        	sliders.add(mySliderMap.get(s));
        }
        return sliders;
    }
    
    public ButtonPanel makeCommandButtons()
    {
    	ButtonPanel commands = new ButtonPanel(myDisplay, mySliderMap);
        commands.add(new BouncerFactory(myResources.getString("MakeBouncer")), myDisplay, this);
        commands.add(new RacerFactory(myResources.getString("MakeRacer")), myDisplay, this);
        commands.add(new WalkerFactory(myResources.getString("MakeWalker")), myDisplay, this);
        commands.add(new AttractorFactory(myResources.getString("MakeAttractor")), myDisplay, this);
        commands.add(new SwarmFactory(myResources.getString("MakeSwarm")), myDisplay, this);
        commands.add(new FlockMemberFactory(myResources.getString("MakeFlock")), myDisplay, this);
        commands.add(new SpringFactory(myResources.getString("MakeSpring")), myDisplay, this);
        return commands;
    }
    
    
    public ButtonPanel makeUtilityButtons()
    {
    	//add individual
    	ButtonPanel utility = new ButtonPanel(myDisplay, mySliderMap);
    	
    	JButton addAssembly = new JButton(myResources.getString("MakeAssembly"));
    	//need to define GUI_ADD_ASSEMBLY
    	addActionListenerForMenuItem(addAssembly, this, GUI_ADD_ASSEMBLY, DEFAULT_AL_ARGS);
        JButton addIndividual = new JButton(myResources.getString("MakeIndividual"));
        addActionListenerForMenuItem(addIndividual, this, GUI_ADD_INDIVIDUAL, DEFAULT_AL_ARGS);        
        
        //make group
        JButton addGroup = new JButton(myResources.getString("MakeGroup"));
        addActionListenerForMenuItem(addGroup, this, GUI_ADD_GROUP, DEFAULT_AL_ARGS);        
        
        //clear
        JButton clear = new JButton(myResources.getString("Clear"));
        addActionListenerForMenuItem(clear, this, GUI_CLEAR, DEFAULT_AL_ARGS);
        utility.add(addAssembly);
        utility.add(addIndividual);
        utility.add(addGroup);
        utility.add(clear);
        
        //Play button
        JButton play = new JButton(myResources.getString("Play"));
        addActionListenerForMenuItem(play, this, GUI_PLAY, DEFAULT_AL_ARGS);
        utility.add(play);
        
        //pause button
        JButton pause = new JButton(myResources.getString("Pause"));
        addActionListenerForMenuItem(pause, this, GUI_PAUSE, DEFAULT_AL_ARGS);
        utility.add(pause);
        
        //step button
        JButton step = new JButton(myResources.getString("Step"));
        addActionListenerForMenuItem(step, this, GUI_STEP, DEFAULT_AL_ARGS);
        utility.add(step);
        
        //Toggle DEBUG button
        JButton toggleDebug = new JButton(myResources.getString("Debug"));
        addActionListenerForMenuItem(toggleDebug, this, GUI_DEBUG, DEFAULT_AL_ARGS);
        utility.add(toggleDebug);
        return utility;
    }
    
    public JMenuBar makeMenuBar()
    {
    	JMenuBar menuBar = new JMenuBar();
    	menuBar.add(makeFileMenu());
    	menuBar.add(makeBehaviorMenu());
    	menuBar.add(makeFormationsMenu());
    	return menuBar;
    }

	/**
     * makes file menu
     * @return JMenu with file options.
     */
    public JMenu makeFileMenu()
    {
    	JMenu file = new JMenu(myResources.getString("FileMenu"));
    	JMenuItem quit = new JMenuItem(myResources.getString("QuitCommand"));
    	addActionListenerForMenuItem(quit, this, GUI_QUIT, DEFAULT_AL_ARGS);
        file.add(quit);
        return file;
    }
    
    /**
     * Creates the formations JMenu
     * @return JMenu of formation options
     */
    public JMenu makeFormationsMenu()
    {
    	JMenu formationTypes = new JMenu(myResources.getString("FormationMenu"));
    	
    	//Create Radio Button grouping for initial sprite formation and add it to menu.
        ButtonGroup formationGroup = new ButtonGroup();
        JRadioButton random = new JRadioButton(myResources.getString("RandomFormation"));
        random.setSelected(true);
        generateFormation(mySliderMap, myFormation);
        addActionListenerForMenuItem(random, this, GUI_MAKE_POINTS, mySliderMap, RANDOM_FORMATION);
        JRadioButton circle = new JRadioButton(myResources.getString("CircleFormation"));
        addActionListenerForMenuItem(circle, this, GUI_MAKE_POINTS, mySliderMap, CIRCLE_FORMATION);
        JRadioButton rightquarter = new JRadioButton(myResources.getString("QuarterFormation"));
        addActionListenerForMenuItem(rightquarter, this, GUI_MAKE_POINTS, mySliderMap, QUARTER_FORMATION);
        JRadioButton leftwall = new JRadioButton(myResources.getString("RacingFormation"));
        addActionListenerForMenuItem(leftwall, this, GUI_MAKE_POINTS, mySliderMap, LEFT_SIDE_FORMATION);
        JRadioButton diagonal = new JRadioButton(myResources.getString("WalkingFormation"));
        addActionListenerForMenuItem(diagonal, this, GUI_MAKE_POINTS, mySliderMap, LINE_FORMATION);
        ArrayList<JRadioButton> radioList = new ArrayList<JRadioButton>();
        radioList.add(random);
        radioList.add(circle);
        radioList.add(rightquarter);
        radioList.add(leftwall);
        radioList.add(diagonal);
        //inefficient need to refactor
        for(JRadioButton j : radioList)
        {
        	j.setBackground(null);
        	formationGroup.add(j);
        	formationTypes.add(j);
        }
        return formationTypes;
    }
    
    /**
     * Creates JMenu for behavior options
     * @return JMenu with different behavior choices.
     */
    public JMenu makeBehaviorMenu()
    {
    	JMenu behavior = new JMenu(myResources.getString("BehaviorMenu"));
    	
    	//Create behavior check boxes.
        ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
        JCheckBox attraction = new JCheckBox(myResources.getString("AttractingBehavior"));
        addActionListenerForMenuItem(attraction, this, GUI_TOGGLE_BEHAVIOR, ATTRACTION);
        boxList.add(attraction);
        JCheckBox racing = new JCheckBox(myResources.getString("RacingBehavior"));
        addActionListenerForMenuItem(racing, this, GUI_TOGGLE_BEHAVIOR, RACING);
        boxList.add(racing);
        JCheckBox walking = new JCheckBox(myResources.getString("WalkingBehavior"));
        addActionListenerForMenuItem(walking, this, GUI_TOGGLE_BEHAVIOR, WALKING);
        boxList.add(walking);
        JCheckBox alignment = new JCheckBox(myResources.getString("AlignmentBehavior"));
        addActionListenerForMenuItem(alignment, this, GUI_TOGGLE_BEHAVIOR, ALIGNMENT);
        boxList.add(alignment);
        JCheckBox cohesion = new JCheckBox(myResources.getString("CohesionBehavior"));
        addActionListenerForMenuItem(cohesion, this, GUI_TOGGLE_BEHAVIOR, COHESION);
        boxList.add(cohesion);
        JCheckBox collisionAvoid = new JCheckBox(myResources.getString("CollisionAvoidBehavior"));
        addActionListenerForMenuItem(collisionAvoid, this, GUI_TOGGLE_BEHAVIOR, COLLISION_AVOIDANCE);
        boxList.add(collisionAvoid);
        JCheckBox randomChanges = new JCheckBox(myResources.getString("RandomChanges"));
        addActionListenerForMenuItem(randomChanges, this, GUI_TOGGLE_BEHAVIOR, RANDOM_INTERVAL_VELOCITY);
        boxList.add(randomChanges);
        JCheckBox speedLimit = new JCheckBox(myResources.getString("SpeedLimiterBehavior"));
        addActionListenerForMenuItem(speedLimit, this, GUI_TOGGLE_BEHAVIOR, SPEED_LIMIT);
        boxList.add(speedLimit);
        JCheckBox standStill = new JCheckBox(myResources.getString("StandingStillBehavior"));
        addActionListenerForMenuItem(standStill, this, GUI_TOGGLE_BEHAVIOR, STANDING_STILL);
        boxList.add(standStill);
        JCheckBox bounce = new JCheckBox(myResources.getString("SimpleBounceBehavior"));
        addActionListenerForMenuItem(bounce, this, GUI_TOGGLE_BEHAVIOR, SIMPLE_BOUNCE);
        boxList.add(bounce);
        JCheckBox escape = new JCheckBox(myResources.getString("EscapeMouseBehavior"));
        addActionListenerForMenuItem(escape, this, GUI_TOGGLE_BEHAVIOR, ESCAPE_MOUSE);
        boxList.add(escape);
        JCheckBox viscosity = new JCheckBox(myResources.getString("ViscocityBehavior"));
        addActionListenerForMenuItem(viscosity, this, GUI_TOGGLE_BEHAVIOR, VISCOSITY);
        boxList.add(viscosity);
        JCheckBox gravity = new JCheckBox(myResources.getString("GravityBehavior"));
        addActionListenerForMenuItem(gravity, this, GUI_TOGGLE_BEHAVIOR, GRAVITY);
        boxList.add(gravity);
        JCheckBox wallBounce = new JCheckBox(myResources.getString("WallBouncingBehavior"));
        addActionListenerForMenuItem(wallBounce, this, GUI_TOGGLE_BEHAVIOR, WALL_BOUNCE);
        boxList.add(wallBounce);
        //JCheckBox wrapAround = new JCheckBox(myResources.getString("WrapAroundBehavior"));
        //addActionListenerForMenuItem(wrapAround, this, GUI_TOGGLE_BEHAVIOR, WRAP_AROUND);
        //boxList.add(wrapAround);
        
        for(JCheckBox j: boxList)
        {
        	j.setBackground(null);
        	behavior.add(j);
        }
        return behavior;
    }   
    
    /**
     * Initializes the Dynamic tree
     */
    public void initializeTree()
    {
    	myTree = new DynamicTree();
        myTree.addTreeSelectionListener(new Selector());
        buildObjectTreeList();
    }
    
    
    /**
     * Sets the look-and-feel of a program to match that of the host system.
     * In other words, the program will not look like a Java applet on a Windows system.
     * It will look more like a normal Windows application.
     */
    public void setLookAndFeel()
    {
    	try
        {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        	JFrame.setDefaultLookAndFeelDecorated(true);
        	
        }
        catch(Exception e)
        {
        	//Reference: http://www.java2s.com/Code/Java/Swing-JFC/Errormessagedialog.htm
        	JOptionPane.showMessageDialog(new JFrame(), myResources.getString("ErrorLookAndFeel"), myResources.getString("Error"),
        	        JOptionPane.ERROR_MESSAGE);  	
        }
    }
    
    /**
     * Creates and adds actionListeners to menu items (anything that extends AbstractButton).
     * This method exists remove a lot of in-line ActionListener classes.
     * @param item: Item that we are adding an actionListener to.
     * @param parent: parent class (Applet in this case) where the button behavior methods are located.
     * @param args: arguments (if any) to pass to the button behavior method. 
     */
    public void addActionListenerForMenuItem(AbstractButton item, final Object parent, final String methodName, final Object ... args)
    {
    	item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(args == null)
                	Reflection.callMethod(parent, methodName);
                else
                	Reflection.callMethod(parent, methodName, args);
            }
        });
    }
    
    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    public void start ()
    {
        // create a timer to animate the canvas
        myTimer = new Timer(DEFAULT_DELAY, 
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    updateDisplay();
                }
            });
        // start the animation
        myTimer.start();
        myIsPlaying = true;
    }
    
    /**
     * Updates the canvas.
     */
    public void updateDisplay()
    {
    	myDisplay.update();
        myDisplay.repaint();
    }
    
    /**
     * Tells the animation to start playing.
     * This method is called by the actionListener for the play button.
     */
    public void playButtonAction()
    {
    	myTimer.start();
    	myIsPlaying = true;
    }
    
    /**
     * Tells the animation to pause.
     * This method is called by the actionListener for the pause button.
     */
    public void pauseButtonAction()
    {
    	myTimer.stop();
    	myIsPlaying = false;
    }
    
    /**
     * Tells the animation to advance one frame forward.
     * This method is called by the actionListener for the step button.
     */
    public void stepButtonAction()
    {
    	if(myIsPlaying)
    		pauseButtonAction();
    	updateDisplay();
    }
    /**
     * Stops the applet's action, i.e., the animation.
     */
    public void stop ()
    {
        // stop the animation
        myTimer.start();
    }
    
    /**
     * Quits the program
     * Called by quit option in the JMenuBar
     */
    public void quit()
    {
    	System.exit(0);
    }
    
    /**
     * Clears all items from the canvas.
     */
    public void clearButtonAction()
    {
    	myDisplay.removeAllSprites();
    	buildObjectTreeList();
    	stepButtonAction();
    }
    
    
    
    /**
     * This is a helper method for building the Tree of individuals that are present on the canvas.
     * @param list of individuals to add
     * @param nodeID of the root node.
     */
    public void buildObjectTreeListHelper(Collection<Individual> list, int nodeID)
    {
    	DefaultMutableTreeNode parent = new DefaultMutableTreeNode(GROUP + nodeID);
    	parent = myTree.addObject(null, PARENT + nodeID, true);
    	for(Individual i: list)
    	{
    		if(i instanceof Flock)
    		{
    			buildObjectTreeListHelper(((Flock) i).getMembers(), i.getID());
    		}
    		else
    		{
    			myTree.addObject(parent, INDIVIDUAL + i.getID());
    		}
    	}
    }
    
    /**
     * Rebuilds the Tree of objects on the canvas.
     */
    public void buildObjectTreeList()
    {
    	myTree.clear();    	
    	buildObjectTreeListHelper(myDisplay.getGroup().getMembers(),0);
    	myTree.setMinimumSize(new Dimension(200,500));
    	myTree.myTreeModel.reload();
    }
    
  
    /**
     * Adds an individual to the group that is currently selected in the sidebar tree.
     * If a group is not selected, this method does nothing.
     */
    public void addIndividualToGroup()
    {
    	Flock f = new Flock();
    	Individual i = findIndividual(myCurrentSelection, myDisplay.getGroup().getMembers(), f.getClass());
    	if(i==null && myDisplay.getGroup().getID()==myCurrentSelection)
    		i = myDisplay.getGroup();
    	if (i instanceof Flock)
    	{
    		((Flock)i).addMember(createCustomMover());
    	}
    	buildObjectTreeList();//this must be done last
    }
    
    /**
     * Creates a mover individual based on information from the JMenuBar and sliders.
     * @return Mover individual.
     */
    public Mover createCustomMover()
    {
    	Mover m = new Mover(new Point(0,0), new Dimension(mySliderMap.get(SIZE_LABEL).getValue(),mySliderMap.get(SIZE_LABEL).getValue()), 
				new Point(10,10), new Color(mySliderMap.get(RED_LABEL).getValue(),
						mySliderMap.get(GREEN_LABEL).getValue(),
						mySliderMap.get(BLUE_LABEL).getValue()), mySliderMap.get(TRAIL_LABEL).getValue(), "", null, myCurrentID);//myTree.getNumNodes()+1);
    	m.setBehaviors(myBehaviors);
    	m.setIsPainting(true);
    	myCurrentID++;
		return m;
    }
    
    
    /**
     * Adds assembly of masses and springs based on information from the JMenuBar and sliders.
     */
    public void addAssembly()
    {
        Assembly parent = new Assembly("", null, myCurrentID);
        myCurrentID++;
        
        Iterator<Point> iter = myPointList.iterator();
        int numberOfItems = mySliderMap.get(MOVERS_LABEL).getValue() / 50;
        for (int i = 0; i < numberOfItems; i ++)
        {
            Mass m = new Mass(iter.next(), new Dimension(mySliderMap.get(SIZE_LABEL).getValue(), mySliderMap.get(SIZE_LABEL).getValue()),
                              myDisplay.nextIntInRange(0, 10), null, myCurrentID);
            m.setIsPainting(true);
            m.setBehaviors(myBehaviors);
            parent.addMember(m);
            myCurrentID++;
        }
        parent.addAllSprings();
        myDisplay.getGroup().addMember(parent);
        buildObjectTreeList();
    }
    
    /**
     * Creates a group of individuals based on information from the JMenuBar and sliders.
     */
    public void addGroupToGroup()
    {
    	Flock parent = new Flock("",null, myCurrentID);
    	myCurrentID++;
    	Iterator<Point> iter = myPointList.iterator();
    	while(iter.hasNext())
    	{
    		Mover m = new Mover(iter.next(), new Dimension(mySliderMap.get(SIZE_LABEL).getValue(),mySliderMap.get(SIZE_LABEL).getValue()), 
    						new Point(10,10), new Color(mySliderMap.get(RED_LABEL).getValue(),
    						mySliderMap.get(GREEN_LABEL).getValue(),
    						mySliderMap.get(BLUE_LABEL).getValue()), mySliderMap.get(TRAIL_LABEL).getValue(), "", null, myCurrentID);//myTree.getNumNodes()+1);
        	m.setBehaviors(myBehaviors);
        	m.setIsPainting(true);
        	parent.addMember(m);
        	myCurrentID++;
    	}
    	myDisplay.getGroup().addMember(parent);
    	buildObjectTreeList();//this must be done last
    }
    /**
     * Method caused by clicking the toggleDebug button. Sets the canvas debug flag to on so that
     * debug strings will be printed next to sprites on the canvas.
     */
    public void debug()
    {
    	myDisplay.setMyIsDebug(!myDisplay.isMyIsDebug());
    }
    /**
     * Reflection method used to add or subtract an instance of a behavior represented by a string
     * from the class behavior list.
     * @param behaviorName
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void toggleBehavior(String behaviorName) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
    	ArrayList<Behavior> listChanger = new ArrayList<Behavior>();
    	String s = "behaviorTypes." + behaviorName;
    	Class c = Class.forName(s);
		for(Behavior b: myBehaviors)
		{
			if(!(c.isInstance(b)))
    		{
    			listChanger.add(b);
    		}
		}
		
		if(listChanger.size() == myBehaviors.size())
		{
			listChanger.add((Behavior) c.newInstance());
		}
		myBehaviors = listChanger;
    }
    /**
     * Method used to generate a list of points in a formation via reflection.
     * 
     * @param sliderMap set of all sliders in GUI pane
     * @param formationSetting string representing the current state of the radio button representing
     * 			formation.
     */
	public void generateFormation(Map<String, RangeSlider> sliderMap, String formationSetting)
    {
		myFormation = formationSetting;
    	myPointList =  (List<Point>) Reflection.callMethod(this, GENERATE+myFormation+FORMATION, sliderMap);
    }
    /**
     * Generates points randomly over the entire canvas.
     * Points used to instantiate groups.
     * @param sliderMap set of all sliders in GUI pane
     * @return list of points representing sprite centers
     */
    public List<Point> generateRandomFormation(Map<String, RangeSlider> sliderMap)
    {
    	List<Point> moverPositions = new ArrayList<Point>();
    	for(int n=0; n<sliderMap.get(MOVERS_LABEL).getValue(); n++)
    	{
    		moverPositions.add(new Point(myDisplay.nextIntInRange(0, myDisplay.getBounds().width),
    				myDisplay.nextIntInRange(0, myDisplay.getBounds().width)));
    	}
    	return moverPositions;
    }
    /**
     * Method creates a list of points at equal separation along a circle centered
     * at the center of the applet canvas. Points used to instantiate a group.
     * @param sliderMap set of all sliders in GUI pane
     * @return list of points representing sprite centers
     */
    public List<Point> generateCircleFormation(Map<String, RangeSlider> sliderMap)
    {
    	List<Point> moverPositions = new ArrayList<Point>();
    	Point center = new Point(myDisplay.getBounds().width / 2, myDisplay.getBounds().height / 2);
    	int radius = Math.min(myDisplay.getBounds().width, myDisplay.getBounds().height) / 2;
    	for (float k = 0; k < 360; k += 360.0f / sliderMap.get(MOVERS_LABEL).getValue())
    	{
    		moverPositions.add(new Point((int)(center.x + Math.cos(Math.toRadians(k)) * radius),
                    (int)(center.y + Math.sin(Math.toRadians(k)) * radius)));
    	}
    	return moverPositions;
    }
    /**
     * Method creates a list of points at equal separation along the left-right diagonal
     * of the applet canvas.  Points used to instantiate a group.
     * @param sliderMap set of all sliders in GUI pane
     * @return list of points representing sprite centers
     */
    public List<Point> generateLineFormation(Map<String, RangeSlider> sliderMap)
    {
    	List<Point> moverPositions = new ArrayList<Point>();
    	int numberItems = sliderMap.get(MOVERS_LABEL).getValue();
    	for(int n=0; n<numberItems; n++)
    	{
    		moverPositions.add(new Point((int)((n + 0.5) * myDisplay.getWidth()/numberItems),
                    (int)((n + 0.5) * myDisplay.getHeight()/numberItems)));
    	}
    	return moverPositions;
    }
    /**
     * Method creates a list of points at equal separation along the left wall
     * of the applet canvas.  Points used to instantiate a group.
     * 
     * @param sliderMap set of all sliders in GUI pane 
     * @return list of points at which to place a group
     */
    public List<Point> generateLeftSideFormation(Map<String, RangeSlider> sliderMap)
    {
    	List<Point> moverPositions = new ArrayList<Point>();
    	int numberItems = sliderMap.get(MOVERS_LABEL).getValue();
    	for(int n=0; n<numberItems; n++)
    	{
    		moverPositions.add(new Point(0, (int)((n + 0.5) * myDisplay.getHeight()/numberItems)));
    	}
    	return moverPositions;
    }
    /**
     * Method creates a list of points at random separation in the bottom right corner
     * of the applet canvas.  Points used to instantiate a group.
     * 
     * @param sliderMap set of all sliders in GUI pane 
     * @return list of points at which to place a group
     */
    public List<Point> generateQuarterFormation(Map<String, RangeSlider> sliderMap)
    {
    	List<Point> moverPositions = new ArrayList<Point>();
    	int numberItems = sliderMap.get(MOVERS_LABEL).getValue();
    	for(int n=0; n<numberItems; n++)
    	{
    		moverPositions.add(new Point(myDisplay.nextIntInRange(myDisplay.getWidth() / 2, myDisplay.getWidth()),
                    myDisplay.nextIntInRange(myDisplay.getHeight() / 2, myDisplay.getHeight())));
    	}
    	return moverPositions;
    }
    
    /**
     * Selections are in the form "WORD NUMBER"
     * Example: "Individual 103" returns 103, "Super Big Flock 432" returns 432
     * This method returns that number as an integer.
     * @param selection is the string name of an item selected from the tree.
     * @return number from selection
     */
    public int getIDfromSelection(String selection)
    {
    	int i = selection.lastIndexOf(" ");
    	if(i == -1)
    		return -1;
    	return Integer.parseInt(selection.substring(i+1));
    }
    
    /**
     * Searches through a list of individuals that may or may not
     * contain flocks, and finds an Individual with a specific id and is of type clss
     * @param id of individual to find.
     * @param type of Individual to find.
     * @param individuals is a collection of individuals
     * @return the Individual if it is found, otherwise null.
     */
    public Individual findIndividual(int id, Collection<Individual> individuals, Class<?> clss)
    {
    	for (Individual i: individuals)
    	{
    		if (i.getID()==id && Reflection.isInstance(clss, i))
    		{
    			return i;
    		}
    		else if (i instanceof Flock)
    		{
    			Individual i2 =  findIndividual(id, ((Flock) i).getMembers(), clss);
    			if(i2!=null && Reflection.isInstance(clss, i))
    				return i2;
    		}
    	}
    	return null;
    }
    
    /**
     * Adapted from here: http://www.java2s.com/Tutorial/Java/0240__Swing/JTreegetSelectionModelsetSelectionModeTreeSelectionModelSINGLETREESELECTION.htm
     * This private class is used as a custom TreeSelectionListener for the sidebar tree.
     * This listener is able to obtain the current item that is selected on the tree.
     */
    private class Selector implements TreeSelectionListener
    {
        public void valueChanged(TreeSelectionEvent event)
        {
	          //event.getNewLeadSelectionPath() is null after double-clicking a Node and then pressing Add Individual
        	  //Java seems to make two calls to this Listener when we do the above actions.
        	  //The first call is null and the second call behaves correctly.
        	  //Since two calls are made and the correct action is performed, there is no error output on the exception.
	          try
	          {
	        	  String selection = event.getNewLeadSelectionPath().getLastPathComponent().toString();
	        	  myCurrentSelection = getIDfromSelection(selection);
	          }
	          catch(NullPointerException e)
	          {}
        }
    }
    
}
