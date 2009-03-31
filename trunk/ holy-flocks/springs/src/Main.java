import guiAndAbstracts.Applet;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.*;

import constantsFiles.*;
/**
 * Creates window that can be moved, resized, and closed by the user.
 *
 * @author David Eitel, Eric Wheeler, Robert C. Duvall
 */
public class Main implements Constants
{
    // main --- where the program starts
    public static void main (String args[])
    {
    	Applet app = new Applet();
        app.init(SIZE);
        
        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // add our user interface components to Frame and show it
        frame.getContentPane().add(app, BorderLayout.CENTER);
        frame.pack();        
        frame.setVisible(true);
        // start the animation
        app.start();
    }
}