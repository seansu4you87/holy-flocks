package creators;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import guiAndAbstracts.Canvas;
import guiAndAbstracts.Factory;
import guiAndAbstracts.Individual;
import guiAndAbstracts.SliderProperties;

/**
 *  Creates any number of follower objects that follow a single leader.
 *  
 *  @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class SwarmFactory extends Factory
{
    /**
     * Construct factory.
     */
    public SwarmFactory (String label)
    {
        super(label);
    }


    /**
     * Creates given number of followers within the given canvas.
     *
     * @param canvas within which shapes should appear
     * @param number number of shapes to create
     * @param trailSize number of lines to draw following the shape
     */
    public List<Individual> createMovers (Canvas canvas, SliderProperties information)
    {
    	List<Individual> individualList = new ArrayList<Individual>();
    	int number = information.getNumberObjects(); 
    	int trailSize = information.getTrailSize();
        // current size of canvas
        Dimension bounds = canvas.getSize();
        int size = 25;
        int id = information.getID();
        // create leader that moves randomly around
        Mover leader =
            new SwarmLeader(
                new Point(canvas.getSize().width / 2,
                          canvas.getSize().width / 2),
                new Dimension(size, size),
                new Point(0, 0),
                Color.BLUE,
                trailSize,
                information.getParentName()+"Swarm Leader",
            	information.getBehaviors(),
            	id);
        individualList.add(leader);
        
        // create followers that all follow the first leader
        for (int k = 0; k < number; k++)
        {
            id++;
        	size = 16;
            individualList.add(
                new SwarmFollower(
                    new Point(canvas.nextIntInRange(size / 2, bounds.width - size / 2),
                              canvas.nextIntInRange(size / 2, bounds.width - size / 2)),
                    new Dimension(size, size),
                    new Point(0, 0),
                    Color.GREEN,
                    trailSize,
                    leader,
                    information.getParentName()+k+"",
                	information.getBehaviors(),
                	id
                    ));
        }
        return individualList;
    }
    
}
