package guiAndAbstracts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import behaviorTypes.Behavior;

/**
 * This is the most basic element for an object that appears on the canvas.
 *
 */
public abstract class Individual
{
	protected String myName;
	protected Collection<Behavior> myBehaviors;
	protected int myID;
	protected boolean myDebugFlag;
	
	public Individual(String name, Collection<Behavior> behaviors, int id)
	{
		myName = name;
		myBehaviors = behaviors;
		myID = id;
		myDebugFlag = false;
	}
	
	/**
	 * Every individual has a name
	 * @return name of an individual
	 */
	public String getName()
	{
		return myName;
	}
	
	/**
	 * Every individual in the program has a uniqueid, like every Duke student.
	 * @return
	 */
	public int getID()
	{
		return myID;
	}
	
	/**
	 * All individuals have behaviors.
	 * @return a Collection of these Behaviors
	 */
	public Collection<Behavior> getBehaviors()
	{
		return myBehaviors;
	}
	
    /**
     * The default toString method returns the name of an individual.
     */
	public String toString()
	{
		return myName;
	}
	
	
	public boolean isDebugFlag()
	{
		return myDebugFlag;
	}

	public void setDebugFlag(boolean debugFlag)
	{
		myDebugFlag = debugFlag;
	}
	
	public abstract void paint (Graphics pen);
	public abstract void update (Canvas canvas);
}