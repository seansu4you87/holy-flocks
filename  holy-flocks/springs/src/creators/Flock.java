package creators;
import guiAndAbstracts.Canvas;
import guiAndAbstracts.Individual;

import java.util.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import behaviorTypes.Behavior;
import constantsFiles.*;
import guiAndAbstracts.*;
/**
 * General construct representing a group of moving objects on a canvas.
 * Flocks can contain any type of Individual (even other Flocks).
 * 
 * @author David Eitel, Eric Wheeler
 */



public class Flock extends Group implements Constants
{
	private Collection<Individual> myMembers;
	
	/**
	 * Creates new Flock Object defaulting to no name, no behaviors, and a zero ID.
	 */
	public Flock()
	{
		super("", null, 0);
		myMembers = new ArrayList<Individual>();
	}
	
	/**
	 * Creates new Flock Object with concretely set name, id number, and behavior set.
	 * @param name   name of this object
	 * @param behaviors  set of behaviors one would like to have the flock store (usually provided by
	 * 					GUI interface check boxes.
	 * @param id  unique ID number for this individual
	 */
	public Flock(String name, Collection<Behavior> behaviors, int id)
	{
		super(name, behaviors, id);
		myMembers = new ArrayList<Individual>();
	}
	
	/**
	 * Creates a new Flock that is a deep copy of another flock used in the constructor.
	 * 
	 * @param f   Flock to be copied.
	 */	
	public Flock(Flock f)
	{
		super(f.getName(), f.getBehaviors(), f.getID());
		myMembers = new ArrayList<Individual>(f.getMembers());
	}
	
	/**
	 * Checks to see if an individual is part of this flock.  Useful for
	 * methods that want a flock member only to interact with his fellow flock.
	 * 
	 */
	public boolean isMember(Individual member)
	{
		return myMembers.contains(member);
	}
	
	/**
	 * Adds a member to this flock.  Can also  be another flock.
	 */
	public boolean addMember(Individual member)
	{
		return myMembers.add(member);
	}
	
	/**
	 * Replaces current member set with the passed in member set.
	 * 
	 */
	public void setMembers(Collection<Individual> members)
	{
		myMembers = members;
	}
	
	/**
	 * Takes an individual out of the flock member collection.
	 */
	public boolean removeMember(Individual member)
	{
		return myMembers.remove(member);
	}

	/**
	 * Gets the average location of flock members in a specified radius.
	 * Used for "Center Seeking" Cohesion behavior.
	 * 
	 * @param mover  flock member you want to find average location for
	 * @return Point representing the average position of all individuals in this flock within
	 * 			FLOCKLET_RADIUS of the given mover
	 */
	public Point getAverageLocation(Mover mover)
	{
		Point averageLocation = new Point(0,0);
		List<Mover> adjacentMembers = this.getAdjacentMembers(mover, FLOCKLET_RADIUS);
		for(Mover m: adjacentMembers)
		{
			averageLocation.x += m.getCenter().getX();
			averageLocation.y += m.getCenter().getY();
		}
		averageLocation.x /= adjacentMembers.size();
		averageLocation.y /= adjacentMembers.size();
		return averageLocation;
	}
	
	/**
	 * Gets average direction, specifically the average of all the velocity vectors
	 * of the members of a flock within FLOCKLET_RADIUS of the given individual.
	 * Used for Alignment Behavior.
	 * @param mover   individual you want to provide average velocity vector
	 * @return   point representing average x and y velocities of local flock members
	 */
	public Point getAverageDirection(Mover mover)
	{
		Point averageDirection = new Point(0,0);
		List<Mover> adjacentMembers = this.getAdjacentMembers(mover, FLOCKLET_RADIUS);
		adjacentMembers.remove(mover);
		if(adjacentMembers.size() != 0)
		{
			for(Mover m: adjacentMembers)
			{
				averageDirection.x += m.getVelocity().getX();
				averageDirection.y += m.getVelocity().getY();
			}
			averageDirection.x /= adjacentMembers.size();
			averageDirection.y /= adjacentMembers.size();
		}
		
		return averageDirection;
	}
	
	//TODO: use java reflection for dynamic function calling
	public Point getPointAverage()
	{
		Point average = new Point();
		for(Individual i: myMembers)
		{
			Mover m = (Mover)i;
			average.x += m.getVelocity().x;
			average.y += m.getVelocity().y;
		}
		average.x /= myMembers.size();
		average.y /= myMembers.size();
		return average;
	}
	
	/**
	 * Returns current member set for this flock.
	 */
	public Collection<Individual> getMembers()
	{
		return myMembers;
	}
	
	/**
	 * Changes member list to the given list.
	 * @param members collection flock will point to instead of its current member set
	 */
	public void setMemberList(Collection<Individual> members)
	{
		myMembers = members;
	}
	
	/**
	 * Removes all members of this flock.
	 */
	public void clearMembers()
	{
		myMembers.clear();
	}
	
	/**
	 * Gets all members within a specific distance of the provided flock member.
	 * @param flocklet  center of adjacency map
	 * @param radius  distance within which method searches for other flock members
	 * @return list of movers found within radius distance of mover flocklet
	 */
	public List<Mover> getAdjacentMembers(Mover flocklet, double radius)
	{
		List<Mover> adjacentMembers = new ArrayList<Mover>();
		for(Individual i: myMembers)
		{
			
			if(i instanceof Mover)
			{
				Mover m = (Mover)i;
				if(m.getCenter().distance(flocklet.getCenter()) < radius)
				{
					adjacentMembers.add(m);
				}
			}
			else
			{
				adjacentMembers.addAll(((Flock)i).getAdjacentMembers(flocklet, radius));
			}
		}
		return adjacentMembers;
	}
	
	/**
	 * Calls the update method and computeNewVelocity method for each of the
	 * members of this flock.  Used to animate and update movement information
	 * for everything in the canvas.
	 */
	public void update(Canvas canvas)
	{
		Dimension size = canvas.getSize();
		for(Individual i: myMembers)
		{
			if(i instanceof Mover)
			{
				Mover m = (Mover)i;
				VelocityInfo info = new VelocityInfo();
				info.setFlock(new Flock(this));
				info.setSize(size);
				info.setMyMousePosition(new Point(canvas.getMyX(), canvas.getMyY()));
				m.computeNewVelocity(info);
				m.update(canvas);
			}
			else //must be a flock
			{
				((Flock)i).update(canvas);
			}
		}
	}
	
	/**
	 * Calls paint method for all members of this flock.
	 * Used to visualize all movers on canvas.
	 */
	public void paint(Graphics pen)
	{
		for(Individual i: myMembers)
		{
			i.paint(pen);
		}
	}
	
}
