import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	private static int c = 1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting....");
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
		    @Override
		    public void run() {
		      //  System.out.println("Time up, running do_something()");
		        System.out.println(c++);
		    }
		};
		timer.schedule(timerTask, 5000L, 1000L);
		while (c < 15 ) {}
		timer.cancel();
		System.out.println("Finishing....");
	}
}
