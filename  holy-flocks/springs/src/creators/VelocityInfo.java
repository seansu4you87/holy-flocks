package creators;

import java.awt.*;

public class VelocityInfo
{
	private Flock myFlock;
	private Dimension mySize;
	private Point myMousePosition;

	public VelocityInfo()
	{
		myFlock = null;
		mySize = null;
		myMousePosition = new Point(0,0);
	}
	
	public Flock getFlock()
	{
		return myFlock;
	}

	public void setFlock(Flock flock)
	{
		myFlock = flock;
	}
	
	public Dimension getSize()
	{
		return mySize;
	}

	public void setSize(Dimension size)
	{
		this.mySize = size;
	}
	public Point getMyMousePosition()
	{
		return myMousePosition;
	}

	public void setMyMousePosition(Point mousePosition)
	{
		myMousePosition = mousePosition;
	}
}
