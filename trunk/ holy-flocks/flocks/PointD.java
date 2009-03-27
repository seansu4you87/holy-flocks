package flocks;

import java.awt.geom.Point2D;

public class PointD extends Point2D
{
    protected double x;
    protected double y;
    
    public PointD (double xVal, double yVal)
    {
        x = xVal;
        y = yVal;
    }
    
    public PointD (PointD point)
    {
        x = point.getX();
        y = point.getY();
    }

    public double getX ()
    {
        return x;
    }

    public double getY ()
    {
        return y;
    }

    public void setLocation (double xVal, double yVal)
    {
        x = xVal;
        y = yVal;
    }
    
    public void translate (double dx, double dy)
    {
        x += dx;
        y += dy;
    }

}
