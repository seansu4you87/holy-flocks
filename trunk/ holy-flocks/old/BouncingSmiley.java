package old;

import java.awt.*;

/**
 * Represents a smiley face that bounces around within the given bounds.
 * 
 * Draws itself as a smiley face.
 * 
 * @author Robert C. Duvall
 */
public class BouncingSmiley extends BouncingBall {
	/**
	 * Create a bouncer
	 * 
	 * @param center
	 *            initial position
	 * @param size
	 *            diameter of the bouncer
	 * @param velocity
	 *            amount to move in x- and y-direction
	 * @param color
	 *            color of the bouncer
	 */
	public BouncingSmiley(PointD center, Dimension size, PointD velocity,
			Color color, int trailSize) {
		super(center, size, velocity, color, trailSize);
	}

	/**
	 * Describes how to "animate" the shape by changing its state.
	 * 
	 * Currently, moves by the current velocity.
	 * 
	 * @param bounds
	 *            which encloses this shape
	 */
	public void update(Canvas canvas) {
		super.update(canvas);
		// myVelocity.y++;
	}

	/**
	 * Paint graphical view of bouncing smiley
	 * 
	 * @param pen
	 *            knows how to draw on the screen
	 */
	public void paint(Graphics pen) {
		// draw trail if it exists
		super.paint(pen);
		// draw shape
		pen.setColor(java.awt.Color.BLACK);
		// left eye
		pen
				.fillOval(
						(int) (myCenter.getX() - mySize.width / 8 - mySize.width / 20),
						(int) (myCenter.getY() - mySize.height / 8 - mySize.height / 10),
						mySize.width / 10, mySize.height / 5);
		// right eye
		pen
				.fillOval(
						(int) (myCenter.getX() + mySize.width / 8 - mySize.width / 20),
						(int) (myCenter.getY() - mySize.height / 8 - mySize.height / 10),
						mySize.width / 10, mySize.height / 5);
		// smile!
		pen
				.drawArc(
						(int) (myCenter.getX() - 11 * mySize.width / 40),
						(int) (myCenter.getY() + mySize.height / 20 - 11 * mySize.height / 40),
						11 * mySize.width / 20, 11 * mySize.height / 20, 345,
						-150);
	}
}
