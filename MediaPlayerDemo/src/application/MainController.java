package application;

//import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.event.ActionEvent;  // use this insted of AWT event

public class MainController implements Initializable {
	@FXML private MediaView mv;
	@FXML private Slider volume;
	private MediaPlayer mp;
	private Media me;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String path = new File("src/media/Nokia.mp4").getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
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
	@FXML
	void play(ActionEvent ev)
	{
		mp.play();
	}
	@FXML
	void pause(ActionEvent ev)
	{
		mp.pause();
	}
	@FXML
	void fast(ActionEvent ev)
	{
		mp.setRate(2);
	}
	@FXML
	void slow(ActionEvent ev)
	{
		mp.setRate(0.5);
	}
	@FXML
	void reload(ActionEvent ev)
	{
		mp.seek(mp.getStartTime());
		mp.play();
	}
	@FXML
	void start(ActionEvent ev)
	{
		mp.seek(mp.getStartTime());
		mp.stop();
	}
	@FXML
	void last(ActionEvent ev)
	{
		mp.seek(mp.getTotalDuration());
		mp.stop();
	}
}
