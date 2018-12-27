package application;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayTone implements Runnable{

	String prefix = "src/media/audiocheck.net_dtmf_";
	String suffix;
	String threadName = "";
	Thread t;
	int priority = 5;
	private Media m;
	private MediaPlayer mp;
	
	// constructor
	PlayTone(String digit)
	{
		suffix = digit;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String file = prefix + suffix + ".wav";
		String a_name = new File(file).getAbsolutePath();
		System.out.println(a_name);
		m = new Media(new File(a_name).toURI().toString());
		mp = new MediaPlayer(m);
		mp.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub				
			}			
		});
		mp.play();
		while (mp.getStatus() == MediaPlayer.Status.PLAYING)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this,threadName);
			t.setPriority(priority);
			t.start();
		}
	}
	
	public static void main(String[] args) {
		PlayTone pt = new PlayTone("1");
		pt.start();
	}
}
