package simulationFramework;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;



/**
 * 
 * @author Weiping Zhang, Sean Yu
 * 
 */
public interface ISimulationComponent {

	public PointD getVelocity();

	public Color getColor();

	public void setColor(Color color);

	public PointD getCenter();

	public void paint(Graphics pen);

	public void update(ISimulationModel model);

}
