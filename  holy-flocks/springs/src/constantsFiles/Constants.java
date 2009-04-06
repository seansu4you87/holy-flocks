/**
 * @author David Eitel, Eric Wheeler
 */


package constantsFiles;

import java.awt.Dimension;

public interface Constants
{
	// Applet constants for sliders
    public static final String MOVERS_LABEL = "Number of shapes to create";
    public static final int MIN_MOVERS = 1;
    public static final int MAX_MOVERS = 512;
    public static final String TRAIL_LABEL = "Length of trail to display";
    public static final int MIN_TRAIL = 0;
    public static final int MAX_TRAIL = 128;
    public static final String SIZE_LABEL = "Size of Individual";
    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 201;
    public static final String RED_LABEL = "Red Intensity";
    public static final String GREEN_LABEL = "Green Intensity";
    public static final String BLUE_LABEL = "Blue Intensity";
    public static final int MIN_COLOR = 0;
    public static final int MAX_COLOR = 255;
    
    // animate 25 times per second if possible
    public static final int DEFAULT_DELAY = 1000 / 25;  // in milliseconds
    
    
    //formation constants
    public static final String GENERATE = "generate";
    public static final String FORMATION = "Formation";
    public static final String CIRCLE_FORMATION = "Circle";
    public static final String LINE_FORMATION = "Line";
    public static final String RANDOM_FORMATION = "Random";
    public static final String LEFT_SIDE_FORMATION = "LeftSide";
    public static final String QUARTER_FORMATION = "Quarter";
    
    //Behavior control constants
    public static final double FLOCKLET_RADIUS = 100.0;//Double.MAX_VALUE;
    public static final int MAX_SPEED = 12;
    public static final int FLOCK_INDIVIDUAL_RADIUS = 10;
    public static final int RANDOMIZER_TIME = 12;
    
    //Resource control constants and window size
    public static final String RESOURCES = "resources.";
    public static final String LANGUAGE_ENGLISH = "English";
    public static final String DEFAULT_LANGUAGE = LANGUAGE_ENGLISH;
    
    public static final String PARAMETER_WIDTH = "width";
    public static final String PARAMETER_HEIGHT = "width";
    
    //GUI button control method names
    public static final String GUI_QUIT = "quit";
    public static final String GUI_DEBUG = "debug";
    public static final String GUI_STOP = "stop";
    public static final String GUI_STEP = "stepButtonAction";
    public static final String GUI_PAUSE = "pauseButtonAction";
    public static final String GUI_PLAY = "playButtonAction";
    public static final String GUI_CLEAR = "clearButtonAction";
    public static final String GUI_ADD_INDIVIDUAL = "addIndividualToGroup";
    public static final String GUI_ADD_GROUP = "addGroupToGroup";
    
    public static final String GUI_ADD_ASSEMBLY = "addAssembly";
    
    public static final String GUI_MAKE_POINTS = "generateFormation";
    public static final String GUI_TOGGLE_BEHAVIOR = "toggleBehavior";
    public static final Dimension SIZE = new Dimension(600, 800);
    public static final String TITLE = "Flocks";
    
    //font styles
    public static final String FONT_SERIF = "Serif";
    public static final String FONT_SANSSERIF = "SansSerif";
    public static final String FONT_MONOSPACED = "Monospaced";
    public static final String FONT_DEFAULT = "Default";
    
    public static final Object[] DEFAULT_AL_ARGS = null;//AL is actionListener. this is used a default value for addActionListenerForMenuItem
    
    //Constants used for GUI reflection for Behavior checkboxes.
    public static final String ATTRACTION = "Attraction";
    public static final String RACING = "Racing";
    public static final String WALKING = "Walking";
    public static final String ALIGNMENT = "Alignment";
    public static final String COHESION = "Cohesion";
    public static final String COLLISION_AVOIDANCE = "CollisionAvoidance";
    public static final String RANDOM_INTERVAL_VELOCITY = "RandomIntervalVelocity";
    public static final String SPEED_LIMIT = "SpeedLimiter";
    public static final String STANDING_STILL = "StandingStill";
    public static final String SIMPLE_BOUNCE = "SimpleBouncing";
    public static final String ESCAPE_MOUSE = "EscapeTheMouse";
    
    public static final String SPRINGING = "Springing";
    public static final String VISCOSITY = "Viscosity";
    public static final String GRAVITY = "Gravity";
    public static final String WALL_BOUNCE = "WallBounce";
    public static final String WRAP_AROUND = "WrapAround";
    
    //Tree node names
    public static final String PARENT = "Parent ";
    public static final String GROUP = "Group ";
    public static final String INDIVIDUAL = "Individual ";
    
}
