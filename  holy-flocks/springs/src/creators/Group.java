package creators;

import guiAndAbstracts.*;

import java.awt.Graphics;
import java.util.*;

import behaviorTypes.Behavior;

/**
 * This is the most basic Group element that holds individuals.
 * All groups inherit properties from Individual.
 */
public abstract class Group extends Individual
{	
	
	public Group(String name, Collection<Behavior> behaviors, int id)
	{
		super(name, behaviors, id);
	}
	
	
	public abstract boolean isMember(Individual member);
	
	public abstract boolean addMember(Individual member);
	
	public abstract boolean removeMember(Individual member);
	
	/**
	 * Returns a collection of members.  However, members do not necessarily need to be
	 * stored in a collection.  We try to give as much flexibility with data structures as is possible.
	 * @return
	 */
	public abstract Collection<Individual> getMembers();
	
	public abstract void setMembers(Collection<Individual> members);
	
	public abstract void clearMembers();
	
	/*
	 * Methods implemented from Individual
	 * 
	 * The implementations will most likely be recursive or iterative in nature, but
	 * we did not define them as such for flexibility.
	 */	
	
	public abstract void paint(Graphics pen);

	public abstract void update(Canvas canvas);
}
