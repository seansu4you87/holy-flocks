package flocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import old.Canvas;
import simulationFramework.ISimulationModel;

public class FlockFactory {

	public static Map<String, FlockMember> shapeMap = new HashMap<String, FlockMember>();
	
	public static final List<String> numbers = new ArrayList<String>();
	
//	for(int k=1; k<=100; k++){
//		numbers.add(""+k);
//	}
	
    public static void createFlockMembers(ISimulationModel model, String flock, int numberToAdd, 
    		String shape, int color, int trailSize){
    	
    }
}
