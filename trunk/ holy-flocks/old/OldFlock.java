package old;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import simulationFramework.ISimulationModel;
import simulationFramework.Applet;
import simulationFramework.PointD;

/**
 * creates a flock and models flock behavior
 * 
 * @author Weiping Zhang
 * 
 */
public class OldFlock {

	private int mySize; // # of members in flock
	private List<Mover> myMembers; // keeps track of members within flock
	private Random rand = new Random();

	/**
	 * 
	 * 
	 */
	public OldFlock() {
		myMembers = new ArrayList<Mover>();
		mySize = myMembers.size();
	}

	public OldFlock(ArrayList<Mover> movers) {
		myMembers = movers;
		mySize = movers.size();
	}

	/*
	 * adds member to the flock.
	 */
	public void add(Mover member) {
		myMembers.add(member);
		mySize++;
	}

	/**
	 * updates behavior of flocks - check if this is the right location
	 */
	public void update() {
		double totalX = 0;
		double totalY = 0;
		for (Mover flockMember : myMembers) {
			align(flockMember);
			if (getAnnulus(flockMember, 1.5 * flockMember.getSize().width,
					3 * flockMember.getSize().width).size() > 0)
				cohere(flockMember);
			if (getNeighbors(flockMember, 1.5 * flockMember.getSize().width)
					.size() > 1)
				separate(flockMember);
			totalX += flockMember.getVelocity().getX();
			totalY += flockMember.getVelocity().getX();
		}
		// System.out.println("X: " + (int)totalX + " Y: " + (int) totalY);
	}

	public void separate(Mover member) {
		double radius = 1.5 * member.getSize().width;
		ArrayList<Mover> neighbors = getNeighbors(member, radius);
		double size = (double) neighbors.size();
		// ArrayList<Point> distances = new ArrayList<Point>();
		double totalXVelocity = 0;
		double totalYVelocity = 0;

		for (Mover neighbor : neighbors) {
			double x = neighbor.getCenter().getX() - member.getCenter().getX();
			double y = neighbor.getCenter().getY() - member.getCenter().getY();
			// distances.add(new Point(x, y));
			totalXVelocity += x;
			totalYVelocity += y;
		}
		double xVelocity = -1 * totalXVelocity / size;
		double yVelocity = -1 * totalYVelocity / size;
		PointD velocity = member.getVelocity();
		member.setVelocity(new PointD(0.99 * velocity.getX() + 0.5 * xVelocity,
				0.99 * velocity.getY() + 0.5 * yVelocity));
	}

	public void align(Mover member) {
		double totalXVelocity = 0;
		double totalYVelocity = 0;
		int radius = 3 * member.getSize().width;
		ArrayList<Mover> neighbors = getNeighbors(member, radius);
		double size = (double) neighbors.size();
		if (size > 1) {
			for (Mover neighbor : neighbors) {
				totalXVelocity += neighbor.getVelocity().getX();
				totalYVelocity += neighbor.getVelocity().getY();
			}
			double xVelocity = totalXVelocity / size + rand.nextDouble()
					- rand.nextDouble();
			double yVelocity = totalYVelocity / size + rand.nextDouble()
					- rand.nextDouble();
			PointD velocity = member.getVelocity();
			if (xVelocity == 0) {
				System.out.println("x is zero");
				xVelocity = velocity.getX() + rand.nextDouble()
						- rand.nextDouble();
			}
			if (yVelocity == 0) {
				System.out.println("y is zero");
				yVelocity = velocity.getY() + rand.nextDouble()
						- rand.nextDouble();
			}
			member
					.setVelocity(new PointD((xVelocity + member.getVelocity()
							.getX()) / 2, (yVelocity + member.getVelocity()
							.getY()) / 2));
		}

	}

	public void cohere(Mover member) {
		double minR = 1.5 * member.getSize().width;
		double maxR = 3 * member.getSize().width;
		ArrayList<Mover> annulus = getAnnulus(member, minR, maxR);
		if (annulus.size() > 0)
			;
		{
			double xTotal = 0;
			double yTotal = 0;
			double size = (double) annulus.size();

			for (Mover neighbor : annulus) {
				xTotal += neighbor.getCenter().getX();
				yTotal += neighbor.getCenter().getY();
			}
			double avgX = xTotal / size;
			double avgY = yTotal / size;
			PointD velocity = member.getVelocity();
			member.setVelocity(new PointD(velocity.getX() + 0.01
					* (avgX - member.getCenter().getX()), velocity.getX()
					+ 0.01 * (avgY - member.getCenter().getY())));
		}
	}

	public ArrayList<Mover> getNeighbors(Mover member, double radius) {
		ArrayList<Mover> neighbors = new ArrayList<Mover>();
		for (Mover flockMember : myMembers) {
			if (member.getCenter().distance(flockMember.getCenter()) <= radius) {
				neighbors.add(flockMember);
			}
		}

		return neighbors;
	}

	public ArrayList<Mover> getAnnulus(Mover member, double minR, double maxR) {

		ArrayList<Mover> minNeighbors = getNeighbors(member, minR);
		ArrayList<Mover> maxNeighbors = getNeighbors(member, maxR);
		ArrayList<Mover> annulus = new ArrayList<Mover>();
		for (Mover neighbor : maxNeighbors) {
			if (!minNeighbors.contains(neighbor)) {
				annulus.add(neighbor);
			}
		}
		return annulus;
	}

	/**
	 * paints flocks to canvas
	 */
	public void paint() {

	}

}
