package flocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import old.Mover;
import simulationFramework.ISimulationComponent;
import simulationFramework.ISimulationModel;
import simulationFramework.PointD;

/**
 * models behavior of a flock keeps track of all members of the flock
 * updates/paints the flock
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
public class Flock implements ISimulationComponent {
	private List<String> myBehaviors;
	private List<FlockMember> myMembers;
	private String myName;

	public Flock(String name, List<String> behaviors) {
		myMembers = new ArrayList<FlockMember>();
		myBehaviors = behaviors;
		myName = name;
	}

	public void add(ISimulationComponent component) {
		myMembers.add((FlockMember) component);
	}

	@Override
	public PointD getCenter() {
		PointD center = new PointD(0, 0);
		for (FlockMember member : myMembers) {
			center.translate(member.getCenter());
		}
		center.setLocation(center.getX() / myMembers.size(), center.getY()
				/ myMembers.size());
		return center;
	}

	@Override
	public Color getColor() {
		return myMembers.get(0).getColor();
	}

	public List<FlockMember> getMembers() {
		return myMembers;
	}

	public String getName() {
		return myName;
	}

	@Override
	public PointD getVelocity() {
		PointD velocity = new PointD(0, 0);
		for (FlockMember member : myMembers) {
			velocity.translate(member.getCenter());
		}
		velocity.setLocation(velocity.getX() / myMembers.size(), velocity
				.getY()
				/ myMembers.size());
		return velocity;
	}

	@Override
	public void paint(Graphics pen) {
		for (FlockMember member : myMembers) {
			member.paint(pen);
		}
	}

	public void remove(ISimulationComponent component) {
		myMembers.remove((FlockMember) component);
	}

	@Override
	public void setColor(Color color) {
		for (FlockMember member : myMembers) {
			member.setColor(color);
		}
	}

	public void setName(String name) {
		myName = name;
	}

	@Override
	public void update(ISimulationModel model) {
		double totalX = 0;
		double totalY = 0;
		for (FlockMember member : myMembers) {
			if (myBehaviors.contains("align"))
				FlockBehavior.align(this, member);
			if (myBehaviors.contains("cohere"))
				FlockBehavior.cohere(this, member);
			if (myBehaviors.contains("separate"))
				FlockBehavior.separate(this, member);
			totalX += member.getVelocity().getX();
			totalY += member.getVelocity().getX();
		}
		// System.out.println("X: " + (int)totalX + " Y: " + (int) totalY);
	}
}
