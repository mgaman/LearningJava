
package audio;
 
/*************************************************************************
 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X) 
 *                javac -classpath .;jl1.0.jar MP3.java         (Windows) 
 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.mp3  (OS X / Linux) 
 *                java -classpath .;jl1.0.jar MP3 filename.mp3  (Windows) 
 *   
 *  Plays an MP3 file using the JLayer MP3 library. 
 * 
 *  Reference:  http://www.javazoom.net/javalayer/sources.html 
 * 
 * 
 *  To execute, get the file jl1.0.jar from the website above or from 
 * 
 *      http://www.cs.princeton.edu/introcs/24inout/jl1.0.jar 
 * 
 *  and put it in your working directory with this file MP3.java. 
 * 
 *************************************************************************/ 
 
import java.io.BufferedInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener; 
 
 
/*
 * http://introcs.cs.princeton.edu/java/faq/mp3/mp3.html 
 *  
 * Using JLayer library (jl1.0.1.jar) 
 */ 
/**
 *  @author David Henry
 *  @version 0.1
 *  @param URL The URL of the file to be played
 *  @param volume A float number between 0 and 1 - ignored
 */
public class Mp3Player { 
    private URL url; 
    private float volume; 
    private Player player;  
    private AdvancedPlayer aplayer;
    public boolean isPlaying = false;
    // constructor that takes the name of an MP3 resource and the volume 
    public Mp3Player(URL url, float volume) { 
        this.url = url; 
        this.volume = volume; 
    } 
 /**
  * @return void
  * @param none
  */
    public void close() {  
 //    if (player != null) 
    if (aplayer != null)
      player.close();  
     isPlaying = false;
    } 
 
    // play the MP3 file to the sound card 
    public void play() { 
        try { 
            BufferedInputStream bis = new BufferedInputStream(url.openStream()); 
       //    player = new Player(bis/*, volume*/); 
            aplayer = new AdvancedPlayer(bis);
            aplayer.setPlayBackListener(new PlaybackListener() {
            	@Override
            	public void playbackFinished(PlaybackEvent e)
            	{
            		System.out.println("Finished");
            	}
            	public void playbackStarted(PlaybackEvent e)
            	{
            		System.out.println("Started");
            	}
           });
        } 
        catch (Exception e) { 
            System.out.println("Problem playing file " + url); 
            e.printStackTrace(); 
        } 
 
        // run in new thread to play in background 
        new Thread() { 
            public void run() { 
                try {  
              //   player.play(); 
                 aplayer.play();
                 isPlaying = true;
                } 
                catch (Exception e) {  
                 System.out.println(e);  
                } 
            } 
        }.start(); 
    }    
}

