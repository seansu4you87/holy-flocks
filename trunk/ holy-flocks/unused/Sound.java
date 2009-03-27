package unused;

import java.applet.Applet;
import java.applet.AudioClip;

    
/**
 * This class handles playing sounds.
 *
 * Only the following formats are supported: wav, aiff, au, mid, rmf.
 *
 * @author Robert C. Duvall
 */
public class Sound
{
    private AudioClip myClip;


    /**
     * Construct a sound with the data referred to by the given filename.
     */
    public Sound (String filename)
    {
        setSound(filename);
    }


    /**
     * Set this sound to the data referred to by the given filename.
     */
    public void setSound (String filename)
    {
        myClip = Applet.newAudioClip(getClass().getResource("/" + filename));
    }


    /**
     * Play the given sound.
     */
    public void play ()
    {
        myClip.play();
    }


    /**
     * Stop playing the given sound.
     */
    public void stop ()
    {
        myClip.stop();
    }
}
