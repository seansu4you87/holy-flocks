package flocks;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import old.Mover;
import simulationFramework.PointD;
import yourwork.Velocity;

/**
 * static methods to determine how flock members behave in a flock
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
public class FlockBehavior {
    private static Random rand = new Random();

	public static void align(Flock flock, FlockMember member) {
		double totalXVelocity = 0;
		double totalYVelocity = 0;
		int radius = 3 * member.getSize().width;
		ArrayList<FlockMember> neighbors = getNeighbors(flock, member, radius);
		double size = (double) neighbors.size();
		if (size > 1) {
			for (FlockMember neighbor : neighbors) {
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

	public static void cohere(Flock flock, FlockMember member) {
		if (getAnnulus(flock, member, 1.5 * member.getSize().width,
				3 * member.getSize().width).size() > 0) {
			double minR = 1.5 * member.getSize().width;
			double maxR = 3 * member.getSize().width;
			ArrayList<FlockMember> annulus = getAnnulus(flock, member, minR,
					maxR);
			if (annulus.size() > 0)
				;
			{
				double xTotal = 0;
				double yTotal = 0;
				double size = (double) annulus.size();

				for (FlockMember neighbor : annulus) {
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
	}

	public static ArrayList<FlockMember> getAnnulus(Flock flock,
			FlockMember member, double minR, double maxR) {

		ArrayList<FlockMember> minNeighbors = getNeighbors(flock, member, minR);
		ArrayList<FlockMember> maxNeighbors = getNeighbors(flock, member, maxR);
		ArrayList<FlockMember> annulus = new ArrayList<FlockMember>();
		for (FlockMember neighbor : maxNeighbors) {
			if (!minNeighbors.contains(neighbor)) {
				annulus.add(neighbor);
			}
		}
		return annulus;
	}

	public static ArrayList<FlockMember> getNeighbors(Flock flock, FlockMember member, double radius) {
		ArrayList<FlockMember> neighbors = new ArrayList<FlockMember>();
		for (FlockMember flockMember : flock.getMembers()) {
			if (member.getCenter().distance(flockMember.getCenter()) <= radius) {
				neighbors.add(flockMember);
			}
		}

		return neighbors;
	}

	public static void separate(Flock flock, FlockMember member) {
		if (getNeighbors(flock, member, 1.5 * member.getSize().width).size() > 1) {
			double radius = 1.5 * member.getSize().width;
			ArrayList<FlockMember> neighbors = getNeighbors(flock, member,
					radius);
			double size = (double) neighbors.size();
			ArrayList<Point> distances = new ArrayList<Point>();
			double totalXVelocity = 0;
			double totalYVelocity = 0;

			for (FlockMember neighbor : neighbors) {
				double x = neighbor.getCenter().getX()
						- member.getCenter().getX();
				double y = neighbor.getCenter().getY()
						- member.getCenter().getY();
				// distances.add(new Point(x, y));
				totalXVelocity += x;
				totalYVelocity += y;
			}
			double xVelocity = -1 * totalXVelocity / size;
			double yVelocity = -1 * totalYVelocity / size;
			PointD velocity = member.getVelocity();
			member.setVelocity(new PointD(0.99 * velocity.getX() + 0.5
					* xVelocity, 0.99 * velocity.getY() + 0.5 * yVelocity));
		}
	}
	
	public static PointD getAveragePosition(List<Mover> movers) {
	        double totalX = 0;
	        double totalY = 0;
	        double total = (double) movers.size();
	        for (Mover shape: movers)
	        {
	            totalX += shape.getCenter().getX();
	            totalY += shape.getCenter().getY();
	        }
	        PointD result = new PointD(totalX / total, totalY / total);
	        return result;
	    }
	
	public static void cohere2(Flock flock, FlockMember, member)
    {
	    ArrayList<FlockMember> myMembers = new ArrayList<FlockMember>();
        TreeMap<Double, FlockMember> distances = new TreeMap<Double, FlockMember>();
        for (int i = 0; i<myMembers.size(); i++)
        {
            Double distance = member.getCenter().distance(myMembers.get(i).getCenter());
            distances.put(distance, myMembers.get(i));
        }
        ArrayList<FlockMember> closestMembers = new ArrayList<FlockMember>();
        int max;
        if (myMembers.size() < 10)
        {
            max = myMembers.size();
        }
        else
        {
            max = 10;
        }
        for (int i = 0; i < max ; i++)
        {
            closestMembers.add(distances.get(distances.firstKey()));
            distances.remove(distances.firstKey());
        }
        PointD averagePosition = getAveragePosition(closestMembers);
        PointD cohereVelocity = member.getCenter().getVelocityVector(averagePosition);
        PointD velocity = member.getVelocity();
        member.setVelocity(new PointD(0.5 * (velocity.getX() + cohereVelocity.getX()), 
                                      0.5 * (velocity.getY() + cohereVelocity.getY())));
    }
}
