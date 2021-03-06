package application;

//import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.event.ActionEvent;  // use this instead of AWT event

public class MainController implements Initializable {
	@FXML private MediaView mv;
	@FXML private Slider volume;
	@FXML private TextField txtPhone;
	private MediaPlayer mp,map;
	private Media me,ma;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// AUDIO MEDIA
		String a_name = new File("src/media/0743.mp3").getAbsolutePath();
		ma = new Media(new File(a_name).toURI().toString());
		map = new MediaPlayer(ma);
		map.setAutoPlay(false);
		map.setOnEndOfMedia(new Runnable () {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("End of audio");
				// rewind & play again
				//map.seek(mp.getStartTime());
				map.seek(Duration.ZERO);
			}			
		});
		// VIDEO MEDIA
		String v_name = new File("src/media/Hands.mp4").getAbsolutePath();
		me = new Media(new File(v_name).toURI().toString());
		mp = new MediaPlayer(me);
		mp.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
			//	mp.dispose();
			}
			
		});
		mv.setMediaPlayer(mp);
		volume.setValue(mp.getVolume()*volume.getMax());
		volume.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				mp.setVolume(volume.getValue()/volume.getMax());
			}
		});
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		try {
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		try  {
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	//	mp.setAutoPlay(true);
	}
	public void stopaudio(ActionEvent ev)
	{
		map.stop();
	}
	public void playaudio(ActionEvent ev)
	{
		map.seek(mp.getStartTime());
		map.play();
	}
	public void play(ActionEvent ev)
	{
		mp.play();
	}
	public void pause(ActionEvent ev)
	{
		mp.pause();
	}
	public void fast(ActionEvent ev)
	{
		mp.setRate(2);
	}
	public void slow(ActionEvent ev)
	{
		mp.setRate(0.5);
	}
	public void reload(ActionEvent ev)
	{
		mp.seek(mp.getStartTime());
		mp.play();
	}
	public void start(ActionEvent ev)
	{
		mp.seek(mp.getStartTime());
		mp.stop();
	}
	public void last(ActionEvent ev)
	{
		mp.seek(mp.getTotalDuration());
		mp.stop();
	}
	public void dial(ActionEvent ev)
	{
		String phone = txtPhone.getText();
		String prefix = "src/media/audiocheck.net_dtmf_";
		String suffix = ".wav";
		ObservableList<Media> mediaList = FXCollections.observableArrayList();
		for (int i=0;i<phone.length();i++)
		{
			mediaList.add(new Media(new File(new File(prefix + phone.substring(i, i+1) + suffix).getAbsolutePath()).toURI().toString()));
		}
        playMediaTracks(mediaList);
	}
	private void playMediaTracks(ObservableList<Media> mediaList) {
        if (mediaList.size() == 0)
            return;

        MediaPlayer mediaplayer = new MediaPlayer(mediaList.remove(0));
        mediaplayer.play();

        mediaplayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playMediaTracks(mediaList);
            }
        });
    }
}
