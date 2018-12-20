package audio;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestMP3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file = "src/resources/Old_Phone_Ring.mp3";
//		String file = "src/resources/0743.mp3";
		Scanner keyboard = new Scanner(System.in);
		URL url = null;
		URI uri = null;
		Path path = null;
		try {
			url = Paths.get(file).toUri().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mp3Player mp3 = new Mp3Player(url,0.5f);
		mp3.play();
		while (!keyboard.hasNext()) {}
		mp3.close();
		keyboard.close();
	}
}